package com.zettelnet.german.form;

public enum GermanComparison implements GermanFormProperty {

	Positive("Pos"), Comparative("Comp"), Superlative("Super");

	private final String shortName;

	private GermanComparison(String shortName) {
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
