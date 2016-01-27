package com.zettelnet.earley;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;

public class SimpleState<T, P extends Parameter> implements State<T, P> {

	private final Chart<T, P> chart;

	private final Production<T, P> production;
	private final int currentPosition;
	private final int originPosition;

	private final P parameter;

	private final Collection<StateCause<T, P>> cause = new ArrayList<>(2);

	public SimpleState(final Chart<T, P> chart, final Production<T, P> production, final int currentPosition, final int originPosition, final P parameter) {
		this.chart = chart;
		this.production = production;
		this.currentPosition = currentPosition;
		this.originPosition = originPosition;
		this.parameter = parameter;
	}

	public Chart<T, P> getChart() {
		return chart;
	}

	public Production<T, P> getProduction() {
		return production;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public int getOriginPosition() {
		return originPosition;
	}

	public Collection<StateCause<T, P>> getCause() {
		return cause;
	}

	public void addCause(StateCause<T, P> newCause) {
		this.cause.add(newCause);
	}

	public Symbol<T> next() {
		if (currentPosition >= production.size()) {
			return null;
		} else {
			return production.get(currentPosition);
		}
	}

	public ParameterExpression<T, P> nextParameterExpression() {
		if (currentPosition >= production.size()) {
			return null;
		} else {
			return production.getParameterExpression(currentPosition);
		}
	}

	public P getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(production.key());
		str.append("<");
		str.append(parameter);
		str.append(">");
		str.append(" ->");

		List<Symbol<T>> productionValues = production.values();
		if (currentPosition == 0) {
			str.append(" .");
		}
		if (productionValues.size() == 0) {
			str.append(" [empty]");
		} else {
			for (int i = 0; i < productionValues.size(); i++) {
				str.append(" ");
				str.append(productionValues.get(i));

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
		result = prime * result + originPosition;
		result = prime * result + ((chart == null) ? 0 : chart.hashCode());
		result = prime * result + ((production == null) ? 0 : production.hashCode());
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
