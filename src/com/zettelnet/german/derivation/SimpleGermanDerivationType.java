package com.zettelnet.german.derivation;

public class SimpleGermanDerivationType implements GermanDerivationType {

	private final String name;
	
	public SimpleGermanDerivationType(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
