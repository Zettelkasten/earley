package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Comparison implements FormProperty {

	Positive("Pos"), Comparative("Comp"), Superlative("Super");

	public static final ValuesPropertyType<Comparison> TYPE = new ValuesPropertyType<>("comp", values());
	
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
	public ValuesPropertyType<Comparison> getType() {
		return TYPE;
	}
}
