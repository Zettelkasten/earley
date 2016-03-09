package com.zettelnet.latin.lemma.conjugation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.derivation.DerivationProvider;
import com.zettelnet.latin.derivation.DerivationType;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.Verb;
import com.zettelnet.latin.morph.MorphProvider;

/**
 * Generates a value of a {@link Form} of a {@link Verb}.
 * <p>
 * <code>form = stem + linking vowel (including tense sign) + ending</code>
 * 
 * @param noun
 * @param form
 * @return
 */
public abstract class AbstractConjugation implements FormProvider<Verb>, DerivationProvider<Verb> {

	private final MorphProvider<Form> linkings;
	private final MorphProvider<Form> endings;

	public AbstractConjugation(final MorphProvider<Form> linkings, final MorphProvider<Form> endings) {
		this.linkings = linkings;
		this.endings = endings;
	}

	protected static Collection<String> concat(final String stem, Collection<String> endings) {
		Collection<String> variants = new ArrayList<>(endings.size());
		for (String ending : endings) {
			StringBuilder str = new StringBuilder();
			str.append(stem);
			if (ending != null) {
				str.append(ending);
			}
			variants.add(str.toString());
		}
		return variants;
	}

	public Tense getStemTense(Form form) {
		switch (form.getTense()) {
		case Present:
		case Imperfect:
		case Future:
			return Tense.Present;
		case Perfect:
		case Pluperfect:
		case FuturePerfect:
			return Tense.Perfect;
		default:
			throw new AssertionError("Unknown tense");
		}
	}

	@Override
	public Collection<String> getForm(final Verb verb, final Form form) {
		// form = stem + vowel + ending
		Collection<String> variants = new ArrayList<>();

		for (String linkingMorph : linkings.getMorph(form)) {
			for (String endingMorph : endings.getMorph(form)) {
				StringBuilder str = new StringBuilder();
				str.append(verb.getStem(getStemTense(form)));
				str.append(linkingMorph);
				str.append(endingMorph);
				variants.add(str.toString());
			}
		}
		return variants;
	}

	public Set<Person> getPersonSet(final Verb verb) {
		return new HashSet<>(Arrays.asList(Person.values()));
	}

	public Set<Numerus> getNumerusSet(final Verb verb) {
		return new HashSet<>(Arrays.asList(Numerus.values()));
	}

	public Set<Tense> getTenseSet(final Verb verb) {
		return new HashSet<>(Arrays.asList(Tense.values()));
	}

	public Set<Mood> getMoodSet(final Verb verb) {
		return new HashSet<>(Arrays.asList(Mood.values()));
	}

	public Set<Voice> getVoiceSet(final Verb verb) {
		return new HashSet<>(Arrays.asList(Voice.values()));
	}

	@Override
	public boolean hasForm(final Verb verb, final Form form) {
		return !getForm(verb, form).isEmpty();
	}

	@Override
	public Map<Form, Collection<String>> getForms(Verb verb) {
		Map<Form, Collection<String>> forms = new HashMap<>();

		for (Person person : getPersonSet(verb)) {
			for (Numerus numerus : getNumerusSet(verb)) {
				for (Tense tense : getTenseSet(verb)) {
					for (Mood mood : getMoodSet(verb)) {
						for (Voice voice : getVoiceSet(verb)) {
							Form form = Form.withValues(person, numerus, tense, mood, voice);
							Collection<String> variants = getForm(verb, form);
							if (!variants.isEmpty()) {
								forms.put(form, variants);
							}
						}
					}
				}
			}
		}

		return forms;
	}

	// derivations

	@Override
	public Lemma getDerivation(Verb verb, Derivation derivation) {
		DerivationType type = derivation.getType();
		Form form = derivation.getForm();
		
		// TODO
		return null;
	}

	@Override
	public boolean hasDerivation(Verb verb, Derivation derivation) {
		return getDerivation(verb, derivation) != null;
	}

	@Override
	public Map<Derivation, Lemma> getDerivations(Verb verb) {
		Map<Derivation, Lemma> derivations = new HashMap<>();

		for (Tense tense : getTenseSet(verb)) {
			for (Voice voice : getVoiceSet(verb)) {
				for (Genus genus : Genus.values()) {
					Derivation derivation = Derivation.withValues(DerivationType.Infinitive, tense, voice, genus);
					Lemma lemma = getDerivation(verb, derivation);
					if (lemma != null) {
						derivations.put(derivation, lemma);
					}
				}
			}
		}

		return derivations;
	}
}
