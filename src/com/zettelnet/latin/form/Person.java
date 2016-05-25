package com.zettelnet.latin.form;

public enum Person implements FormProperty {

	First("1"), Second("2"), Third("3");
	
	public static final FormPropertyType<Person> TYPE = new FormPropertyType<>("Person", values());
	
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
	public FormPropertyType<Person> getType() {
		return TYPE;
	}
}
