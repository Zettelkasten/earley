package com.zettelnet.latin.derivation;

public interface DerivationType {

	public static final DerivationType Infinitive = new SimpleDerivationType("Infinitive");
	public static final DerivationType Participle = new SimpleDerivationType("Participle");
	public static final DerivationType Supine = new SimpleDerivationType("Supine");
}
