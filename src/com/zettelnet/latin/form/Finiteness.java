package com.zettelnet.latin.form;

public enum Finiteness implements FormProperty {

	Finite("Fin"), Infinitive("Inf"), Participle("Part"), Gerund("Gerund"), Gerundive("Gerundiv"), Supine("Sup");

	private final String shortName;

	private Finiteness(String shortName) {
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
