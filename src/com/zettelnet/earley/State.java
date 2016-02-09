package com.zettelnet.earley;

import java.util.List;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.symbol.Symbol;

public interface State<T, P extends Parameter> {

	Chart<T, P> getChart();
	
	Production<T, P> getProduction();
	
	int getCurrentPosition();
	
	InputPosition<T> getOriginPosition();
	
	List<StateCause<T, P>> getCause();
	
	void addCause(StateCause<T, P> newCause);
	
	Symbol<T> next();
	
	ParameterExpression<T, P> nextParameterExpression();
	
	P getParameter();
}
