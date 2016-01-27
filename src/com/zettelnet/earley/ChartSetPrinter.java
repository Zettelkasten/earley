package com.zettelnet.earley;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;

public class ChartSetPrinter<T, P extends Parameter> {

	private final SortedMap<InputPosition<T>, Chart<T, P>> charts;

	private final Map<Chart<T, P>, Map<State<T, P>, Integer>> stateIds;

	public ChartSetPrinter(final SortedMap<InputPosition<T>, Chart<T, P>> charts) {
		this.charts = charts;

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
		out.printf("<div class='chart col-md-4' id='chart-%s'>", chart.getInputPosition());
		out.printf("<h2 class='chart-title'>S(%s)</h2>", chart.getInputPosition());

		out.print("<ol class='chart-states'>");
		for (State<T, P> state : chart) {
			printState(out, state);
		}
		out.print("</ol>");

		out.print("</div>");
	}

	public void printState(PrintStream out, State<T, P> state) {
		out.printf("<li class='state' id='state-%s-%s'>", state.getChart().getInputPosition(), getStateId(state));

		Production<T, P> production = state.getProduction();
		printSymbol(out, production.key());
		printParameter(out, state.getParameter());

		out.print(" &#8594;");

		List<Symbol<T>> values = production.values();

		if (values.size() > 0) {
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
			out.print(" &epsilon;");
		}

		out.print(", ");
		out.printf("<span class='state-originpos'>%s</span>", state.getOriginPosition());

		for (StateCause<T, P> cause : state.getCause()) {
			out.print(" ");
			printStateOrigin(out, cause, state);
		}

		if (state.getCurrentPosition() == state.getProduction().size() && state.getOriginPosition().isClean() && state.getChart().getInputPosition() == charts.lastKey()) {
			out.print(" DONE!");
		}

		out.print("</li>");
	}

	public void printParameter(PrintStream out, P parameter) {
		out.printf("(&alpha; : %s)", parameter);
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
				printStateReference(out, predict.getFromState(), observer);
			}
		} else if (origin instanceof StateCause.Scan) {
			StateCause.Scan<T, P> scan = (StateCause.Scan<T, P>) origin;
			out.print("scan ");
			printStateReference(out, scan.getFromState(), observer);
		} else if (origin instanceof StateCause.Complete) {
			StateCause.Complete<T, P> complete = (StateCause.Complete<T, P>) origin;
			out.print("complete ");
			printStateReference(out, complete.getFromState(), observer);
			out.print(" and ");
			printStateReference(out, complete.getWithState(), observer);
		} else {
			out.print("unknown");
		}
		out.print(")</span>");
	}
}
