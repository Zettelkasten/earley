package com.zettelnet.earley.param;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.symbol.Terminal;

/*
 * Does not care about anything.
 */
public class AnyParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P> {

	private final ParameterManager<P> manager;
	
	public AnyParameterExpression(final Grammar<T, P> grammar) {
		this(grammar.getParameterManager());
	}
	
	public AnyParameterExpression(final ParameterManager<P> manager) {
		this.manager = manager;
	}
	
	@Override
	public Collection<P> predict(P parameter, P childParameter) {
		return Arrays.asList(manager.copyParameter(childParameter));
	}

	@Override
	public Collection<P> scan(P parameter, T token, Terminal<T> terminal) {
		return Arrays.asList(manager.copyParameter(parameter));
	}

	@Override
	public Collection<P> complete(P parameter, P childParameter) {
		return Arrays.asList(manager.copyParameter(parameter));
	}
	
	@Override
	public String toString() {
		return "?";
	}
}
