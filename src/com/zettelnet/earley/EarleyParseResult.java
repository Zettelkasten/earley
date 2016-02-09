package com.zettelnet.earley;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

		predict(new SeedState<T, P>(currentChart, grammar.getStartSymbol(), grammar.getStartSymbolParameter().makeParameter(), grammar.getStartSymbolParameterExpression()), grammar.getStartSymbol());
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

		Set<Production<T, P>> productions = grammar.getProductions(nonTerminal);
		for (Production<T, P> production : productions) {
			// get new parameter; for seed (state = null) this is the start
			// parameter
			ParameterExpression<T, P> parameterExpression = state.nextParameterExpression();
			P sourceParameter = state.getParameter();
			Collection<P> newParameters = parameterExpression.predict(sourceParameter, production.keyParameter());

			for (P newParameter : newParameters) {
				State<T, P> newState = new SimpleState<>(currentChart, production, 0, chartPosition, newParameter);
				currentChart.add(newState, cause);
			}

		}
	}

	private void scan(State<T, P> state, Terminal<T> terminal) {
		InputPosition<T> chartPosition = currentChart.getInputPosition();

		for (T toResolve : chartPosition.getAvailableTokens()) {
			Chart<T, P> nextChart = charts.get(chartPosition.nextPosition(toResolve));

			if (nextChart != null && terminal.isCompatibleWith(toResolve)) {

				StateCause<T, P> origin = new StateCause.Scan<>(state, toResolve);

				ParameterExpression<T, P> parameterExpression = state.nextParameterExpression();
				P parameter = state.getParameter();

				for (P newParameter : parameterExpression.scan(parameter, toResolve, terminal)) {
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
		if (!complete) {
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

					for (P newParameter : parameterExpression.complete(searchParameter, childParameter)) {
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
		return complete;
	}

	@Override
	public SortedMap<InputPosition<T>, Chart<T, P>> getCharts() {
		return charts;
	}
	
	public Set<State<T, P>> getCompleteStates() {
		return completeStates;
	}

	@Override
	public Collection<ParseTree<T>> getTreeForest() {
		// System.out.println("Retreiving parse forest");

		Collection<ParseTree<T>> trees = new HashSet<>();
		for (State<T, P> completeState : completeStates) {
			Queue<TreeQueue<T, P>> queueList = new LinkedList<>();

			// seed
			ParseTree<T> seedTree = new ParseTree<T>(grammar.getStartSymbol());
			Queue<TreeState<T, P>> seedQueue = new LinkedList<>();
			seedQueue.add(new TreeState<>(completeState, seedTree));
			queueList.add(new TreeQueue<>(seedTree, seedQueue));

			// System.out.println("Start");
			// System.out.println(" >>> " + seedTree);

			while (!queueList.isEmpty()) {
				TreeQueue<T, P> treeQueue = queueList.poll();
				ParseTree<T> tree = treeQueue.getTree();
				Queue<TreeState<T, P>> queue = treeQueue.getQueue();

				while (!queue.isEmpty()) {
					TreeState<T, P> current = queue.poll();
					State<T, P> state = current.getState();
					TreeNode<T> branch = current.getBranch();

					int pos = state.getCurrentPosition();
					if (pos > 0) {
						// System.out.println("processing s' = " + state + " on
						// " + state.getChart());
						Symbol<T> last = state.getProduction().get(pos - 1);
						Collection<StateCause<T, P>> causes = state.getCause();
						// for (Iterator<StateCause> i = causes.iterator();
						// i.hasNext(); ) {
						StateCause<T, P> cause = new ArrayList<>(causes).get(causes.size() - 1);
						// if (i.hasNext()) {
						// // clone tree
						// // transfer TreeQueue to new queue
						// }

						if (cause instanceof StateCause.Scan) {
							// reverse-scanning
							StateCause.Scan<T, P> scanCause = (StateCause.Scan<T, P>) cause;
							branch.addChild(new TerminalNode<>((Terminal<T>) last));
							queue.add(new TreeState<>(scanCause.getPreState(), branch));

							// System.out.println(" - s' is result of
							// scanning");
							// System.out.println(" - s0 = " +
							// scanCause.getFromState() + " on " +
							// scanCause.getFromState().getChart() + " ->
							// queued");
						} else if (cause instanceof StateCause.Complete) {
							// reverse-completion
							StateCause.Complete<T, P> completeCause = (StateCause.Complete<T, P>) cause;
							// from state
							TreeNode<T> newBranch = new TreeNode<>((NonTerminal<T>) last);
							branch.addChild(newBranch);
							queue.add(new TreeState<>(completeCause.getChildState(), newBranch));
							// with state
							queue.add(new TreeState<>(completeCause.getPreState(), branch));

							// System.out.println(" - s' is result of
							// completion");
							// System.out.println(" - s0 = " +
							// completeCause.getFromState() + " on " +
							// completeCause.getFromState().getChart() + " ->
							// queued first");
							// System.out.println(" - s = " +
							// completeCause.getWithState() + " on " +
							// completeCause.getWithState().getChart() + " ->
							// queued after");
						} else {
							// do nothing

							// System.out.println(" - s' is result of
							// prediction");
						}

						// System.out.println(" >>> " + tree);
						// }
					}
				}

				trees.add(tree);
			}
		}
		return trees;
	}
}
