package com.zettelnet.latin.lemma.simple.conjugation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.FormProvider;

/**
 * Generates a value of a {@link Form} of a {@link ConjugableLemma}.
 * <p>
 * <code>form = stem + linking vowel (including tense sign) + ending</code>
 */
public abstract class AbstractConjugation implements FormProvider<ConjugableLemma> {

	private final FormValueProvider<String> linkings;
	private final FormValueProvider<String> endings;

	public AbstractConjugation(final FormValueProvider<String> linkings, final FormValueProvider<String> endings) {
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

	public ConjugationStem getStemType(Form form) {
		switch (form.getTense()) {
		case Present:
		case Imperfect:
		case Future:
			return ConjugationStem.Present;
		case Perfect:
		case Pluperfect:
		case FuturePerfect:
			return ConjugationStem.Perfect;
		default:
			throw new AssertionError("Unknown tense");
		}
	}

	@Override
	public Collection<String> getForm(final ConjugableLemma ConjugableLemma, final Form form) {
		// form = stem + vowel + ending
		Collection<String> variants = new ArrayList<>();

		for (String linkingMorph : linkings.getValue(form)) {
			for (String endingMorph : endings.getValue(form)) {
				StringBuilder str = new StringBuilder();
				str.append(ConjugableLemma.getStem(getStemType(form)));
				str.append(linkingMorph);
				str.append(endingMorph);
				variants.add(str.toString());
			}
		}
		return variants;
	}

	public Set<Person> getPersonSet(final ConjugableLemma ConjugableLemma) {
		return EnumSet.allOf(Person.class);
	}

	public Set<Numerus> getNumerusSet(final ConjugableLemma ConjugableLemma) {
		return EnumSet.allOf(Numerus.class);
	}

	public Set<Tense> getTenseSet(final ConjugableLemma ConjugableLemma) {
		return EnumSet.allOf(Tense.class);
	}

	public Set<Mood> getMoodSet(final ConjugableLemma ConjugableLemma) {
		return EnumSet.allOf(Mood.class);
	}

	public Set<Voice> getVoiceSet(final ConjugableLemma ConjugableLemma) {
		return EnumSet.allOf(Voice.class);
	}

	@Override
	public boolean hasForm(final ConjugableLemma ConjugableLemma, final Form form) {
		return !getForm(ConjugableLemma, form).isEmpty();
	}

	@Override
	public Map<Form, Collection<String>> getForms(ConjugableLemma ConjugableLemma) {
		Map<Form, Collection<String>> forms = new HashMap<>();

		for (Person person : getPersonSet(ConjugableLemma)) {
			for (Numerus numerus : getNumerusSet(ConjugableLemma)) {
				for (Tense tense : getTenseSet(ConjugableLemma)) {
					for (Mood mood : getMoodSet(ConjugableLemma)) {
						for (Voice voice : getVoiceSet(ConjugableLemma)) {
							Form form = Form.withValues(person, numerus, tense, mood, voice);
							Collection<String> variants = getForm(ConjugableLemma, form);
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
}
