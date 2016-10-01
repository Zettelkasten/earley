package com.zettelnet.german.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanFormValueProvider;

public class ConcreteGermanFormProvider implements GermanFormProvider<GermanLemma> {

	private final GermanFormValueProvider<String> values;

	public ConcreteGermanFormProvider(final GermanFormValueProvider<String> values) {
		this.values = values;
	}

	@Override
	public Collection<String> getForm(GermanLemma lemma, GermanForm form) {
		return values.getValue(form);
	}

	@Override
	public boolean hasForm(GermanLemma lemma, GermanForm form) {
		return !values.hasValue(form);
	}

	@Override
	public Map<GermanForm, Collection<String>> getForms(GermanLemma lemma) {
		return values.getValues();
	}
}
