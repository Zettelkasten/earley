package com.zettelnet.latin.lemma.simple.conjugation.participle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.derivation.DerivationProvider;
import com.zettelnet.latin.derivation.DerivationType;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.simple.SimpleParticiple;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugableLemma;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugationStem;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public abstract class AbstractParticipleConjugation implements DerivationProvider<ConjugableLemma> {

	private final FormValueProvider<String> firstFormEndings;
	private final FormValueProvider<String> stemEndings;

	private final FormValueProvider<ConjugationStem> stemTypes;
	private final FormValueProvider<Map<Genus, FormProvider<DeclinableLemma>>> formProviders;

	public AbstractParticipleConjugation(final FormValueProvider<String> firstFormEndings, final FormValueProvider<String> stemEndings, final FormValueProvider<ConjugationStem> stemTypes, final FormValueProvider<Map<Genus, FormProvider<DeclinableLemma>>> formProviders) {
		this.firstFormEndings = firstFormEndings;
		this.stemEndings = stemEndings;
		this.stemTypes = stemTypes;
		this.formProviders = formProviders;
	}

	private static <T> T first(Collection<T> collection) {
		return collection.iterator().next();
	}

	@Override
	public Collection<Lemma> getDerivation(ConjugableLemma lemma, Derivation derivation) {
		if (derivation.getType() != DerivationType.Participle) {
			return Collections.emptyList();
		} else {
			Collection<Lemma> lemmas = new ArrayList<>();

			Form form = derivation.getForm();

			for (String stemEnding : stemEndings.getValue(form)) {
				String VerbStem = lemma.getStem(first(stemTypes.getValue(form)));

				Map<Genus, String> firstForms = new EnumMap<>(Genus.class);
				for (Genus genus : Genus.values()) {
					firstForms.put(genus, VerbStem + first(firstFormEndings.getValue(form.derive(genus))));
				}
				String stem = VerbStem + stemEnding;
				Map<Genus, FormProvider<DeclinableLemma>> formProviders = first(this.formProviders.getValue(form));

				Lemma participle = new SimpleParticiple(firstForms, stem, formProviders, lemma, derivation);
				lemmas.add(participle);
			}
			return lemmas;
		}
	}

	@Override
	public boolean hasDerivation(ConjugableLemma lemma, Derivation derivation) {
		return !getDerivation(lemma, derivation).isEmpty();
	}

	@Override
	public Map<Derivation, Collection<Lemma>> getDerivations(ConjugableLemma lemma) {
		Map<Derivation, Collection<Lemma>> derivations = new HashMap<>();

		for (Tense tense : getTenseSet(lemma)) {
			for (Voice voice : getVoiceSet(lemma)) {
				Derivation derivation = Derivation.withValues(DerivationType.Participle, tense, voice);
				Collection<Lemma> values = getDerivation(lemma, derivation);

				if (!values.isEmpty()) {
					derivations.put(derivation, values);
				}
			}
		}

		return derivations;
	}

	public Set<Tense> getTenseSet(ConjugableLemma lemma) {
		return EnumSet.of(Tense.Present, Tense.Perfect, Tense.Future);
	}

	public Set<Voice> getVoiceSet(ConjugableLemma lemma) {
		return EnumSet.of(Voice.Active, Voice.Passive);
	}
}
