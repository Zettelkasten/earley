package com.zettelnet.earley.param;

public interface ParameterFactory<P extends Parameter> {

	P makeParameter();
}
