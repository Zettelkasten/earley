package com.zettelnet.german.lemma.property;

import com.zettelnet.earley.param.property.SimplePropertyType;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public enum GermanFiniteness implements LemmaProperty {

	Finite("Fin"), Infinitive("Inf"), Participle("Part");

	public static final Object TYPE = new SimplePropertyType("fin");
	
	private final String shortName;

	private GermanFiniteness(String shortName) {
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
