package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Voice implements FormProperty {

	Active("Act"), Passive("Pas");

	public static final ValuesPropertyType<Voice> TYPE = new ValuesPropertyType<>("v", values());
	
	private final String shortName;

	private Voice(String shortName) {
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
	public ValuesPropertyType<Voice> getType() {
		return TYPE;
	}
}
