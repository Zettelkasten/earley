package com.zettelnet.earley.test.latin;

import com.zettelnet.earley.symbol.SimpleTerminal;
import com.zettelnet.latin.lemma.Lemma;

public class LemmaTerminal extends SimpleTerminal<Token> {

	private final Lemma.Type type;

	public LemmaTerminal(final Lemma.Type type) {
		super(type.toString());
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
