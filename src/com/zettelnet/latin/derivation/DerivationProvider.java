package com.zettelnet.latin.derivation;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.latin.lemma.Lemma;

public interface DerivationProvider<T> {

	Collection<Lemma> getDerivation(T lemma, Derivation derivation);

	boolean hasDerivation(T lemma, Derivation derivation);

	Map<Derivation, Collection<Lemma>> getDerivations(T lemma);
}
