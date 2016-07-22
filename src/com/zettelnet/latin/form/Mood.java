package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Mood implements FormProperty {

	Indicative("Ind"), Subjunctive("Sub"), Imperative("Imp");
	
	public static final ValuesPropertyType<Mood> TYPE = new ValuesPropertyType<>("m", values());
	
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
	public ValuesPropertyType<Mood> getType() {
		return TYPE;
	}
}
