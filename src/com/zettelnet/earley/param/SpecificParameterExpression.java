package com.zettelnet.earley.param;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.zettelnet.earley.symbol.Terminal;

public class SpecificParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P> {

	private final ParameterManager<P> manager;
	private final TokenParameterizer<T, P> parameterizer;

	private final P parameter;

	public SpecificParameterExpression(final ParameterManager<P> manager, final TokenParameterizer<T, P> parameterizer, final P parameter) {
		this.manager = manager;
		this.parameterizer = parameterizer;

		this.parameter = parameter;
	}

	@Override
	public Collection<P> predict(P parentParameter, P childParameter) {
		if (manager.isCompatible(parameter, childParameter)) {
			P result = manager.copyParameter(parameter, childParameter);
			return Arrays.asList(result);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<P> scan(P parentParameter, T token, Terminal<T> terminal) {
		for (P tokenParameter : parameterizer.getTokenParameters(token, terminal)) {
			if (manager.isCompatible(parameter, tokenParameter)) {
				return Arrays.asList(manager.scanParameter(parentParameter, tokenParameter));
			}
		}
		return Collections.emptyList();
	}

	@Override
	public Collection<P> complete(P parentParameter, P childParameter) {
		if (manager.isCompatible(parameter, childParameter)) {
			return Arrays.asList(manager.copyParameter(parentParameter));
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public String toString() {
		return parameter.toString();
	}
}
