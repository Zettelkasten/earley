package com.zettelnet.earley.symbol;

public class SimpleNonTerminal<T> implements NonTerminal<T> {

	private final String name;

	public SimpleNonTerminal() {
		this(null);
	}

	public SimpleNonTerminal(final String name) {
		if (name != null) {
			this.name = name;
		} else {
			this.name = super.toString();
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
