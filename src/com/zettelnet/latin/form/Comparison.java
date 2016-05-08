package com.zettelnet.latin.form;

public enum Comparison implements FormProperty {

	Positive("Pos"), Comparative("Comp"), Superlative("Super");

	private final String shortName;

	private Comparison(String shortName) {
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
