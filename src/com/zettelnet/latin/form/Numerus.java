package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Numerus implements FormProperty {

	Singular("Sg"), Plural("Pl");

	public static final ValuesPropertyType<Numerus> TYPE = new ValuesPropertyType<>("n", values());
	
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
	public ValuesPropertyType<Numerus> getType() {
		return TYPE;
	}
}
