package com.zettelnet.earley.param;

// this parameter does not change state, therefore we can use a singleton
public class DefaultParameter implements Parameter {

	protected static final DefaultParameter INSTANCE = new DefaultParameter();
}
