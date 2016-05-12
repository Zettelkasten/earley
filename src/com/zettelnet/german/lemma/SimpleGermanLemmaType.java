package com.zettelnet.german.lemma;

public class SimpleGermanLemmaType implements GermanLemmaType {

	private final String name;
	
	public SimpleGermanLemmaType(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
