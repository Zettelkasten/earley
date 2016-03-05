package com.zettelnet.latin.form;

public enum Genus implements FormProperty {

	Masculine("m"), Feminine("f"), Neuter("n");

	private final String shortName;

	private Genus(final String shortName) {
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
