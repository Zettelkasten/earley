package com.zettelnet.earley.print;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.zettelnet.earley.Chart;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.StateCause;
import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.symbol.Symbol;

public class ChartSetPrinter<T, P extends Parameter> {

	private final SortedMap<InputPosition<T>, Chart<T, P>> charts;
	private final List<T> tokens;

	private final Map<Chart<T, P>, Map<State<T, P>, Integer>> stateIds;

	private boolean tableMode;

	private final Set<Chart<T, P>> aliveCharts;
	private final Set<State<T, P>> aliveStates;

	private final static NumberFormat percentFormat;
	static {
		percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(1);
	}

	public ChartSetPrinter(final SortedMap<InputPosition<T>, Chart<T, P>> charts, final List<T> tokens) {
		this(charts, tokens, true);
	}

	public ChartSetPrinter(final SortedMap<InputPosition<T>, Chart<T, P>> charts, final List<T> tokens, boolean align) {
		this.charts = charts;
		this.tokens = tokens;
		this.tableMode = align;

		stateIds = new HashMap<>();
		for (Chart<T, P> chart : charts.values()) {
			Map<State<T, P>, Integer> ids = new HashMap<>();
			int id = 0;
			for (State<T, P> state : chart) {
				ids.put(state, id);
				id++;
			}
			stateIds.put(chart, ids);
		}

		this.aliveCharts = new HashSet<>();
		this.aliveStates = new HashSet<>();
		calculateAliveStates();
		calculateAliveCharts();
	}

	private void calculateAliveStates() {
		Deque<State<T, P>> queue = new LinkedList<>();

		// find complete states
		for (Map.Entry<InputPosition<T>, Chart<T, P>> entry : charts.entrySet()) {
			if (entry.getKey().isComplete()) {
				for (State<T, P> state : entry.getValue()) {
					if (state.getCurrentPosition() == state.getProduction().size() && state.getOriginPosition().isClean()) {
						queue.add(state);
					}
				}
			}
		}

		State<T, P> next;
		while ((next = queue.poll()) != null) {
			if (!aliveStates.contains(next)) {
				aliveStates.add(next);

				for (StateCause<T, P> origin : next.getCause()) {
					if (origin instanceof StateCause.Predict) {
						StateCause.Predict<T, P> predict = (StateCause.Predict<T, P>) origin;
						if (!predict.isInitial()) {
							queue.push(predict.getParentState());
						}
					} else if (origin instanceof StateCause.Scan) {
						StateCause.Scan<T, P> scan = (StateCause.Scan<T, P>) origin;
						queue.push(scan.getPreState());
					} else if (origin instanceof StateCause.Complete) {
						StateCause.Complete<T, P> complete = (StateCause.Complete<T, P>) origin;
						queue.push(complete.getPreState());
						queue.push(complete.getChildState());
					} else if (origin instanceof StateCause.Epsilon) {
						StateCause.Epsilon<T, P> epsilon = (StateCause.Epsilon<T, P>) origin;
						queue.push(epsilon.getPreState());
					}
				}
			}
		}
	}

	private void calculateAliveCharts() {
		for (Chart<T, P> chart : charts.values()) {
			for (State<T, P> state : chart) {
				if (aliveStates.contains(state)) {
					aliveCharts.add(chart);
					break;
				}
			}
		}
	}

	private Integer getStateId(State<T, P> state) {
		return stateIds.get(state.getChart()).get(state);
	}

	public void print(PrintStream out) {
		out.print("<html>");
		out.print("<head>");
		out.print("<meta charset='utf-8'>");
		out.print("<meta name='viewport' content='width=device-width, initial-scale=1'>");
		out.print("<link href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css' rel='stylesheet'>");
		out.print("<link href='src/main.css' rel='stylesheet'>");
		out.print("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		out.print("<script src='src/main.js'></script>");
		out.print("</head>");
		out.print("<body>");
		out.print("<div class='container'>");
		out.print("<input type='checkbox' id='include-dead-states' checked> <label for='include-dead-states'>Show dead states</label>; ");

		int totalStates = 0;
		int aliveStates = 0;
		for (Chart<T, P> chart : charts.values()) {
			for (State<T, P> state : chart) {
				totalStates++;
				if (this.aliveStates.contains(state)) {
					aliveStates++;
				}
			}
		}

		out.printf("total %s (%s) states", totalStates, aliveStates);

		for (Chart<T, P> chart : charts.values()) {
			if (chart.iterator().hasNext()) {
				printChart(out, chart);
			}
		}

		out.print("<div class='container footer'>");
		out.printf("<span class='generated'>Automatically generated on %s</span>", new Date());
		out.print("</div>");

		out.print("</div>");

		out.print("</body>");
		out.print("</html>");
	}

	public void printChart(PrintStream out, Chart<T, P> chart) {
		String chartClass = "chart chart-" + (aliveCharts.contains(chart) ? "alive" : "dead");

		out.printf("<div class='%s' id='chart-%s'>", chartClass, chart.getInputPosition());
		out.print("<h2 class='chart-title'>");
		out.printf("S(%s) - ", chart.getInputPosition());
		printInputPosition(out, chart.getInputPosition());
		out.print("</h2>");

		if (tableMode) {
			out.print("<table class='chart-states' border='0'>");
		} else {
			out.print("<ol class='chart-states'>");
		}
		for (State<T, P> state : chart) {
			printState(out, state);
		}
		if (tableMode) {
			out.print("</table>");
		} else {
			out.print("</ol>");
		}

		out.print("</div>");
	}

	public void printInputPosition(PrintStream out, InputPosition<T> position) {
		out.print("<span class='chart-tokens'>");
		for (Iterator<T> i = tokens.iterator(); i.hasNext();) {
			T token = i.next();

			boolean available = position.isTokenAvailable(token);
			boolean used = position.getUsedTokens().contains(token);

			if (available) {
				out.print("<u>");
			}
			if (used) {
				out.print("<strike>");
			}
			out.print(token);
			if (used) {
				out.print("</strike>");
			}
			if (available) {
				out.print("</u>");
			}

			if (i.hasNext()) {
				out.print(" ");
			}
		}
		out.print("</span>");
	}

	public void printState(PrintStream out, State<T, P> state) {
		String stateClass = "state state-" + (aliveStates.contains(state) ? "alive" : "dead");

		if (tableMode) {
			out.printf("<tr class='%s' id='state-%s-%s'>", stateClass, state.getChart().getInputPosition(),
					getStateId(state));
			out.printf("<td class='state-id'>(%s)</td>", getStateId(state) + 1);
		} else {
			out.printf("<li class='%s' id='state-%s-%s'>", stateClass, state.getChart().getInputPosition(),
					getStateId(state));
		}

		Production<T, P> production = state.getProduction();
		if (tableMode) {
			out.print("<td class='production-key'>");
		}
		printSymbol(out, production.key());
		printParameter(out, state.getParameter());
		if (tableMode) {
			out.print("</td>");
		}

		if (tableMode) {
			out.print("<td class='production-arrow'>&#8594;</td>");
		} else {
			out.print(" &#8594;");
		}

		List<Symbol<T>> values = production.values();

		if (values.size() > 0) {
			if (tableMode) {
				out.print("<td class='production-values'>");
			}

			int bullet = state.getCurrentPosition();

			for (int i = 0; i <= values.size(); i++) {
				if (i == bullet) {
					out.print(" &bull;");
				}
				if (i < values.size()) {
					out.print(" ");
					printSymbol(out, values.get(i));
					printParameterExpression(out, production.getParameterExpression(i));
				}
			}

		} else {
			if (tableMode) {
				out.print("<td class='production-values'>&epsilon;");
			} else {
				out.print(" &epsilon;");
			}
		}

		out.printf(", <span class='state-originpos'>%s</span>", state.getOriginPosition());
		if (tableMode) {
			out.print("</td>");
		}
		
		if (tableMode) {
			out.printf("<td class='state-probability' title=%s>%s</td>", state.getProbability(), percentFormat.format(state.getProbability()));
		} else {
			out.printf(" (%s)", percentFormat.format(state.getProbability()));
		}

		if (tableMode) {
			out.print("<td>");
		} else {
			out.print(" ");

		}
		for (Iterator<StateCause<T, P>> i = state.getCause().iterator(); i.hasNext();) {
			StateCause<T, P> cause = i.next();

			printStateOrigin(out, cause, state);
			if (i.hasNext()) {
				out.print(" ");
			}
		}

		if (state.getCurrentPosition() == state.getProduction().size() && state.getOriginPosition().isClean() && state.getChart().getInputPosition().isComplete()) {
			out.print(" DONE!");
		}

		if (tableMode) {
			out.print("</td>");
		}

		if (tableMode) {
			out.print("</tr>");
		} else {
			out.print("</li>");
		}
	}

	public void printParameter(PrintStream out, P parameter) {
		out.printf("(&pi; : %s)", parameter);
	}

	public void printParameterExpression(PrintStream out, ParameterExpression<T, P> parameter) {
		out.printf("(%s)", parameter);
	}

	public void printSymbol(PrintStream out, Symbol<T> symbol) {
		out.printf("<code>%s</code>", symbol);
	}

	public void printStateReference(PrintStream out, State<T, P> state, State<T, P> observer) {
		out.printf("<span class='state-ref' data-target='state-%s-%s'>", state.getChart().getInputPosition(),
				getStateId(state));
		if (observer != null && !state.getChart().equals(observer.getChart())) {
			out.printf("S(%s)", state.getChart().getInputPosition());
		}
		out.printf("(%s)", getStateId(state) + 1);
		out.print("</span>");
	}

	public void printStateOrigin(PrintStream out, StateCause<T, P> origin, State<T, P> observer) {
		out.print("<span class='state-origin'>(");
		if (origin instanceof StateCause.Predict) {
			StateCause.Predict<T, P> predict = (StateCause.Predict<T, P>) origin;
			if (predict.isInitial()) {
				out.print("seed");
			} else {
				out.print("predict ");
				printStateReference(out, predict.getParentState(), observer);
			}
		} else if (origin instanceof StateCause.Scan) {
			StateCause.Scan<T, P> scan = (StateCause.Scan<T, P>) origin;
			out.print("scan ");
			printStateReference(out, scan.getPreState(), observer);
			out.print(" with ");
			out.print(scan.getToken());
		} else if (origin instanceof StateCause.Complete) {
			StateCause.Complete<T, P> complete = (StateCause.Complete<T, P>) origin;
			out.print("complete ");
			printStateReference(out, complete.getChildState(), observer);
			out.print(" and ");
			printStateReference(out, complete.getPreState(), observer);
		} else if (origin instanceof StateCause.Epsilon) {
			StateCause.Epsilon<T, P> epsilon = (StateCause.Epsilon<T, P>) origin;
			out.print("epsilonize ");
			printStateReference(out, epsilon.getPreState(), observer);
		} else {
			out.print("unknown");
		}
		out.print(")</span>");
	}
}
