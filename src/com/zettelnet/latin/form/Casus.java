package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Casus implements FormProperty {

	Nominative("Nom"), Genitive("Gen"), Dative("Dat"), Accusative("Acc"), Ablative("Abl"), Vocative("Voc"), Locative("Loc");

	public static final ValuesPropertyType<Casus> TYPE = new ValuesPropertyType<>("k", values());
	
	private final String shortName;

	private Casus(final String shortName) {
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
	public ValuesPropertyType<Casus> getType() {
		return TYPE;
	}
}
