package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanComparison implements GermanFormProperty {

	Positive("Pos"), Comparative("Comp"), Superlative("Super");

	public static final ValuesPropertyType<GermanComparison> TYPE = new ValuesPropertyType<>("comp", values());

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

	@Override
	public ValuesPropertyType<GermanComparison> getType() {
		return TYPE;
	}
}
