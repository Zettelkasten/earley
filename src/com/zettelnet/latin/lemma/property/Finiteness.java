package com.zettelnet.latin.lemma.property;

public enum Finiteness implements LemmaProperty {

	Finite("Fin"), Infinitive("Inf"), Participle("Part"), Gerund("Gerund"), Gerundive("Gerundiv"), Supine("Sup");

	public static final Class<? extends Finiteness> TYPE = Finiteness.class;
	
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
	
	@Override
	public Class<? extends Finiteness> getType() {
		return TYPE;
	}
}
