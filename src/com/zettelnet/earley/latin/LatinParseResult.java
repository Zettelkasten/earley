package com.zettelnet.earley.latin;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.zettelnet.earley.Chart;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.NonTerminal;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.ParseTree;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.Symbol;
import com.zettelnet.earley.Terminal;
import com.zettelnet.earley.TerminalNode;
import com.zettelnet.earley.TreeNode;
import com.zettelnet.earley.TreeQueue;
import com.zettelnet.earley.TreeState;

public final class LatinParseResult<T> implements ParseResult<T> {

	private final Grammar grammar;
	private final List<T> tokens;

	private boolean complete;
	private final Set<State> completeStates = new HashSet<>();

	private List<Chart> charts;
	private int chartPosition;
	private Chart currentChart;

	protected LatinParseResult(final LatinParser<T> latinParser, final List<T> tokens) {
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
		charts = new ArrayList<>(stateSetCount);
		for (int inputPosition = 0; inputPosition < stateSetCount; inputPosition++) {
			charts.add(new Chart(inputPosition));
		}
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
			Chart nextChart = nextStates(toResolve);
			if (nextChart != null && terminal.isCompatibleWith(toResolve, state)) {
				StateCause origin = new StateCause.Scan(state);
				State newState = new State(nextChart, state.getProduction(), state.getCurrentPosition() + 1, state.getOriginPosition());
				nextChart.add(newState, origin);
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
		int position = chartPosition ^ chartMax();
		while (position > 0) {
			if ((position & 1) == 1) {
				unused.add(tokens.get(index));
			}
			position >>= 1;
			index++;
		}
		return unused;
	}

	private int chartMax() {
		return (1 << tokens.size()) - 1;
	}

	private void complete(State state) {
		NonTerminal key = state.getProduction().key();
		if (chartPosition == chartMax() && key.equals(grammar.getStartSymbol()) && state.getOriginPosition() == 0) {
			// done
			completeStates.add(state);
			complete = true;
		}
		if (!complete) {
			// search for completors
			Chart searchStates = charts.get(state.getOriginPosition());
			for (State searchState : searchStates) {
				Production searchProduction = searchState.getProduction();
				int searchPosition = searchState.getCurrentPosition();
				if (searchPosition < searchProduction.size() && searchProduction.get(searchPosition).equals(key)) {
					// found
					StateCause origin = new StateCause.Complete(state, searchState);
					int newPosition = searchState.getCurrentPosition() + 1;
					State newState = new State(currentChart, searchProduction, newPosition, searchState.getOriginPosition());
					currentChart.add(newState, origin);
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

	@Override
	public boolean isComplete() {
		return complete;
	}
	
	@Override
	public List<Chart> getCharts() {
		return charts;
	}

	@Override
	public Collection<ParseTree<T>> getTreeForest() {
		Collection<ParseTree<T>> trees = new HashSet<>();
		for (State completeState : completeStates) {
			Queue<TreeQueue<T>> queueList = new LinkedList<>();

			// seed
			ParseTree<T> seedTree = new ParseTree<T>(grammar.getStartSymbol());
			Queue<TreeState> seedQueue = new LinkedList<>();
			seedQueue.add(new TreeState(completeState, seedTree));
			queueList.addProduction(new TreeQueue<>(seedTree, seedQueue));

			System.out.println("Start");
			System.out.println(" >>> " + seedTree);

			while (!queueList.isClean()) {
				TreeQueue<T> treeQueue = queueList.poll();
				ParseTree<T> tree = treeQueue.getTree();
				Queue<TreeState> queue = treeQueue.getQueue();

				while (!queue.isEmpty()) {
					TreeState current = queue.poll();
					State state = current.getState();
					TreeNode branch = current.getBranch();

					int pos = state.getCurrentPosition();
					if (pos > 0) {
						System.out.println("processing s' = " + state + " on " + state.getChart());
						Symbol last = state.getProduction().get(pos - 1);
						Collection<StateCause> causes = state.getCause();
						// for (Iterator<StateCause> i = causes.iterator();
						// i.hasNext(); ) {
						StateCause cause = new ArrayList<>(causes).get(causes.size() - 1);
						// if (i.hasNext()) {
						// // clone tree
						// // transfer TreeQueue to new queue
						// }

						if (cause instanceof StateCause.Scan) {
							// reverse-scanning
							StateCause.Scan scanCause = (StateCause.Scan) cause;
							branch.addChild(new TerminalNode<>((Terminal<?>) last));
							queue.add(new TreeState(scanCause.getFromState(), branch));

							System.out.println(" - s' is result of scanning");
							System.out.println(" - s0 = " + scanCause.getFromState() + " on " + scanCause.getFromState().getChart() + " -> queued");
						} else if (cause instanceof StateCause.Complete) {
							// reverse-completion
							StateCause.Complete completeCause = (StateCause.Complete) cause;
							// from state
							TreeNode newBranch = new TreeNode((NonTerminal) last);
							branch.addChild(newBranch);
							queue.add(new TreeState(completeCause.getFromState(), newBranch));
							// with state
							queue.add(new TreeState(completeCause.getWithState(), branch));

							System.out.println(" - s' is result of completion");
							System.out.println(" - s0 = " + completeCause.getFromState() + " on " + completeCause.getFromState().getChart() + " -> queued first");
							System.out.println(" - s = " + completeCause.getWithState() + " on " + completeCause.getWithState().getChart() + " -> queued after");
						} else {
							// do nothing

							System.out.println(" - s' is result of prediction");
						}

						System.out.println(" >>> " + tree);
						// }
					}
				}

				trees.add(tree);
			}
		}
		return trees;
	}
}
