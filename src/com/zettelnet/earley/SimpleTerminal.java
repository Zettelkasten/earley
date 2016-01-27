package com.zettelnet.earley;

public abstract class SimpleTerminal<T> implements Terminal<T> {

	private final String name;

	public SimpleTerminal() {
		this(null);
	}

	public SimpleTerminal(final String name) {
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
