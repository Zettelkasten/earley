package com.zettelnet.latin.lemma.adjective;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Comparison;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.LemmaProperty;

public class SimpleAdjective implements Adjective {

	private final Map<Genus, String> firstForm;
	private final String stem;
	private final Map<Genus, FormProvider<DeclinableLemma>> formProviders;
	private final PropertySet<LemmaProperty> properties;

	public SimpleAdjective(final String masculineForm, final String feminineForm, final String neuterForm, final String stem, final Map<Genus, FormProvider<DeclinableLemma>> formProvider, LemmaProperty... lemmaProperties) {
		this(Genus.makeMap(masculineForm, feminineForm, neuterForm), stem, formProvider);
	}

	public SimpleAdjective(final String masculineForm, final String feminineForm, final String neuterForm, final String stem, final FormProvider<DeclinableLemma> formProvider, LemmaProperty... lemmaProperties) {
		this(Genus.makeMap(masculineForm, feminineForm, neuterForm), stem, Genus.makeMap(formProvider, formProvider, formProvider));
	}

	public SimpleAdjective(final Map<Genus, String> firstForm, final String stem, final Map<Genus, FormProvider<DeclinableLemma>> formProvider, LemmaProperty... lemmaProperties) {
		this.firstForm = firstForm;
		this.stem = stem;
		this.formProviders = formProvider;
		this.properties = MapPropertySet.withValues(lemmaProperties);
	}

	@Override
	public String getNominalForm() {
		return getForm(Form.withValues(Casus.Nominative, Numerus.Singular, Genus.Masculine, Comparison.Positive)).iterator().next();
	}

	@Override
	public String getFirstForm(Genus genus) {
		return firstForm.get(genus);
	}

	@Override
	public Collection<String> getForm(Form form) {
		return formProviders.get(form.getGenus()).getForm(this, form);
	}

	@Override
	public boolean hasForm(Form form) {
		return formProviders.get(form.getGenus()).hasForm(this, form);
	}

	@Override
	public Map<Form, Collection<String>> getForms() {
		Map<Form, Collection<String>> forms = new HashMap<>();
		for (Map.Entry<Genus, FormProvider<DeclinableLemma>> entry : formProviders.entrySet()) {
			forms.putAll(entry.getValue().getForms(this));
		}
		return forms;
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
	public String getStem() {
		return stem;
	}

	@Override
	public Set<Genus> getGenus() {
		return EnumSet.allOf(Genus.class);
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Adjective;
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
