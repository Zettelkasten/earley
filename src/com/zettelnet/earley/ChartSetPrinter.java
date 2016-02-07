package com.zettelnet.earley;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.symbol.Symbol;

public class ChartSetPrinter<T, P extends Parameter> {

	private final SortedMap<InputPosition<T>, Chart<T, P>> charts;
	private final List<T> tokens;

	private final Map<Chart<T, P>, Map<State<T, P>, Integer>> stateIds;

	private boolean tableMode;
	
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
		out.print("<link href='file://E:/projects/latin/workspace/earley/src/charts.css' rel='stylesheet'>");
		out.print("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		out.print("<script src='file://E:/projects/latin/workspace/earley/src/charts.js'></script>");
		out.print("</head>");
		out.print("<body>");
		out.print("<div class='container'>");

		{
			int rowCount = 0;
			for (Chart<T, P> chart : charts.values()) {
				if (rowCount == 0) {
					out.print("<div class='row'>");
				}
				printChart(out, chart);
				rowCount = (rowCount + 1) % 3;
				if (rowCount == 0) {
					out.print("</div>");
				}
			}
			if (rowCount != 0) {
				out.print("</div>");
			}
		}

		out.print("</div>");
		out.print("</body>");
		out.print("</html>");
	}

	public void printChart(PrintStream out, Chart<T, P> chart) {
		out.printf("<div class='chart' id='chart-%s'>", chart.getInputPosition());
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

			if (!available) {
				out.print("<strike>");
			}
			out.print(token);
			if (!available) {
				out.print("</strike>");
			}

			if (i.hasNext()) {
				out.print(" ");
			}
		}
		out.print("</span>");
	}

	public void printState(PrintStream out, State<T, P> state) {
		if (tableMode) {
			out.printf("<tr class='state' id='state-%s-%s'>", state.getChart().getInputPosition(), getStateId(state));
		} else {
			out.printf("<li class='state' id='state-%s-%s'>", state.getChart().getInputPosition(), getStateId(state));
		}

		Production<T, P> production = state.getProduction();
		if (tableMode) {
			out.print("<td align='right'>");
		}
		printSymbol(out, production.key());
		printParameter(out, state.getParameter());
		if (tableMode) {
			out.print("</td>");
		}
		
		if (tableMode) {
			out.print("<td>&#8594;</td>");
		} else {
			out.print(" &#8594;");
		}
		
		List<Symbol<T>> values = production.values();

		
		if (values.size() > 0) {
			if (tableMode) {
				out.print("<td>");
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
				out.print("<td>&epsilon;");
			} else {
				out.print(" &epsilon;");
			}
		}

		out.printf(", <span class='state-originpos'>%s</span>", state.getOriginPosition());
		if (tableMode) {
			out.print("</td>");
		}
		
		if (tableMode) {
			out.print("<td>");
		} else {
			out.print(" ");
			
		}
		for (Iterator<StateCause<T, P>> i = state.getCause().iterator(); i.hasNext(); ) {
			StateCause<T, P> cause = i.next();
			
			printStateOrigin(out, cause, state);
			if (i.hasNext()) {
				out.print(" ");
			}
		}

		if (state.getCurrentPosition() == state.getProduction().size() && state.getOriginPosition().isClean() && state.getChart().getInputPosition() == charts.lastKey()) {
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
		out.printf("<span class='state-ref' data-target='state-%s-%s'>", state.getChart().getInputPosition(), getStateId(state));
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
		} else if (origin instanceof StateCause.Complete) {
			StateCause.Complete<T, P> complete = (StateCause.Complete<T, P>) origin;
			out.print("complete ");
			printStateReference(out, complete.getChildState(), observer);
			out.print(" and ");
			printStateReference(out, complete.getPreState(), observer);
		} else {
			out.print("unknown");
		}
		out.print(")</span>");
	}
}
