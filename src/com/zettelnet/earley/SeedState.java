package com.zettelnet.earley;

import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;

public class SeedState<T, P extends Parameter> implements State<T, P> {

	private final Chart<T, P> chart;

	private final NonTerminal<T> symbol;
	private final P parameter;
	private final ParameterExpression<T, P> parameterExpression;
	private final double probability;

	public SeedState(Chart<T, P> chart, NonTerminal<T> startSymbol, P startSymbolParameter, ParameterExpression<T, P> startSymbolParameterExpression, double probability) {
		this.chart = chart;
		this.symbol = startSymbol;
		this.parameter = startSymbolParameter;
		this.parameterExpression = startSymbolParameterExpression;
		this.probability = probability;
	}

	@Override
	public Chart<T, P> getChart() {
		return chart;
	}

	@Override
	public Production<T, P> getProduction() {
		return null;
	}

	@Override
	public int getCurrentPosition() {
		return 0;
	}

	@Override
	public InputPosition<T> getOriginPosition() {
		return chart.getInputPosition();
	}

	@Override
	public List<StateCause<T, P>> getCause() {
		return Collections.emptyList();
	}
	
	@Override
	public List<ParameterCause<T, P>> getParameterCause() {
		return Collections.emptyList();
	}

	@Override
	public void addCause(StateCause<T, P> newCause) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Symbol<T> next() {
		return symbol;
	}

	@Override
	public Symbol<T> last() {
		return null;
	}

	@Override
	public ParameterExpression<T, P> nextParameterExpression() {
		return parameterExpression;
	}

	@Override
	public ParameterExpression<T, P> lastParameterExpression() {
		return null;
	}

	@Override
	public P getParameter() {
		return parameter;
	}

	@Override
	public double getProbability() {
		return probability;
	}
}
