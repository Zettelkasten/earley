package com.zettelnet.latin.form;

public enum Casus implements FormProperty {

	Nominative("Nom"), Genitive("Gen"), Dative("Dat"), Accusative("Acc"), Ablative("Abl"), Vocative("Voc"), Locative("Loc");

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
}
