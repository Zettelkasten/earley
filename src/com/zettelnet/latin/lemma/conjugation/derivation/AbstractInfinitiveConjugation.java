package com.zettelnet.latin.lemma.conjugation.derivation;

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
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.SimpleNoun;
import com.zettelnet.latin.lemma.Verb;
import com.zettelnet.latin.lemma.VerbStem;
import com.zettelnet.latin.lemma.declension.Declension;

public abstract class AbstractInfinitiveConjugation implements DerivationProvider<Verb> {

	private final FormValueProvider<String> firstFormEndings;
	private final FormValueProvider<String> stemEndings;

	public AbstractInfinitiveConjugation(final FormValueProvider<String> firstFormEndings, final FormValueProvider<String> stemEndings) {
		this.firstFormEndings = firstFormEndings;
		this.stemEndings = stemEndings;
	}

	@Override
	public Collection<Lemma> getDerivation(Verb lemma, Derivation derivation) {
		if (derivation.getType() != DerivationType.Infinitive) {
			return Collections.emptyList();
		} else {
			Collection<Lemma> lemmas = new ArrayList<>();

			Form form = derivation.getForm();
			String verbStem = lemma.getStem(VerbStem.Present);

			for (String stemEnding : stemEndings.getMorph(form)) {
				String firstForm = verbStem + firstFormEndings.getMorph(form).iterator().next();
				String stem = verbStem + stemEnding;
				
				Lemma infinitive = new SimpleNoun(firstForm, stem, Declension.Second, Genus.Neuter);
				lemmas.add(infinitive);
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
