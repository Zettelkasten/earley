package com.zettelnet.earley.param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.zettelnet.earley.symbol.Terminal;

public class SpecificParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P> {

	private final ParameterManager<P> manager;
	private final TokenParameterizer<T, P> parameterizer;

	private final P specifiedParameter;

	public SpecificParameterExpression(final ParameterManager<P> manager, final TokenParameterizer<T, P> parameterizer, final P parameter) {
		this.manager = manager;
		this.parameterizer = parameterizer;

		this.specifiedParameter = parameter;
	}

	@Override
	public Collection<P> predict(P parentParameter, P childParameter) {
		if (manager.isCompatible(specifiedParameter, childParameter)) {
			P result = manager.copyParameter(specifiedParameter, childParameter);
			return Arrays.asList(result);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<P> scan(P parentParameter, T token, Terminal<T> terminal) {
		Collection<P> results = new ArrayList<>();
		for (P tokenParameter : parameterizer.getTokenParameters(token, terminal)) {
			if (manager.isCompatible(specifiedParameter, tokenParameter)) {
				P result = manager.scanParameter(parentParameter, tokenParameter);
				results.add(result);
			}
		}
		return results;
	}

	@Override
	public Collection<P> complete(P parentParameter, P childParameter) {
		if (manager.isCompatible(specifiedParameter, childParameter)) {
			return Arrays.asList(manager.copyParameter(parentParameter));
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public String toString() {
		return specifiedParameter.toString();
	}
}
