package com.zettelnet.earley;

import java.util.List;

import com.zettelnet.earley.input.InputPositionInitializer;
import com.zettelnet.earley.input.LinearInputPositionInitializer;
import com.zettelnet.earley.param.Parameter;

public class EarleyParser<T, P extends Parameter> implements GrammarParser<T, P> {

	private final Grammar<T, P> grammar;
	private final InputPositionInitializer<T> inputPositionInitializer;

	public EarleyParser(final Grammar<T, P> grammar) {
		this(grammar, new LinearInputPositionInitializer<>());
	}

	public EarleyParser(final Grammar<T, P> grammar, final InputPositionInitializer<T> inputPositionInitializer) {
		this.grammar = grammar;
		this.inputPositionInitializer = inputPositionInitializer;
	}

	public Grammar<T, P> getGrammar() {
		return grammar;
	}

	@Override
	public EarleyParseResult<T, P> parse(final List<T> tokens) {
		return new EarleyParseResult<T, P>(this, tokens, inputPositionInitializer);
	}
}
