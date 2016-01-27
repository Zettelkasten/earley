package com.zettelnet.earley;

import java.util.function.Predicate;

public class PredicateTerminal<T> implements Terminal<T> {

	private final Predicate<T> predicate;
	private final String name;

	public PredicateTerminal(final Predicate<T> predicate) {
		this(null, predicate);
	}

	public PredicateTerminal(final String name, final Predicate<T> predicate) {
		this.predicate = predicate;

		if (name != null) {
			this.name = name;
		} else {
			this.name = super.toString();
		}
	}

	@Override
	public boolean isCompatibleWith(T token) {
		return predicate.test(token);
	}

	@Override
	public String toString() {
		return name;
	}
}
