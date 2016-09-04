package com.zettelnet.german.lemma.simple.conjugation;

import java.util.Collection;

import com.zettelnet.german.lemma.GermanLemma;

public interface ConjugableGermanLemma extends GermanLemma {

	Collection<String> getInfinitive();
	
	Collection<String> getStem(GermanConjugationStem stem);
}
