package com.zettelnet.latin.derivation;

import java.util.Map;

import com.zettelnet.latin.lemma.Lemma;

public interface DerivationProvider<T> {

	Lemma getDerivation(T lemma, Derivation derivation);

	boolean hasDerivation(T lemma, Derivation derivation);

	Map<Derivation, Lemma> getDerivations(T lemma);
}
