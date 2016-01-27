package com.zettelnet.earley;

import java.util.Collection;
import java.util.Collections;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;

public class SeedState<T, P extends Parameter> implements State<T, P> {

	private final Chart<T, P> chart;
	
	private final NonTerminal<T> symbol;
	private final P parameter;
	private final ParameterExpression<T, P> parameterExpression;
	
	public SeedState(Chart<T, P> chart, NonTerminal<T> startSymbol, P startSymbolParameter, ParameterExpression<T, P> startSymbolParameterExpression) {
		this.chart = chart;
		this.symbol = startSymbol;
		this.parameter = startSymbolParameter;
		this.parameterExpression = startSymbolParameterExpression;
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
	public int getOriginPosition() {
		return 0;
	}

	@Override
	public Collection<StateCause<T, P>> getCause() {
		return Collections.emptySet();
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
	public ParameterExpression<T, P> nextParameterExpression() {
		return parameterExpression;
	}

	@Override
	public P getParameter() {
		return parameter;
	}

}
