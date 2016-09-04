package com.zettelnet.german.lemma.simple.conjugation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanFormValueProvider;
import com.zettelnet.german.form.GermanMood;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.GermanPerson;
import com.zettelnet.german.form.GermanTense;
import com.zettelnet.german.form.GermanVoice;
import com.zettelnet.german.lemma.GermanFormProvider;

public abstract class AbstractGermanConjugation implements GermanFormProvider<ConjugableGermanLemma> {

	private final GermanFormValueProvider<String> endings;

	public AbstractGermanConjugation(final GermanFormValueProvider<String> endings) {
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

	public GermanConjugationStem getStemType(GermanForm form) {
		switch (form.getMood()) {
		case Indicative:
			switch (form.getTense()) {
			case Present:
				if (form.hasProperties(GermanNumerus.Singular) && (form.hasProperties(GermanPerson.Second) || form.hasProperties(GermanPerson.Third))) {
					return GermanConjugationStem.SecondPresent;
				} else {
					return GermanConjugationStem.Present;
				}
			case Past:
				return GermanConjugationStem.Past;
			default:
				throw new AssertionError("Unhandled tense for mood " + form.getMood());
			}
		case Subjunctive1:
			switch (form.getTense()) {
			case Present:
				return GermanConjugationStem.Present;
			default:
				throw new AssertionError("Unhandled tense for mood " + form.getMood());
			}
		case Subjunctive2:
			switch (form.getTense()) {
			case Past:
				return GermanConjugationStem.Subjunctive2;
			default:
				throw new AssertionError("Unhandled tense for mood " + form.getMood());
			}
		default:
			throw new AssertionError("Unhandled mood");
		}
	}

	@Override
	public Collection<String> getForm(final ConjugableGermanLemma conjugableLemma, final GermanForm form) {
		// form = stem + ending
		Collection<String> variants = new ArrayList<>();

		GermanConjugationStem stemType = getStemType(form);

		for (String stem : conjugableLemma.getStem(stemType)) {
			for (String endingMorph : endings.getValue(form)) {
				StringBuilder str = new StringBuilder();
				str.append(stem);
				str.append(endingMorph);
				variants.add(str.toString());
			}
		}
		return variants;
	}

	@Override
	public boolean hasForm(final ConjugableGermanLemma conjugableLemma, final GermanForm form) {
		return !getForm(conjugableLemma, form).isEmpty();
	}

	@Override
	public Map<GermanForm, Collection<String>> getForms(ConjugableGermanLemma lemma) {
		Map<GermanForm, Collection<String>> forms = new HashMap<>();

		addForms(lemma, forms, GermanTense.Present, GermanMood.Indicative, GermanVoice.Active);
		addForms(lemma, forms, GermanTense.Past, GermanMood.Indicative, GermanVoice.Active);
		addForms(lemma, forms, GermanTense.Present, GermanMood.Subjunctive1, GermanVoice.Active);
		addForms(lemma, forms, GermanTense.Past, GermanMood.Subjunctive2, GermanVoice.Active);

		return forms;
	}

	private void addForms(final ConjugableGermanLemma lemma, final Map<GermanForm, Collection<String>> forms, final GermanTense tense, final GermanMood mood, final GermanVoice voice) {
		for (GermanPerson person : getPersonSet(lemma)) {
			for (GermanNumerus numerus : getNumerusSet(lemma)) {
				GermanForm form = GermanForm.withValues(person, numerus, tense, mood, voice);
				Collection<String> variants = getForm(lemma, form);
				if (!variants.isEmpty()) {
					forms.put(form, variants);
				}
			}
		}
	}

	public Set<GermanPerson> getPersonSet(ConjugableGermanLemma lemma) {
		return EnumSet.allOf(GermanPerson.class);
	}

	public Set<GermanNumerus> getNumerusSet(ConjugableGermanLemma lemma) {
		return EnumSet.allOf(GermanNumerus.class);
	}
}
