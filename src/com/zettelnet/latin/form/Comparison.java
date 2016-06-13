package com.zettelnet.latin.form;

public enum Comparison implements FormProperty {

	Positive("Pos"), Comparative("Comp"), Superlative("Super");

	public static final FormPropertyType<Comparison> TYPE = new FormPropertyType<>("comp", values());
	
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
	
	@Override
	public FormPropertyType<Comparison> getType() {
		return TYPE;
	}
}
