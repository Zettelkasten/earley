package com.zettelnet.latin.form;

public enum Tense implements FormProperty {

	Present("Pre"), Imperfect("Imp"), Future("Fut"), Perfect("Per"), Pluperfect("Plu"), FuturePerfect("FuP");

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
}
