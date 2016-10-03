package com.zettelnet.earley.param;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;

public class CopyParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P>, ParameterFunction<T, P> {

	private final ParameterManager<T, P> manager;

	public CopyParameterExpression(final Grammar<T, P> grammar) {
		this(grammar.getParameterManager());
	}

	public CopyParameterExpression(final ParameterManager<T, P> manager) {
		this.manager = manager;
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
