package com.zettelnet.earley;

import java.util.List;

import com.zettelnet.earley.param.Parameter;

public class EarleyParser<T, P extends Parameter> implements GrammarParser<T, P> {

	private final Grammar<T, P> grammar;

	public EarleyParser(final Grammar<T, P> grammar) {
		this.grammar = grammar;
	}

	public Grammar<T, P> getGrammar() {
		return grammar;
	}

	@Override
	public EarleyParseResult<T, P> parse(final List<T> tokens) {
		return new EarleyParseResult<T, P>(this, tokens);
	}
}
