package com.zettelnet.latin.derivation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.latin.lemma.Lemma;

public class DistributingDerivationProvider<T> implements DerivationProvider<T> {

	private final Map<DerivationType, DerivationProvider<T>> providers;
	
	public DistributingDerivationProvider() {
		this.providers = new HashMap<>();
	}
	
	public DistributingDerivationProvider<T> addProvider(final DerivationType type, final DerivationProvider<T> provider) {
		providers.put(type, provider);
		return this;
	}
	
	@Override
	public Collection<Lemma> getDerivation(T lemma, Derivation derivation) {
		DerivationType type = derivation.getType();
		if (!providers.containsKey(type)) {
			return Collections.emptyList();
		} else {
			return providers.get(type).getDerivation(lemma, derivation);
		}
	}

	@Override
	public boolean hasDerivation(T lemma, Derivation derivation) {
		DerivationType type = derivation.getType();
		if (!providers.containsKey(type)) {
			return false;
		} else {
			return providers.get(type).hasDerivation(lemma, derivation);
		}
	}

	@Override
	public Map<Derivation, Collection<Lemma>> getDerivations(T lemma) {
		Map<Derivation, Collection<Lemma>> lemmas = new HashMap<>();
		for (DerivationProvider<T> provider : providers.values()) {
			lemmas.putAll(provider.getDerivations(lemma));
		}
		return lemmas;
	}

}
