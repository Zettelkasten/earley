package com.zettelnet.latin.form;

public enum Person implements FormProperty {

	First("1"), Second("2"), Third("3");

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
}
