package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanPerson implements GermanFormProperty {

	First("1"), Second("2"), Third("3");

	public static final ValuesPropertyType<GermanPerson> TYPE = new ValuesPropertyType<>("p", values());
	
	private final String shortName;

	private GermanPerson(String shortName) {
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
	public ValuesPropertyType<GermanPerson> getType() {
		return TYPE;
	}
}
