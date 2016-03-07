package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;

public class SimpleVerb implements Verb {

	private final Map<Tense, String> stems;
	private final FormProvider<Verb> formProvider;

	public SimpleVerb(final String presentStem, final String perfectStem, final String supineStem, final FormProvider<Verb> formProvider) {
		this.stems = new EnumMap<>(Tense.class);
		this.stems.put(Tense.Present, presentStem);
		this.stems.put(Tense.Perfect, perfectStem);
		this.formProvider = formProvider;
	}

	@Override
	public String getFirstForm() {
		return getForm(Form.withValues(Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active)).iterator().next();
	}

	@Override
	public String getStem(Tense tense) {
		return stems.get(tense);
	}

	@Override
	public Collection<String> getForm(Form form) {
		return formProvider.getForm(this, form);
	}

	@Override
	public boolean hasForm(Form form) {
		return formProvider.hasForm(this, form);
	}

	@Override
	public Map<Form, Collection<String>> getForms() {
		return formProvider.getForms(this);
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Verb;
	}

	@Override
	public String toString() {
		return getFirstForm();
	}
}
