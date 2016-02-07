package com.zettelnet.earley.symbol;

public class AnyTokenTerminal<T> extends SimpleTerminal<T> {

	public AnyTokenTerminal() {
		super();
	}

	public AnyTokenTerminal(final String name) {
		super(name);
	}

	@Override
	public boolean isCompatibleWith(T token) {
		return true;
	}
}
