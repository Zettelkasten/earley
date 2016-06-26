package com.zettelnet.latin.form;

public enum Numerus implements FormProperty {

	Singular("Sg"), Plural("Pl");

	public static final FormPropertyType<Numerus> TYPE = new FormPropertyType<>("n", values());
	
	private final String shortName;

	private Numerus(String shortName) {
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
	public FormPropertyType<Numerus> getType() {
		return TYPE;
	}
}
