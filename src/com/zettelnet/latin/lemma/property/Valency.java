package com.zettelnet.latin.lemma.property;

import com.zettelnet.earley.param.property.SimplePropertyType;

public enum Valency implements LemmaProperty {

	Copula("Copula"),
	Null("NullVal"),
	Single("SingleVal"),
	Accusative("AccVal"),
	Dative("DatVal"),
	Genitive("GenVal"),
	AccusativeDative("AccDatVal"),
	Infinitive("InfVal");

	public static final Object TYPE = new SimplePropertyType("Valency");

	private final String shortName;

	private Valency(String shortName) {
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
