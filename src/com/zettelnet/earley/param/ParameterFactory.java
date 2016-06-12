package com.zettelnet.earley.param;

import com.zettelnet.earley.symbol.Symbol;

@FunctionalInterface
public interface ParameterFactory<T, P extends Parameter> {

	P makeParameter(Symbol<T> symbol);
}
