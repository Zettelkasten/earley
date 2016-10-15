package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanDegree implements GermanFormProperty {

	Positive("Pos"), Comparative("Comp"), Superlative("Super");

	public static final ValuesPropertyType<GermanDegree> TYPE = new ValuesPropertyType<>("degree", values());

	private final String shortName;

	private GermanDegree(String shortName) {
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
	public ValuesPropertyType<GermanDegree> getType() {
		return TYPE;
	}
}
