package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanNumerus implements GermanFormProperty {

	Singular("Sg"), Plural("Pl");

	public static final ValuesPropertyType<GermanNumerus> TYPE = new ValuesPropertyType<>("n", values());
	
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

	@Override
	public ValuesPropertyType<GermanNumerus> getType() {
		return TYPE;
	}
}
