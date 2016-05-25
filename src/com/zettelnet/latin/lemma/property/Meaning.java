package com.zettelnet.latin.lemma.property;

import com.zettelnet.latin.lemma.Lemma;

public class Meaning implements LemmaProperty {

	public static final Class<? extends Meaning> TYPE = Meaning.class;
	
	private final Lemma lemma;

	public Meaning(final Lemma lemma) {
		this.lemma = lemma;
	}

	public Lemma getLemma() {
		return lemma;
	}
}
