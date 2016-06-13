package com.zettelnet.earley.param;

import com.zettelnet.earley.symbol.Symbol;

public class SingletonParameterFactory<T, P extends Parameter> implements ParameterFactory<T, P> {

	private final P value;

	public SingletonParameterFactory(final P value) {
		this.value = value;
	}

	@Override
	public P makeParameter(Symbol<T> symbol) {
		return value;
	}
}
