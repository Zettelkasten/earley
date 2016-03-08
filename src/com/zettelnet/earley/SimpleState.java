package com.zettelnet.earley;

import java.util.ArrayList;
import java.util.List;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.symbol.Symbol;

public class SimpleState<T, P extends Parameter> implements State<T, P> {

	private final Chart<T, P> chart;

	private final Production<T, P> production;
	private final int currentPosition;
	private final InputPosition<T> originPosition;

	private final P parameter;

	private final List<StateCause<T, P>> cause = new ArrayList<>(2);

	public SimpleState(final Chart<T, P> chart, final Production<T, P> production, final int currentPosition, final InputPosition<T> originPosition, final P parameter) {
		assert originPosition != null : "Origin Position cannot be null";

		this.chart = chart;
		this.production = production;
		this.currentPosition = currentPosition;
		this.originPosition = originPosition;
		this.parameter = parameter;
	}

	@Override
	public Chart<T, P> getChart() {
		return chart;
	}

	@Override
	public Production<T, P> getProduction() {
		return production;
	}

	@Override
	public int getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public InputPosition<T> getOriginPosition() {
		return originPosition;
	}

	@Override
	public List<StateCause<T, P>> getCause() {
		return cause;
	}

	@Override
	public void addCause(StateCause<T, P> newCause) {
		this.cause.add(newCause);
	}

	@Override
	public Symbol<T> next() {
		if (currentPosition >= production.size()) {
			return null;
		} else {
			return production.get(currentPosition);
		}
	}

	@Override
	public Symbol<T> last() {
		if (currentPosition > production.size()) {
			return null;
		} else {
			return production.get(currentPosition - 1);
		}
	}

	@Override
	public ParameterExpression<T, P> nextParameterExpression() {
		if (currentPosition >= production.size()) {
			return null;
		} else {
			return production.getParameterExpression(currentPosition);
		}
	}

	@Override
	public P getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(production.key());
		str.append("(pi : ");
		str.append(parameter);
		str.append(")");
		str.append(" ->");

		List<Symbol<T>> productionValues = production.values();
		if (productionValues.size() == 0) {
			str.append(" [empty]");
		} else {
			if (currentPosition == 0) {
				str.append(" .");
			}
			for (int i = 0; i < productionValues.size(); i++) {
				str.append(" ");
				str.append(production.get(i));
				str.append("(");
				str.append(production.getParameterExpression(i));
				str.append(")");

				if (currentPosition == i + 1) {
					str.append(" .");
				}
			}
		}

		str.append(", ");
		str.append(originPosition);

		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPosition;
		result = prime * result + originPosition.hashCode();
		result = prime * result + chart.hashCode();
		result = prime * result + production.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		State<?, ?> other = (State<?, ?>) obj;
		if (currentPosition != other.getCurrentPosition()) {
			return false;
		}
		if (originPosition != other.getOriginPosition()) {
			return false;
		}
		if (!chart.equals(other.getChart())) {
			return false;
		}
		if (!production.equals(other.getProduction())) {
			return false;
		}
		if (!parameter.equals(other.getParameter())) {
			return false;
		}
		return true;
	}
}
