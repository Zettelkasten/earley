package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanVoice implements GermanFormProperty {

	Active("Act"), Passive("Pas");

	public static final ValuesPropertyType<GermanVoice> TYPE = new ValuesPropertyType<>("v", values());
	
	private final String shortName;

	private GermanVoice(String shortName) {
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
	public ValuesPropertyType<GermanVoice> getType() {
		return TYPE;
	}
}
