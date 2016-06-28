package com.zettelnet.german.form;

public enum GermanCasus implements GermanFormProperty {

	Nominative("Nom"), Genitive("Gen"), Dative("Dat"), Accusative("Acc");

	private final String shortName;

	private GermanCasus(final String shortName) {
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
