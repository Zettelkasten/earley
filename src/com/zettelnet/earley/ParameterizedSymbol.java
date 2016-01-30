package com.zettelnet.earley;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.symbol.Symbol;

/**
 * Tuple from {@link Symbol} and {@link ParameterExpression}s representing right
 * side constituents of {@link Production}s.
 * 
 * @author Zettelkasten
 *
 */
public class ParameterizedSymbol<T, P extends Parameter> {

	private final Symbol<T> symbol;

	private final ParameterExpression<T, P> expression;

	public ParameterizedSymbol(final Symbol<T> symbol, final ParameterExpression<T, P> expression) {
		this.symbol = symbol;
		this.expression = expression;
	}

	public Symbol<T> getSymbol() {
		return symbol;
	}

	public ParameterExpression<T, P> getParameterExpression() {
		return expression;
	}

	@Override
	public String toString() {
		return symbol.toString();
	}
}
