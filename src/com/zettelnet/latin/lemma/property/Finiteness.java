package com.zettelnet.latin.lemma.property;

import com.zettelnet.earley.param.property.SimplePropertyType;

public enum Finiteness implements LemmaProperty {

	Finite("Fin"), Infinitive("Inf"), Participle("Part"), Gerund("Gerund"), Gerundive("Gerundiv"), Supine("Sup");

	public static final Object TYPE = new SimplePropertyType("fin");
	
	private final String shortName;

	private Finiteness(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public String shortName() {
		return shortName;
	}

	@Override
	public String toString() {
		return shortName();
	}
	
	@Override
	public Object getType() {
		return TYPE;
	}
}
