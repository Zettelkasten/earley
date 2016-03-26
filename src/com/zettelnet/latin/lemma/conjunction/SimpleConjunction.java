package com.zettelnet.latin.lemma.conjunction;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public class SimpleConjunction implements Conjunction {

	private final String firstForm;
	private final PropertySet<LemmaProperty> properties;

	public SimpleConjunction(final String firstForm, final LemmaProperty... properties) {
		this.firstForm = firstForm;
		this.properties = MapPropertySet.withValues(properties);
	}

	@Override
	public String getNominalForm() {
		return firstForm;
	}

	@Override
	public Collection<String> getForm(Form form) {
		return Arrays.asList(firstForm);
	}

	@Override
	public boolean hasForm(Form form) {
		return true;
	}

	@Override
	public Map<Form, Collection<String>> getForms() {
		Map<Form, Collection<String>> map = new HashMap<>(1);
		map.put(Form.withValues(), Arrays.asList(firstForm));
		return map;
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
		return LemmaType.Conjunction;
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
