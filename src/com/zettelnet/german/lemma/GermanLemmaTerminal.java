package com.zettelnet.german.lemma;

import com.zettelnet.earley.symbol.SimpleTerminal;
import com.zettelnet.german.token.GermanDetermination;
import com.zettelnet.german.token.GermanToken;

public class GermanLemmaTerminal extends SimpleTerminal<GermanToken> {

	private final GermanLemmaType type;

	public GermanLemmaTerminal(final GermanLemmaType type) {
		this(type, type.toString());
	}

	public GermanLemmaTerminal(final GermanLemmaType type, final String name) {
		super(name);
		this.type = type;
	}

	@Override
	public boolean isCompatibleWith(GermanToken token) {
		for (GermanDetermination determination : token.getDeterminations()) {
			if (isCompatibleWith(determination)) {
				return true;
			}
		}
		return false;
	}

	public boolean isCompatibleWith(GermanDetermination determination) {
		return determination.getLemmaType().equals(type);
	}
}
