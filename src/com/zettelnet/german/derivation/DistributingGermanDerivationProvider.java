package com.zettelnet.german.derivation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.german.lemma.GermanLemma;

public class DistributingGermanDerivationProvider<T> implements GermanDerivationProvider<T> {

	private final Map<GermanDerivationType, GermanDerivationProvider<T>> providers;

	public DistributingGermanDerivationProvider() {
		this.providers = new HashMap<>();
	}

	public DistributingGermanDerivationProvider<T> addProvider(final GermanDerivationType type, final GermanDerivationProvider<T> provider) {
		providers.put(type, provider);
		return this;
	}

	public DistributingGermanDerivationProvider<T> addProvider(final GermanDerivationType[] types, final GermanDerivationProvider<T> provider) {
		for (GermanDerivationType type : types) {
			addProvider(type, provider);
		}
		return this;
	}

	@Override
	public Collection<GermanLemma> getDerivation(T lemma, GermanDerivation derivation) {
		GermanDerivationType type = derivation.getType();
		if (!providers.containsKey(type)) {
			return Collections.emptyList();
		} else {
			return providers.get(type).getDerivation(lemma, derivation);
		}
	}

	@Override
	public boolean hasDerivation(T lemma, GermanDerivation derivation) {
		GermanDerivationType type = derivation.getType();
		if (!providers.containsKey(type)) {
			return false;
		} else {
			return providers.get(type).hasDerivation(lemma, derivation);
		}
	}

	@Override
	public Map<GermanDerivation, Collection<GermanLemma>> getDerivations(T lemma) {
		Map<GermanDerivation, Collection<GermanLemma>> lemmas = new HashMap<>();
		for (GermanDerivationProvider<T> provider : providers.values()) {
			lemmas.putAll(provider.getDerivations(lemma));
		}
		return lemmas;
	}

}
