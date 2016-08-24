package com.zettelnet.german.lemma.simple.declension;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.lemma.GermanFormProvider;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;

public abstract class AbstractDeclinableGermanLemma implements DeclinableGermanLemma {

	private final String nominativeSingular;
	private final String genitiveSingular;
	private final String nominativePlural;

	private final GermanFormProvider<DeclinableGermanLemma> formProvider;
	private final GermanGenus genus;
	private PropertySet<GermanLemmaProperty> properties;

	public AbstractDeclinableGermanLemma(final String nominativeSingular, final String genitiveSingular, final String nominativePlural, final GermanFormProvider<DeclinableGermanLemma> formProvider, final GermanGenus genus, final GermanLemmaProperty... properties) {
		this.nominativeSingular = nominativeSingular;
		this.genitiveSingular = genitiveSingular;
		this.nominativePlural = nominativePlural;
		this.formProvider = formProvider;
		this.genus = genus;
		this.properties = MapPropertySet.withValues(properties);
	}

	@Override
	public String getNominalForm() {
		return nominativeSingular;
	}

	@Override
	public String getNominativeSingular() {
		return nominativeSingular;
	}

	@Override
	public String getGenitiveSingular() {
		return genitiveSingular;
	}

	@Override
	public String getNominativePlural() {
		return nominativePlural;
	}

	@Override
	public GermanGenus getGenus() {
		return genus;
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

	// @Override
	// public Collection<GermanLemma> getDerivation(GermanDerivation derivation)
	// {
	// return Collections.emptyList();
	// }
	//
	// @Override
	// public boolean hasDerivation(GermanDerivation derivation) {
	// return false;
	// }
	//
	// @Override
	// public Map<GermanDerivation, Collection<GermanLemma>> getDerivations() {
	// return Collections.emptyMap();
	// }

	@Override
	public PropertySet<GermanLemmaProperty> getProperties() {
		return properties;
	}

	protected void setProperties(PropertySet<GermanLemmaProperty> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return getNominalForm();
	}
}
