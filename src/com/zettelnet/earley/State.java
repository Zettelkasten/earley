package com.zettelnet.earley;

import java.util.Collection;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;

public interface State<T, P extends Parameter> {

	Chart<T, P> getChart();
	
	Production<T, P> getProduction();
	
	int getCurrentPosition();
	
	int getOriginPosition();
	
	Collection<StateCause<T, P>> getCause();
	
	void addCause(StateCause<T, P> newCause);
	
	Symbol<T> next();
	
	ParameterExpression<T, P> nextParameterExpression();
	
	P getParameter();
}
