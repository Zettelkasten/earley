package com.zettelnet.earley.param;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.symbol.NonTerminal;

/**
 * Represents a {@link ParameterExpression} that does not modify parameters.
 * This means
 * <ul>
 * <li>on <i>prediction</i>, the parent parameter is ignored.</li>
 * <li>on <i>scanning</i>, the token parameter is ignored.</li>
 * <li>on <i>completing</i>, the child parameter is ignored.</li>
 * </ul>
 * 
 * @author Zettelkasten
 *
 * @param <T>
 *            The type of Tokens to be used
 * @param <P>
 * 			  The type of Parameter to be handled
 */
public class AnyParameterExpression<T, P extends Parameter> implements ParameterExpression<T, P> {

	private final ParameterManager<T, P> manager;

	public AnyParameterExpression(final Grammar<T, P> grammar) {
		this(grammar.getParameterManager());
	}

	public AnyParameterExpression(final ParameterManager<T, P> manager) {
		this.manager = manager;
	}

	@Override
	public Collection<P> predict(P parentParameter, P childParameter, NonTerminal<T> childSymbol) {
		return Arrays.asList(manager.copyParameter(childParameter, childSymbol));
	}

	@Override
	public Collection<P> complete(P parentParameter, NonTerminal<T> parentSymbol, P childParameter) {
		return Arrays.asList(manager.copyParameter(parentParameter, parentSymbol));
	}

	@Override
	public String toString() {
		return "?";
	}
}
