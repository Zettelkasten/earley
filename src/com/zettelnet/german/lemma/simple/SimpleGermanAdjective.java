package com.zettelnet.german.lemma.simple;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.german.derivation.GermanDerivation;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.lemma.GermanFormProvider;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.GermanLemmaType;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;
import com.zettelnet.german.lemma.simple.declension.GermanAdjective;

public class SimpleGermanAdjective implements GermanAdjective {

	private final String stem;
	private final GermanFormProvider<GermanAdjective> formProvider;
	private final PropertySet<GermanLemmaProperty> properties;

	public SimpleGermanAdjective(final String stem, final GermanFormProvider<GermanAdjective> formProvider, final GermanLemmaProperty... properties) {
		this.stem = stem;
		this.formProvider = formProvider;
		this.properties = MapPropertySet.withValues(properties);
	}

	@Override
	public String getNominalForm() {
		return stem;
	}

	@Override
	public String getStem() {
		return stem;
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
		return GermanLemmaType.Adjective;
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
