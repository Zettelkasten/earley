package com.zettelnet.german.lemma.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.german.derivation.GermanDerivation;
import com.zettelnet.german.derivation.GermanDerivationProvider;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanMood;
import com.zettelnet.german.form.GermanNumerus;
import com.zettelnet.german.form.GermanPerson;
import com.zettelnet.german.form.GermanTense;
import com.zettelnet.german.form.GermanVoice;
import com.zettelnet.german.lemma.CombinedGermanProvider;
import com.zettelnet.german.lemma.GermanFormProvider;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.GermanLemmaType;
import com.zettelnet.german.lemma.property.GermanFiniteness;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;
import com.zettelnet.german.lemma.simple.conjugation.ConjugableGermanLemma;
import com.zettelnet.german.lemma.simple.conjugation.GermanConjugationStem;

public class SimpleGermanVerb implements ConjugableGermanLemma {

	private final Collection<String> infinitive;
	private final Map<GermanConjugationStem, Collection<String>> stems;

	private final GermanFormProvider<ConjugableGermanLemma> formProvider;
	private final GermanDerivationProvider<ConjugableGermanLemma> derivationProvider;

	private final PropertySet<GermanLemmaProperty> properties;

	public SimpleGermanVerb(final String infinitive, final String presentStem, final String pastStem, final String participle2Stem, final CombinedGermanProvider<ConjugableGermanLemma> provider, final GermanLemmaProperty... properties) {
		this(Arrays.asList(infinitive),
				GermanConjugationStem.makeMap(Arrays.asList(presentStem), Arrays.asList(pastStem), Arrays.asList(participle2Stem)),
				provider, provider,
				properties);
	}

	public SimpleGermanVerb(final String infinitive, final Map<GermanConjugationStem, Collection<String>> stems, final CombinedGermanProvider<ConjugableGermanLemma> provider, final GermanLemmaProperty... properties) {
		this(Arrays.asList(infinitive), stems, provider, provider, properties);
	}

	public SimpleGermanVerb(final Collection<String> infinitive, final Map<GermanConjugationStem, Collection<String>> stems, final GermanFormProvider<ConjugableGermanLemma> formProvider, final GermanDerivationProvider<ConjugableGermanLemma> derivationProvider, final GermanLemmaProperty... properties) {
		this.infinitive = infinitive;
		this.stems = stems;

		this.formProvider = formProvider;
		this.derivationProvider = derivationProvider;

		List<GermanLemmaProperty> propertyList = new ArrayList<>(Arrays.asList(properties));
		propertyList.add(GermanFiniteness.Finite);
		this.properties = MapPropertySet.withValues(propertyList);
	}

	@Override
	public String getNominalForm() {
		return getForm(GermanForm.withValues(GermanPerson.First, GermanNumerus.Singular, GermanTense.Present, GermanMood.Indicative, GermanVoice.Active)).iterator().next();
	}

	@Override
	public Collection<String> getInfinitive() {
		return infinitive;
	}

	@Override
	public Collection<String> getStem(GermanConjugationStem stem) {
		return stems.get(stem);
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
	public PropertySet<GermanLemmaProperty> getProperties() {
		return properties;
	}

	@Override
	public Collection<GermanLemma> getDerivation(GermanDerivation derivation) {
		return derivationProvider.getDerivation(this, derivation);
	}

	@Override
	public boolean hasDerivation(GermanDerivation derivation) {
		return derivationProvider.hasDerivation(this, derivation);
	}

	@Override
	public Map<GermanDerivation, Collection<GermanLemma>> getDerivations() {
		return derivationProvider.getDerivations(this);
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
	public GermanLemmaType getType() {
		return GermanLemmaType.Verb;
	}
	
	@Override
	public String toString() {
		return getNominalForm();
	}
}
