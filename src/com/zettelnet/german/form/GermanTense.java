package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanTense implements GermanFormProperty {

	Present("Pre"), Future("Fut"), Past("Past"), Perfect("Per"), Pluperfect("Plu"), FuturePerfect("FuP");

	public static final ValuesPropertyType<GermanTense> TYPE = new ValuesPropertyType<>("t", values());
	
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

	@Override
	public ValuesPropertyType<GermanTense> getType() {
		return TYPE;
	}
}
