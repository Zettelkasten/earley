package com.zettelnet.latin.form;

public enum Voice implements FormProperty {

	Active("Act"), Passive("Pas");

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
}
