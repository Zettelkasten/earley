package com.zettelnet.earley.latin;

import java.util.List;

import com.zettelnet.earley.Grammar;

public class LatinParser<T> {

	private final Grammar grammar;

	public LatinParser(final Grammar grammar) {
		this.grammar = grammar;
	}

	public Grammar getGrammar() {
		return grammar;
	}

	public LatinParseResult<T> parse(final List<T> tokens) {
		return new LatinParseResult<T>(this, tokens);
	}
}
