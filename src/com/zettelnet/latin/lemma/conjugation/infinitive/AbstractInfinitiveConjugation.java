package com.zettelnet.latin.lemma.conjugation.infinitive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.infinitive.SimpleGerund;
import com.zettelnet.latin.lemma.infinitive.SimpleInfinitive;
import com.zettelnet.latin.lemma.verb.Verb;
import com.zettelnet.latin.lemma.verb.VerbStem;

public abstract class AbstractInfinitiveConjugation implements DerivationProvider<Verb> {

	private final FormValueProvider<String> firstFormEndings;
	private final FormValueProvider<String> stemEndings;

	private final FormValueProvider<VerbStem> stemTypes;
	private static final FormProvider<DeclinableLemma> formProvider = new InfinitiveDeclension();

	public AbstractInfinitiveConjugation(final FormValueProvider<String> firstFormEndings, final FormValueProvider<String> stemEndings, final FormValueProvider<VerbStem> stemTypes) {
		this.firstFormEndings = firstFormEndings;
		this.stemEndings = stemEndings;
		this.stemTypes = stemTypes;
	}

	private static <T> T first(Collection<T> collection) {
		return collection.iterator().next();
	}

	@Override
	public Collection<Lemma> getDerivation(Verb lemma, Derivation derivation) {
		if (derivation.getType() != DerivationType.Infinitive) {
			return Collections.emptyList();
		} else {
			Collection<Lemma> lemmas = new ArrayList<>();

			Form form = derivation.getForm();

			Collection<String> firstFormEndings = this.firstFormEndings.getValue(form);
			if (!firstFormEndings.isEmpty()) {
				String verbStem = lemma.getStem(first(stemTypes.getValue(form)));
				String firstForm = verbStem + first(firstFormEndings);

				Collection<String> stemEndings = this.stemEndings.getValue(form);
				if (stemEndings.isEmpty()) {
					// infinitive without inflected forms
					Lemma infinitive = new SimpleInfinitive(firstForm, Genus.Neuter, lemma, derivation);
					lemmas.add(infinitive);
				} else {
					// infinitive / gerund with inflected forms
					for (String stemEnding : stemEndings) {
						String stem = verbStem + stemEnding;

						Lemma gerund = new SimpleGerund(firstForm, stem, formProvider, Genus.Neuter, lemma, derivation);
						lemmas.add(gerund);
					}
				}
			}
			return lemmas;
		}
	}

	@Override
	public boolean hasDerivation(Verb lemma, Derivation derivation) {
		return !getDerivation(lemma, derivation).isEmpty();
	}

	@Override
	public Map<Derivation, Collection<Lemma>> getDerivations(Verb lemma) {
		Map<Derivation, Collection<Lemma>> derivations = new HashMap<>();

		for (Tense tense : getTenseSet(lemma)) {
			for (Voice voice : getVoiceSet(lemma)) {
				Derivation derivation = Derivation.withValues(DerivationType.Infinitive, tense, voice);
				Collection<Lemma> values = getDerivation(lemma, derivation);

				if (!values.isEmpty()) {
					derivations.put(derivation, values);
				}
			}
		}

		return derivations;
	}

	public Set<Tense> getTenseSet(Verb lemma) {
		return EnumSet.of(Tense.Present, Tense.Perfect);
	}

	public Set<Voice> getVoiceSet(Verb lemma) {
		return EnumSet.of(Voice.Active, Voice.Passive);
	}
}
