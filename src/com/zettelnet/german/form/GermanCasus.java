package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanCasus implements GermanFormProperty {

	Nominative("Nom"), Genitive("Gen"), Dative("Dat"), Accusative("Acc");

	public static final ValuesPropertyType<GermanCasus> TYPE = new ValuesPropertyType<>("k", values());

	private final String shortName;

	private GermanCasus(final String shortName) {
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
	public ValuesPropertyType<GermanCasus> getType() {
		return TYPE;
	}
}
