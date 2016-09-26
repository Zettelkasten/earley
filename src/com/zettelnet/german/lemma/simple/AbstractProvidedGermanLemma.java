package com.zettelnet.german.lemma.simple;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.lemma.GermanFormProvider;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;

public abstract class AbstractProvidedGermanLemma implements GermanLemma {

	private final GermanFormProvider<GermanLemma> formProvider;
	private final PropertySet<GermanLemmaProperty> properties;

	public AbstractProvidedGermanLemma(final GermanFormProvider<GermanLemma> formProvider, final GermanLemmaProperty... properties) {
		this.formProvider = formProvider;
		this.properties = MapPropertySet.withValues(properties);
	}

	@Override
	public Collection<String> getForm(GermanForm form) {
		return formProvider.getForm(this, form);
	}

	@Override
	public boolean hasForm(GermanForm form) {
		return formProvider.hasForm(this, form);
	}

	@Override
	public Map<GermanForm, Collection<String>> getForms() {
		return formProvider.getForms(this);
	}

	@Override
	public PropertySet<GermanLemmaProperty> getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return getNominalForm();
	}
}
