package com.zettelnet.earley.param;

import com.zettelnet.earley.symbol.Symbol;

/**
 * Represents a parameter manager that provides functions for copying and
 * deriving parameters.
 * <p>
 * Parameters have different behavior depending to what {@link Symbol} they are
 * applied.
 * 
 * @author Zettelkasten
 *
 * @param <T>
 *            The type of Tokens to be used
 * @param <P>
 *            The type of Parameter to be handled
 */
public interface ParameterManager<T, P extends Parameter> extends ParameterFactory<T, P> {

	P copyParameter(P source, Symbol<T> symbol);

	P copyParameter(P source, P with, Symbol<T> symbol);

	P scanParameter(P parameter, P tokenParameter);

	boolean isCompatible(P parent, P child);
}
