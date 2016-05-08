package com.zettelnet.german.form;

public enum GermanVoice implements GermanFormProperty {

	Active("Act"), Passive("Pas");

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
}
