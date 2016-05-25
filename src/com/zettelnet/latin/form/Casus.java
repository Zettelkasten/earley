package com.zettelnet.latin.form;

public enum Casus implements FormProperty {

	Nominative("Nom"), Genitive("Gen"), Dative("Dat"), Accusative("Acc"), Ablative("Abl"), Vocative("Voc"), Locative("Loc");

	public static final FormPropertyType<Casus> TYPE = new FormPropertyType<>("Casus", values());
	
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
	public FormPropertyType<? extends FormProperty> getType() {
		return TYPE;
	}
}
