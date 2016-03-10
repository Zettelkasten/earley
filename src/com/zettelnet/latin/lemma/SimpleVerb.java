package com.zettelnet.latin.lemma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.derivation.DerivationProvider;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.property.MapLemmaPropertySet;

public class SimpleVerb implements Verb {

	private final Map<Tense, String> stems;

	private final FormProvider<Verb> formProvider;
	private final DerivationProvider<Verb> derivationProvider;

	private final PropertySet<LemmaProperty> properties;

	public SimpleVerb(final String presentStem, final String perfectStem, final String supineStem, final CombinedProvider<Verb> provider, final LemmaProperty... properties) {
		this(presentStem, perfectStem, supineStem, provider, provider, properties);
	}

	public SimpleVerb(final String presentStem, final String perfectStem, final String supineStem, final FormProvider<Verb> formProvider, final DerivationProvider<Verb> derivationProvider, final LemmaProperty... properties) {
		this.stems = new EnumMap<>(Tense.class);
		this.stems.put(Tense.Present, presentStem);
		this.stems.put(Tense.Perfect, perfectStem);

		this.formProvider = formProvider;
		this.derivationProvider = derivationProvider;

		List<LemmaProperty> propertyList = new ArrayList<>(Arrays.asList(properties));
		propertyList.add(Finiteness.Finite);
		this.properties = MapLemmaPropertySet.valueOf(propertyList);
	}

	@Override
	public String getNominalForm() {
		return getForm(Form.withValues(Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active)).iterator().next();
	}

	@Override
	public String getStem(Tense tense) {
		return stems.get(tense);
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
		return derivationProvider.getDerivation(this, derivation);
	}

	@Override
	public boolean hasDerivation(Derivation derivation) {
		return derivationProvider.hasDerivation(this, derivation);
	}

	@Override
	public Map<Derivation, Collection<Lemma>> getDerivations() {
		return derivationProvider.getDerivations(this);
	}

	@Override
	public PropertySet<LemmaProperty> getProperties() {
		return properties;
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Verb;
	}

	@Override
	public String toString() {
		return getNominalForm();
	}
}
