package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public class DummyLemma implements Lemma {

	private final String firstForm;
	private final PropertySet<LemmaProperty> properties;
	private final LemmaType type;

	public DummyLemma(final LemmaType type, final LemmaProperty... properties) {
		this(UUID.randomUUID().toString(), type, properties);
	}

	public DummyLemma(final String firstForm, final LemmaType type, final LemmaProperty... properties) {
		this.firstForm = firstForm;
		this.type = type;
		this.properties = MapPropertySet.withValues(properties);
	}

	@Override
	public String getNominalForm() {
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
	public Collection<Lemma> getDerivation(Derivation derivation) {
		return Collections.emptyList();
	}

	@Override
	public boolean hasDerivation(Derivation derivation) {
		return false;
	}

	@Override
	public Map<Derivation, Collection<Lemma>> getDerivations() {
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
	public boolean isDerivation() {
		return false;
	}

	@Override
	public Lemma getDerivedFrom() {
		return null;
	}

	@Override
	public Derivation getDerivationKind() {
		return null;
	}

	@Override
	public String toString() {
		return getNominalForm();
	}
}
