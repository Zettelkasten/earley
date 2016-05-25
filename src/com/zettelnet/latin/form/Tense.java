package com.zettelnet.latin.form;

public enum Tense implements FormProperty {

	Present("Pre"), Imperfect("Imp"), Future("Fut"), Perfect("Per"), Pluperfect("Plu"), FuturePerfect("FuP");
	
	public static final FormPropertyType<Tense> TYPE = new FormPropertyType<>("Tense", values());
	
	private final String shortName;

	private Tense(String shortName) {
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
	public FormPropertyType<Tense> getType() {
		return TYPE;
	}
}
