package com.zettelnet.earley.param;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.earley.Terminal;

public class SpecificParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P> {

	private final ParameterManager<P> manager;
	private final P parameter;

	public SpecificParameterExpression(final ParameterManager<P> manager, final P parameter) {
		this.manager = manager;
		this.parameter = parameter;
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
		return parameter.toString();
	}
}
