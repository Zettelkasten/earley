package com.zettelnet.earley.test.latin;

import com.zettelnet.earley.symbol.SimpleTerminal;
import com.zettelnet.latin.lemma.LemmaType;

public class LemmaTerminal extends SimpleTerminal<Token> {

	private final LemmaType type;

	public LemmaTerminal(final LemmaType type) {
		this (type, type.toString());
	}

	public LemmaTerminal(final LemmaType type, final String name) {
		super(name);
		this.type = type;
	}

	@Override
	public boolean isCompatibleWith(Token token) {
		for (Determination determination : token.getDeterminations()) {
			if (isCompatibleWith(determination)) {
				return true;
			}
		}
		return false;
	}

	public boolean isCompatibleWith(Determination determination) {
		return determination.getLemmaType().equals(type);
	}
}
