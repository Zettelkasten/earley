package com.zettelnet.latin.derivation;

public class SimpleDerivationType implements DerivationType {

	private final String name;
	
	public SimpleDerivationType(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
