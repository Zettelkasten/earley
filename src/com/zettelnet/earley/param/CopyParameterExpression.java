package com.zettelnet.earley.param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.Terminal;

public class CopyParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P> {

	private final ParameterManager<P> manager;
	private final TokenParameterizer<T, P> parameterizer;

	public CopyParameterExpression(final Grammar<T, P> grammar, final TokenParameterizer<T, P> parameterizer) {
		this(grammar.getParameterManager(), parameterizer);
	}

	// parameterizer may be null if this expr is not used for terminal scanning
	public CopyParameterExpression(final ParameterManager<P> manager, final TokenParameterizer<T, P> parameterizer) {
		this.manager = manager;
		this.parameterizer = parameterizer;
	}

	@Override
	public Collection<P> scan(P parameter, T token, Terminal<T> terminal) {
		Collection<P> results = new ArrayList<>();
		for (P tokenParameter : parameterizer.getTokenParameters(token, terminal)) {
			if (manager.isCompatible(parameter, tokenParameter)) {
				P result = manager.copyParameter(parameter, tokenParameter);
				results.add(result);
			}
		}
		return results;
	}

	@Override
	public Collection<P> complete(P parameter, P childParameter) {
		if (manager.isCompatible(parameter, childParameter)) {
			P result = manager.copyParameter(parameter, childParameter);
			return Arrays.asList(result);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public String toString() {
		return "&alpha;";
	}
}
