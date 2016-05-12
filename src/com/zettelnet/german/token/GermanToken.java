package com.zettelnet.german.token;

import java.util.Arrays;
import java.util.Collection;

public class GermanToken {

	private final String content;
	private final Collection<GermanDetermination> determinations;

	public GermanToken(final String content, final GermanDetermination... determinations) {
		this(content, Arrays.asList(determinations));
	}

	public GermanToken(final String content, final Collection<GermanDetermination> determinations) {
		this.content = content;
		this.determinations = determinations;
	}

	public String getContent() {
		return content;
	}

	public Collection<GermanDetermination> getDeterminations() {
		return determinations;
	}
	
	@Override
	public String toString() {
		return content;
	}
}
