package com.zettelnet.german.derivation;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.german.lemma.GermanLemma;

public interface GermanDerivationProvider<T> {

	Collection<GermanLemma> getDerivation(T lemma, GermanDerivation derivation);

	boolean hasDerivation(T lemma, GermanDerivation derivation);

	Map<GermanDerivation, Collection<GermanLemma>> getDerivations(T lemma);
}
