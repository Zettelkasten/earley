package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Person implements FormProperty {

	First("1"), Second("2"), Third("3");
	
	public static final ValuesPropertyType<Person> TYPE = new ValuesPropertyType<>("p", values());
	
	private final String shortName;

	private Person(String shortName) {
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
	public ValuesPropertyType<Person> getType() {
		return TYPE;
	}
}
