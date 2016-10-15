package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Degree implements FormProperty {

	Positive("Pos"), Comparative("Comp"), Superlative("Super");

	public static final ValuesPropertyType<Degree> TYPE = new ValuesPropertyType<>("degree", values());
	
	private final String shortName;

	private Degree(String shortName) {
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
	public ValuesPropertyType<Degree> getType() {
		return TYPE;
	}
}
