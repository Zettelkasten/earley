package com.zettelnet.german.lemma.simple.conjugation;

import com.zettelnet.german.lemma.CombinedGermanProvider;
import com.zettelnet.german.lemma.SimpleCombinedGermanProvider;

public final class GermanConjugation {

	private GermanConjugation() {
	}

	public static final CombinedGermanProvider<ConjugableGermanLemma> Weak = new SimpleCombinedGermanProvider<ConjugableGermanLemma>(new WeakGermanConjugation(), null);
}
