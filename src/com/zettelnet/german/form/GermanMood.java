package com.zettelnet.german.form;

public enum GermanMood implements GermanFormProperty {

	Indicative("Ind"), Subjunctive("Sub"), Imperative("Imp");

	private final String shortName;

	private GermanMood(String shortName) {
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
