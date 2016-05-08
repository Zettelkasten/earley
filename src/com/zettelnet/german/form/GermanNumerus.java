package com.zettelnet.german.form;

public enum GermanNumerus implements GermanFormProperty {

	Singular("Sg"), Plural("Pl");

	private final String shortName;

	private GermanNumerus(String shortName) {
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
