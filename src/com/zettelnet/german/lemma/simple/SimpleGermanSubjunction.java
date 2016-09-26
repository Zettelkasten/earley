package com.zettelnet.german.lemma.simple;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.german.derivation.GermanDerivation;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.GermanLemmaType;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;

public class SimpleGermanSubjunction implements GermanLemma {

	private final String firstForm;
	private final PropertySet<GermanLemmaProperty> properties;

	public SimpleGermanSubjunction(final String firstForm, final GermanLemmaProperty... properties) {
		this.firstForm = firstForm;
		this.properties = MapPropertySet.withValues(properties);
	}

	@Override
	public String getNominalForm() {
		return firstForm;
	}

	@Override
	public Collection<String> getForm(GermanForm form) {
		return Arrays.asList(firstForm);
	}

	@Override
	public boolean hasForm(GermanForm form) {
		return true;
	}

	@Override
	public Map<GermanForm, Collection<String>> getForms() {
		Map<GermanForm, Collection<String>> map = new HashMap<>(1);
		map.put(GermanForm.withValues(), Arrays.asList(firstForm));
		return map;
	}

	@Override
	public Collection<GermanLemma> getDerivation(GermanDerivation derivation) {
		return Collections.emptyList();
	}

	@Override
	public boolean hasDerivation(GermanDerivation derivation) {
		return false;
	}

	@Override
	public Map<GermanDerivation, Collection<GermanLemma>> getDerivations() {
		return Collections.emptyMap();
	}

	@Override
	public PropertySet<GermanLemmaProperty> getProperties() {
		return properties;
	}

	@Override
	public GermanLemmaType getType() {
		return GermanLemmaType.Subjunction;
	}

	@Override
	public boolean isDerivation() {
		return false;
	}

	@Override
	public GermanLemma getDerivedFrom() {
		return null;
	}

	@Override
	public GermanDerivation getDerivationKind() {
		return null;
	}

	@Override
	public String toString() {
		return getNominalForm();
	}
}
