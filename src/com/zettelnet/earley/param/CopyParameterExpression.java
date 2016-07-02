package com.zettelnet.earley.param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

public class CopyParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P>, ParameterFunction<T, P> {

	private final ParameterManager<T, P> manager;
	private final TokenParameterizer<T, P> parameterizer;

	public CopyParameterExpression(final Grammar<T, P> grammar, final TokenParameterizer<T, P> parameterizer) {
		this(grammar.getParameterManager(), parameterizer);
	}

	// parameterizer may be null if this expr is not used for terminal scanning
	public CopyParameterExpression(final ParameterManager<T, P> manager, final TokenParameterizer<T, P> parameterizer) {
		this.manager = manager;
		this.parameterizer = parameterizer;
	}

	@Override
	public Collection<P> predict(P parentParameter, P childParameter, NonTerminal<T> childSymbol) {
		if (manager.isCompatible(parentParameter, childParameter)) {
			P result = manager.copyParameter(parentParameter, childParameter, childSymbol);
			return Arrays.asList(result);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<P> scan(P parentParameter, NonTerminal<T> parentSymbol, T token, Terminal<T> terminal) {
		Collection<P> results = new ArrayList<>();
		for (P tokenParameter : parameterizer.getTokenParameters(token, terminal)) {
			if (manager.isCompatible(parentParameter, tokenParameter)) {
				P result = manager.copyParameter(parentParameter, tokenParameter, parentSymbol);
				results.add(result);
			}
		}
		return results;
	}

	@Override
	public Collection<P> complete(P parentParameter, NonTerminal<T> parentSymbol, P childParameter) {
		if (manager.isCompatible(parentParameter, childParameter)) {
			P result = manager.copyParameter(parentParameter, childParameter, parentSymbol);
			return Arrays.asList(result);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public String toString() {
		return "&pi;";
	}

	@Override
	public P passParameter(P parameter, Symbol<T> symbol) {
		return manager.copyParameter(parameter, symbol);
	}
}
