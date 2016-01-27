package com.zettelnet.earley.param;

public class SimpleParameterFactory<P extends Parameter> implements ParameterFactory<P> {

	private final P value;

	public SimpleParameterFactory(final P value) {
		this.value = value;
	}

	@Override
	public P makeParameter() {
		return value;
	}

}
