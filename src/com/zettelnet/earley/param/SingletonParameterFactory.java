package com.zettelnet.earley.param;

public class SingletonParameterFactory<P extends Parameter> implements ParameterFactory<P> {

	private final P value;

	public SingletonParameterFactory(final P value) {
		this.value = value;
	}

	@Override
	public P makeParameter() {
		return value;
	}

}
