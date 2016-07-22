package com.zettelnet.latin.lemma.property;

import com.zettelnet.earley.param.property.SimplePropertyType;
import com.zettelnet.latin.lemma.Lemma;

public class Meaning implements LemmaProperty {

	public static final Object TYPE = new SimplePropertyType("mean");
	
	private final Lemma lemma;

	public Meaning(final Lemma lemma) {
		this.lemma = lemma;
	}

	public Lemma getLemma() {
		return lemma;
	}
	
	@Override
	public Object getType() {
		return TYPE;
	}
	
	@Override
	public String toString() {
		return lemma.toString();
	}
}
