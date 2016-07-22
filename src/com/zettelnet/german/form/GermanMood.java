package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanMood implements GermanFormProperty {

	Indicative("Ind"), Subjunctive1("Sub1"), Subjunctive2("Sub2"), Imperative("Imp");

	public static final ValuesPropertyType<GermanMood> TYPE = new ValuesPropertyType<>("m", values());
	
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

	@Override
	public ValuesPropertyType<GermanMood> getType() {
		return TYPE;
	}
}
