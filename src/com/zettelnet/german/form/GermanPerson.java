package com.zettelnet.german.form;

public enum GermanPerson implements GermanFormProperty {

	First("1"), Second("2"), Third("3");

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
}
