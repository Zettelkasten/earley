package com.zettelnet.german.derivation;

public interface GermanDerivationType {

	public static final GermanDerivationType Infinitive = new SimpleGermanDerivationType("Infinitive");
	public static final GermanDerivationType Participle1 = new SimpleGermanDerivationType("Participle1");
	public static final GermanDerivationType Participle2 = new SimpleGermanDerivationType("Participle2");
}
