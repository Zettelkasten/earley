package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Tense implements FormProperty {

	Present("Pre"), Imperfect("Imp"), Future("Fut"), Perfect("Per"), Pluperfect("Plu"), FuturePerfect("FuP");
	
	public static final ValuesPropertyType<Tense> TYPE = new ValuesPropertyType<>("t", values());
	
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
	public ValuesPropertyType<Tense> getType() {
		return TYPE;
	}
}
