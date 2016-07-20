package com.zettelnet.earley;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.input.InputPositionInitializer;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.binary.BinarySyntaxTree;
import com.zettelnet.earley.tree.binary.InitialStateBinarySyntaxTree;

public final class EarleyParseResult<T, P extends Parameter> implements ParseResult<T, P> {

	private final Grammar<T, P> grammar;
	private final List<T> tokens;
	private final InputPositionInitializer<T> inputPositionInitializer;

	private boolean complete;

	private SortedMap<InputPosition<T>, Chart<T, P>> charts;
	private Chart<T, P> currentChart;

	private final Set<State<T, P>> completeStates = new HashSet<>();

	protected EarleyParseResult(final EarleyParser<T, P> parser, final List<T> tokens, final InputPositionInitializer<T> inputPositionInitializer) {
		this.grammar = parser.getGrammar();
		this.tokens = tokens;
		this.inputPositionInitializer = inputPositionInitializer;

		setup();
		seed();
		parse();
	}

	private void setup() {
		complete = false;

		SortedSet<InputPosition<T>> inputPositions = inputPositionInitializer.getInputPositions(tokens);
		this.charts = new TreeMap<>(inputPositions.comparator());
		for (InputPosition<T> inputPosition : inputPositions) {
			charts.put(inputPosition, new Chart<>(inputPosition));
		}
	}

	private void seed() {
		currentChart = charts.get(charts.firstKey());

		predict(new SeedState<T, P>(currentChart,
				grammar.getStartSymbol(),
				grammar.getStartSymbolParameter().makeParameter(grammar.getStartSymbol()),
				grammar.getStartSymbolParameterExpression()),
				grammar.getStartSymbol());
	}

	private void parse() {
		for (Iterator<Map.Entry<InputPosition<T>, Chart<T, P>>> i = charts.entrySet().iterator(); i.hasNext() && !complete;) {
			Map.Entry<InputPosition<T>, Chart<T, P>> entry = i.next();
			currentChart = entry.getValue();

			State<T, P> state;
			while ((state = currentChart.poll()) != null && !complete) {
				Symbol<T> next = state.next();

				if (next == null) {
					// completion
					complete(state);
				} else {
					if (next instanceof Terminal) {
						// scanning
						scan(state, (Terminal<T>) next);
					} else if (next instanceof NonTerminal) {
						// prediction
						predict(state, (NonTerminal<T>) next);
					} else {
						throw new AssertionError("Symbol neither Terminal nor NonTerminal");
					}
				}
			}
		}
	}

	private void predict(State<T, P> state, NonTerminal<T> nonTerminal) {
		InputPosition<T> chartPosition = currentChart.getInputPosition();
		StateCause<T, P> cause = new StateCause.Predict<>(state);

		ParameterExpression<T, P> parameterExpression = state.nextParameterExpression();
		P sourceParameter = state.getParameter();

		Set<Production<T, P>> productions = grammar.getProductions(nonTerminal);
		for (Production<T, P> production : productions) {
			// get new parameter; for seed (state = null) this is the start
			// parameter
			Collection<P> newParameters = parameterExpression.predict(sourceParameter, production.keyParameter(), nonTerminal);

			for (P newParameter : newParameters) {
				if (production.isEpsilon() && state.getProduction() != null) {
					// epsilonize state: next symbol can be resolved as epsilon
					// (this is not the case for seed states!)

					State<T, P> newState = new SimpleState<>(currentChart, state.getProduction(), state.getCurrentPosition() + 1, state.getOriginPosition(), sourceParameter);
					currentChart.add(newState, new StateCause.Epsilon<>(state, production, newParameter));
				} else {
					State<T, P> newState = new SimpleState<>(currentChart, production, 0, chartPosition, newParameter);
					currentChart.add(newState, cause);
				}
			}
		}
	}

	private void scan(State<T, P> state, Terminal<T> terminal) {
		InputPosition<T> chartPosition = currentChart.getInputPosition();
		NonTerminal<T> parentSymbol = state.getProduction().key();

		for (Map.Entry<InputPosition<T>, T> entry : chartPosition.getAvailableTokens().entrySet()) {
			Chart<T, P> nextChart = charts.get(entry.getKey());
			T toResolve = entry.getValue();

			if (nextChart != null && terminal.isCompatibleWith(toResolve)) {
				ParameterExpression<T, P> parameterExpression = state.nextParameterExpression();
				P parameter = state.getParameter();

				for (P newParameter : parameterExpression.scan(parameter, parentSymbol, toResolve, terminal)) {
					StateCause<T, P> origin = new StateCause.Scan<>(state, toResolve, newParameter);

					State<T, P> newState = new SimpleState<>(nextChart, state.getProduction(), state.getCurrentPosition() + 1, state.getOriginPosition(), newParameter);
					nextChart.add(newState, origin);
				}
			}
		}
	}

	private void complete(State<T, P> state) {
		InputPosition<T> chartPosition = currentChart.getInputPosition();

		NonTerminal<T> key = state.getProduction().key();
		if (chartPosition.isComplete() && key.equals(grammar.getStartSymbol()) && state.getOriginPosition().isClean()) {
			// done
			completeStates.add(state);
			// complete = true;
		}
		if (!state.getProduction().isEpsilon()) {
			// do not complete epsilon states, they are already handled by
			// prediction (only way for epsilon states to be inserted into chart
			// is when they are seeded)

			P childParameter = state.getParameter();

			// search for completors
			Chart<T, P> searchStates = charts.get(state.getOriginPosition());
			for (State<T, P> searchState : searchStates.concurrentIterable()) {
				Production<T, P> searchProduction = searchState.getProduction();
				int searchPosition = searchState.getCurrentPosition();
				if (searchPosition < searchProduction.size() && searchProduction.get(searchPosition).equals(key)) {
					// found

					StateCause<T, P> origin = new StateCause.Complete<>(state, searchState);
					int newPosition = searchState.getCurrentPosition() + 1;

					ParameterExpression<T, P> parameterExpression = searchState.nextParameterExpression();
					P searchParameter = searchState.getParameter();

					for (P newParameter : parameterExpression.complete(searchParameter, searchProduction.key(), childParameter)) {
						State<T, P> newState = new SimpleState<>(currentChart, searchProduction, newPosition, searchState.getOriginPosition(), newParameter);
						currentChart.add(newState, origin);
					}
				}
			}
		}
	}

	public void printChartSetsHtml(PrintStream out) {
		new ChartSetPrinter<>(charts, tokens).print(out);
	}

	@Override
	public boolean isComplete() {
		return !completeStates.isEmpty();
	}

	@Override
	public SortedMap<InputPosition<T>, Chart<T, P>> getCharts() {
		return charts;
	}

	@Override
	public Set<State<T, P>> getCompleteStates() {
		return completeStates;
	}

	public BinarySyntaxTree<T, P> getBinarySyntaxTree() {
		return new InitialStateBinarySyntaxTree<>(grammar, completeStates);
	}

	@Override
	public SyntaxTree<T, P> getSyntaxTree() {
		return getBinarySyntaxTree().toNaturalTree();
	}
}
