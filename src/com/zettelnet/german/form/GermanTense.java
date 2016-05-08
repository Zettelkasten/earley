package com.zettelnet.german.form;

public enum GermanTense implements GermanFormProperty {

	Present("Pre"), Future("Fut"), Perfect("Per"), Pluperfect("Plu"), FuturePerfect("FuP");

	private final String shortName;

	private GermanTense(String shortName) {
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
