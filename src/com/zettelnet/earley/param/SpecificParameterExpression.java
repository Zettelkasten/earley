package com.zettelnet.earley.param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

public class SpecificParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P> {

	private final ParameterManager<T, P> manager;
	private final TokenParameterizer<T, P> parameterizer;

	// either use a concrete parameter value or provide a factory that returns
	// one (and can react to different symbol types)
	private final P concreteParameter;
	private final ParameterFactory<T, P> abstractParameter;

	public SpecificParameterExpression(final ParameterManager<T, P> manager, final TokenParameterizer<T, P> parameterizer, final P parameter) {
		this.manager = manager;
		this.parameterizer = parameterizer;

		this.concreteParameter = parameter;
		this.abstractParameter = null;
	}

	public SpecificParameterExpression(final ParameterManager<T, P> manager, final TokenParameterizer<T, P> parameterizer, final ParameterFactory<T, P> parameter) {
		this.manager = manager;
		this.parameterizer = parameterizer;

		this.concreteParameter = null;
		this.abstractParameter = parameter;
	}

	private P getSpecifiedParameter(Symbol<T> symbol) {
		if (concreteParameter == null) {
			return abstractParameter.makeParameter(symbol);
		} else {
			return concreteParameter;
		}
	}

	@Override
	public Collection<P> predict(P parentParameter, P childParameter, NonTerminal<T> childSymbol) {
		P specifiedParameter = getSpecifiedParameter(childSymbol);
		if (manager.isCompatible(specifiedParameter, childParameter)) {
			P result = manager.copyParameter(specifiedParameter, childParameter, childSymbol);
			return Arrays.asList(result);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<P> scan(P parentParameter, NonTerminal<T> parentSymbol, T token, Terminal<T> terminal) {
		P specifiedParameter = getSpecifiedParameter(parentSymbol);
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
	public Collection<P> complete(P parentParameter, NonTerminal<T> parentSymbol, P childParameter) {
		P specifiedParameter = getSpecifiedParameter(parentSymbol);
		if (manager.isCompatible(specifiedParameter, childParameter)) {
			return Arrays.asList(manager.copyParameter(parentParameter, parentSymbol));
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public String toString() {
		if (concreteParameter == null) {
			return abstractParameter.toString();
		} else {
			return concreteParameter.toString();
		}
	}
}
