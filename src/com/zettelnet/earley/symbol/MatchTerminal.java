package com.zettelnet.earley.symbol;

public class MatchTerminal<T> implements Terminal<T> {

	private final T expected;

	public MatchTerminal(final T expected) {
		this.expected = expected;
	}

	@Override
	public boolean isCompatibleWith(T token) {
		return expected.equals(token);
	}

	@Override
	public String toString() {
		return expected.toString();
	}
}
