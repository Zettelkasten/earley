package com.zettelnet.latin.lemma.simple.conjugation;

import com.zettelnet.latin.lemma.Lemma;

public interface ConjugableLemma extends Lemma {

	String getStem(ConjugationStem stemType);
}
