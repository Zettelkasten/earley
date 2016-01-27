package com.zettelnet.earley.advlatin;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.NonTerminal;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.Chart;
import com.zettelnet.earley.Symbol;
import com.zettelnet.earley.Terminal;
import com.zettelnet.earley.latin.LatinParser;

public final class ParseResult<T> {

	private final Grammar grammar;
	private final List<T> tokens;

	private boolean complete;

	private List<Chart> charts;
	private int chartPosition;
	private Chart currentChart;

	protected ParseResult(final LatinParser<T> latinParser, final List<T> tokens) {
		this.grammar = latinParser.getGrammar();
		this.tokens = tokens;

		setup();
		seed();
		parse();
	}

	private void setup() {
		complete = false;

		// setup state sets
		int stateSetCount = 1 << tokens.size();
		System.out.println("Initializing " + stateSetCount + " state sets");
		System.out.println("Tokens are " + tokens);
	}

	private void seed() {
		currentChart = charts.get(0);
		predict(grammar.getStartSymbol());
	}

	@SuppressWarnings("unchecked")
	private void parse() {
		for (chartPosition = 0; chartPosition < charts.size() && !complete; chartPosition++) {
			currentChart = charts.get(chartPosition);

			State state;
			while ((state = currentChart.poll()) != null && !complete) {
				Symbol next = state.next();

				if (next == null) {
					// completion
					complete(state);
				} else {
					if (next instanceof Terminal) {
						// scanning
						scan(state, (Terminal<T>) next);
					} else if (next instanceof NonTerminal) {
						// prediction
						predict((NonTerminal) next);
					} else {
						assert false : "Symbol not Terminal nor NonTerminal";
					}
				}
			}
		}
	}

	private void predict(NonTerminal nonTerminal) {
		Set<Production> productions = grammar.getProductions(nonTerminal);
		for (Production production : productions) {
			currentChart.add(new State(currentChart, production, 0, chartPosition));
		}
	}

	private void scan(State state, Terminal<T> terminal) {
		for (T toResolve : unusedTokens()) {
			Chart nextStates = nextStates(toResolve);
			if (nextStates != null && terminal.isCompatibleWith(toResolve, state)) {
				State newState = new State(currentChart, state.getProduction(), state.getCurrentPosition() + 1, state.getOriginPosition());
				nextStates.add(newState);
			}
		}
	}

	private Chart nextStates(T resolved) {
		int nextPosition = chartPosition | (1 << tokens.indexOf(resolved));
		return nextPosition < charts.size() ? charts.get(nextPosition) : null;
	}

	private Set<T> unusedTokens() {
		Set<T> unused = new HashSet<>(tokens.size());
		int index = 0;
		int position = chartPosition ^ statesMax();
		while (position > 0) {
			if ((position & 1) == 1) {
				unused.add(tokens.get(index));
			}
			position >>= 1;
			index++;
		}
		return unused;
	}

	private int statesMax() {
		return (1 << tokens.size()) - 1;
	}

	private void complete(State state) {
		NonTerminal key = state.getProduction().key();
		if (chartPosition == statesMax() && key == grammar.getStartSymbol()) {
			// done
			complete = true;
			return;
		} else {
			// search for completors
			Chart searchStates = charts.get(state.getOriginPosition());
			for (State searchState : searchStates) {
				Production searchProduction = searchState.getProduction();
				int searchPosition = searchState.getCurrentPosition();
				if (searchPosition < searchProduction.size() && searchProduction.get(searchPosition) == key) {
					// found
					int newPosition = searchState.getCurrentPosition() + 1;
					State newState = new State(currentChart, searchProduction, newPosition, searchState.getOriginPosition());
					currentChart.add(newState);
				}
			}
		}
	}

	public void printStateSets(PrintStream out) {
		int padding = 50;
		List<Iterator<State>> iterators = new ArrayList<>(charts.size());
		for (Chart chart : charts) {
			iterators.add(chart.iterator());
			out.print(String.format("%-" + padding + "s", "     " + chart.getInputPosition()));
		}
		out.println();

		int next;
		do {
			next = 0;
			for (Iterator<State> iterator : iterators) {
				if (iterator.hasNext()) {
					out.print(String.format("%" + padding + "s", iterator.next()));
					next++;
				} else {
					out.print(String.format("%" + padding + "s", ""));
				}
			}
			out.println();
		} while (next > 0);
	}

	public boolean isComplete() {
		return complete;
	}
}
