package com.zettelnet.latin.lemma;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public abstract class AbstractDeclinableLemma implements DeclinableLemma {

	private final String firstForm;
	private final String stem;
	private final FormProvider<DeclinableLemma> formProvider;
	private final Set<Genus> genus;
	private final PropertySet<LemmaProperty> properties;

	public AbstractDeclinableLemma(final String firstForm, final String stem, final FormProvider<DeclinableLemma> formProvider, final Genus genus, final LemmaProperty... properties) {
		this(firstForm, stem, formProvider, EnumSet.of(genus), properties);
	}

	public AbstractDeclinableLemma(final String firstForm, final String stem, final FormProvider<DeclinableLemma> formProvider, final Set<Genus> genus, final LemmaProperty... properties) {
		this.firstForm = firstForm;
		this.stem = stem;
		this.formProvider = formProvider;
		this.genus = genus;
		this.properties = MapPropertySet.withValues(properties);
	}

	@Override
	public String getNominalForm() {
		return firstForm;
	}
	
	@Override
	public String getFirstForm(Genus genus) {
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
	public Set<Genus> getGenus() {
		return genus;
	}

	@Override
	public PropertySet<LemmaProperty> getProperties() {
		return properties;
	}
	
	@Override
	public String toString() {
		return getNominalForm();
	}
}
