package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;

public abstract class AbstractDeclinableLemma implements DeclinableLemma {

	private final String firstForm;
	private final String stem;
	private final FormProvider<DeclinableLemma> formProvider;
	private final Genus genus;

	public AbstractDeclinableLemma(final String firstForm, final String stem, final FormProvider<DeclinableLemma> formProvider, final Genus genus) {
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
	public Genus getGenus() {
		return genus;
	}
}
