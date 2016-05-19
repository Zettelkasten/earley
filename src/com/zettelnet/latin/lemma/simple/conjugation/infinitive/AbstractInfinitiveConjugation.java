package com.zettelnet.latin.lemma.simple.conjugation.infinitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
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
import com.zettelnet.latin.lemma.simple.SimpleGerund;
import com.zettelnet.latin.lemma.simple.SimpleInfinitive;
import com.zettelnet.latin.lemma.simple.Verb;
import com.zettelnet.latin.lemma.simple.VerbStem;

public abstract class AbstractInfinitiveConjugation implements DerivationProvider<Verb> {

	public static final Set<DerivationType> TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(DerivationType.Infinitive, DerivationType.Gerund)));

	private final FormValueProvider<String> firstFormEndings;
	private final FormValueProvider<String> stemEndings;

	private final FormValueProvider<VerbStem> stemTypes;
	private static final FormProvider<DeclinableLemma> formProvider = new GerundDeclension();

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
		if (derivation.getType() != DerivationType.Infinitive && derivation.getType() != DerivationType.Gerund) {
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
					// infinitive
					if (derivation.getType() != DerivationType.Infinitive) {
						return Collections.emptyList();
					} else {
						Lemma infinitive = new SimpleInfinitive(firstForm, Genus.Neuter, lemma, derivation);
						lemmas.add(infinitive);
					}
				} else {
					// has inflected forms -> infinitive AND gerund
					for (String stemEnding : stemEndings) {
						String stem = verbStem + stemEnding;

						if (derivation.getType() == DerivationType.Infinitive) {
							Lemma infinitive = new SimpleInfinitive(firstForm, Genus.Neuter, lemma, derivation);
							lemmas.add(infinitive);
						} else if (derivation.getType() == DerivationType.Gerund) {
							Lemma gerund = new SimpleGerund(verbStem + stemEnding + "um", stem, formProvider, Genus.Neuter, lemma, derivation);
							lemmas.add(gerund);
						} else {
							throw new AssertionError();
						}
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
				for (DerivationType type : TYPES) {
					Derivation derivation = Derivation.withValues(type, tense, voice);
					Collection<Lemma> values = getDerivation(lemma, derivation);

					if (!values.isEmpty()) {
						derivations.put(derivation, values);
					}
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
