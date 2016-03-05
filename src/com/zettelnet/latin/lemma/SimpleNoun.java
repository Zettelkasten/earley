package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;

public class SimpleNoun implements Noun {

	private final String firstForm;
	private final String stem;
	private final FormProvider<Noun> formProvider;
	private final Genus genus;

	public SimpleNoun(final String firstForm, final String stem, final FormProvider<Noun> formProvider, final Genus genus) {
		this.firstForm = firstForm;
		this.stem = stem;
		this.formProvider = formProvider;
		this.genus = genus;
	}

	@Override
	public String getFirstForm() {
		return firstForm;
	}

	@Override
	public String getStem() {
		return stem;
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
		return LemmaType.Noun;
	}

	@Override
	public Genus getGenus() {
		return genus;
	}
}
