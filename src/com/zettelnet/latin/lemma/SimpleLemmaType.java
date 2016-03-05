package com.zettelnet.latin.lemma;

public class SimpleLemmaType implements LemmaType {

	private final String name;
	
	public SimpleLemmaType(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
