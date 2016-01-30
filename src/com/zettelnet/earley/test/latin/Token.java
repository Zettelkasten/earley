package com.zettelnet.earley.test.latin;

import java.util.Arrays;
import java.util.Collection;

public class Token {

	private final String content;
	private final Collection<Determination> determinations;

	public Token(final String content, final Determination... determinations) {
		this(content, Arrays.asList(determinations));
	}

	public Token(final String content, final Collection<Determination> determinations) {
		this.content = content;
		this.determinations = determinations;
	}

	public String getContent() {
		return content;
	}

	public Collection<Determination> getDeterminations() {
		return determinations;
	}
	
	@Override
	public String toString() {
		return content;
	}
}
