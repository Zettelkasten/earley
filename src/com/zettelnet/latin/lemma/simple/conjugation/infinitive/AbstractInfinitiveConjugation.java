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
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.simple.SimpleGerund;
import com.zettelnet.latin.lemma.simple.SimpleInfinitive;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugableLemma;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugationStem;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public abstract class AbstractInfinitiveConjugation implements DerivationProvider<ConjugableLemma> {

	public static final Set<DerivationType> TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(DerivationType.Infinitive, DerivationType.Gerund)));

	private final FormValueProvider<String> firstFormEndings;
	private final FormValueProvider<String> stemEndings;

	private final FormValueProvider<ConjugationStem> stemTypes;
	private static final FormProvider<DeclinableLemma> formProvider = new GerundDeclension();

	public AbstractInfinitiveConjugation(final FormValueProvider<String> firstFormEndings, final FormValueProvider<String> stemEndings, final FormValueProvider<ConjugationStem> stemTypes) {
		this.firstFormEndings = firstFormEndings;
		this.stemEndings = stemEndings;
		this.stemTypes = stemTypes;
	}

	private static <T> T first(Collection<T> collection) {
		return collection.iterator().next();
	}

	@Override
	public Collection<Lemma> getDerivation(ConjugableLemma lemma, Derivation derivation) {
		if (derivation.getType() != DerivationType.Infinitive && derivation.getType() != DerivationType.Gerund) {
			return Collections.emptyList();
		} else {
			Collection<Lemma> lemmas = new ArrayList<>();

			Form form = derivation.getForm();

			Collection<String> firstFormEndings = this.firstFormEndings.getValue(form);
			if (!firstFormEndings.isEmpty()) {
				String VerbStem = lemma.getStem(first(stemTypes.getValue(form)));
				String firstForm = VerbStem + first(firstFormEndings);

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
						String stem = VerbStem + stemEnding;

						if (derivation.getType() == DerivationType.Infinitive) {
							Lemma infinitive = new SimpleInfinitive(firstForm, Genus.Neuter, lemma, derivation);
							lemmas.add(infinitive);
						} else if (derivation.getType() == DerivationType.Gerund) {
							Lemma gerund = new SimpleGerund(VerbStem + stemEnding + "um", stem, formProvider, Genus.Neuter, lemma, derivation);
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
	public boolean hasDerivation(ConjugableLemma lemma, Derivation derivation) {
		return !getDerivation(lemma, derivation).isEmpty();
	}

	@Override
	public Map<Derivation, Collection<Lemma>> getDerivations(ConjugableLemma lemma) {
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

	public Set<Tense> getTenseSet(ConjugableLemma lemma) {
		return EnumSet.of(Tense.Present, Tense.Perfect);
	}

	public Set<Voice> getVoiceSet(ConjugableLemma lemma) {
		return EnumSet.of(Voice.Active, Voice.Passive);
	}
}
