package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.property.MapLemmaPropertySet;

public class SimpleLemma implements Lemma {

	private final String firstForm;
	private final PropertySet<LemmaProperty> properties;
	private final LemmaType type;

	public SimpleLemma(final LemmaType type, final LemmaProperty... properties) {
		this(UUID.randomUUID().toString(), type, properties);
	}

	public SimpleLemma(final String firstForm, final LemmaType type, final LemmaProperty... properties) {
		this.firstForm = firstForm;
		this.type = type;
		this.properties = MapLemmaPropertySet.valueOf(properties);
	}

	@Override
	public String getFirstForm() {
		return firstForm;
	}

	@Override
	public Collection<String> getForm(Form form) {
		return Collections.emptyList();
	}

	@Override
	public boolean hasForm(Form form) {
		return false;
	}

	@Override
	public Map<Form, Collection<String>> getForms() {
		return Collections.emptyMap();
	}

	@Override
	public PropertySet<LemmaProperty> getProperties() {
		return properties;
	}

	@Override
	public LemmaType getType() {
		return type;
	}

	@Override
	public String toString() {
		return getFirstForm();
	}
}
