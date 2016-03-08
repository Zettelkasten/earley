package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.property.MapLemmaPropertySet;

public abstract class AbstractDeclinableLemma implements DeclinableLemma {

	private final String firstForm;
	private final String stem;
	private final FormProvider<DeclinableLemma> formProvider;
	private final Genus genus;
	private final PropertySet<LemmaProperty> properties;

	public AbstractDeclinableLemma(final String firstForm, final String stem, final FormProvider<DeclinableLemma> formProvider, final Genus genus, final LemmaProperty... properties) {
		this.firstForm = firstForm;
		this.stem = stem;
		this.formProvider = formProvider;
		this.genus = genus;
		this.properties = MapLemmaPropertySet.valueOf(properties);
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

	@Override
	public PropertySet<LemmaProperty> getProperties() {
		return properties;
	}
}
