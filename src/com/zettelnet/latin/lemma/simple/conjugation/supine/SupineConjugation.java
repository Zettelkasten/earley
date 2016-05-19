package com.zettelnet.latin.lemma.simple.conjugation.supine;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.derivation.DerivationProvider;
import com.zettelnet.latin.derivation.DerivationType;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.simple.SimpleSupine;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugableLemma;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugationStem;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class SupineConjugation implements DerivationProvider<ConjugableLemma> {

	private static final FormProvider<DeclinableLemma> formProvider = new SupineDeclension();

	public SupineConjugation() {
	}

	@Override
	public Collection<Lemma> getDerivation(ConjugableLemma lemma, Derivation derivation) {
		if (derivation.getType() != DerivationType.Supine) {
			return Collections.emptyList();
		} else {
			String stem = lemma.getStem(ConjugationStem.Supine);
			Lemma supine = new SimpleSupine(stem, formProvider, Genus.Neuter, lemma, derivation);
			return Arrays.asList(supine);
		}
	}

	@Override
	public boolean hasDerivation(ConjugableLemma lemma, Derivation derivation) {
		return !getDerivation(lemma, derivation).isEmpty();
	}

	@Override
	public Map<Derivation, Collection<Lemma>> getDerivations(ConjugableLemma lemma) {
		Map<Derivation, Collection<Lemma>> derivations = new HashMap<>();

		Derivation derivation = Derivation.withValues(DerivationType.Supine);
		Collection<Lemma> values = getDerivation(lemma, derivation);
		derivations.put(derivation, values);

		return derivations;
	}
}
