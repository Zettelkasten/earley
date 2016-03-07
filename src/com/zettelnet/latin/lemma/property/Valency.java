package com.zettelnet.latin.lemma.property;

public enum Valency implements LemmaProperty {

	Copula("Copula"),
	Null("NullVal"),
	Single("SingleVal"),
	Accusative("AccVal"),
	Dative("DatVal"),
	Genitive("GenVal"),
	AccusativeDative("AccDatVal");

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
}
