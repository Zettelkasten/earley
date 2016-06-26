package com.zettelnet.latin.form;

public enum Mood implements FormProperty {

	Indicative("Ind"), Subjunctive("Sub"), Imperative("Imp");
	
	public static final FormPropertyType<Mood> TYPE = new FormPropertyType<>("m", values());
	
	private final String shortName;

	private Mood(String shortName) {
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
	public FormPropertyType<Mood> getType() {
		return TYPE;
	}
}
