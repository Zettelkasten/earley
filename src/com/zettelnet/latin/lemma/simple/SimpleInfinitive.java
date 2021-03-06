package com.zettelnet.latin.lemma.simple;

import java.util.Arrays;
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
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class SimpleInfinitive implements DeclinableLemma {

	private final String firstForm;
	private final Set<Genus> genus;

	private final Lemma verb;
	private final Derivation derivation;

	private final PropertySet<LemmaProperty> properties;

	public SimpleInfinitive(final String firstForm, final Genus genus, final Lemma verb, final Derivation derivation, final LemmaProperty... properties) {
		this.firstForm = firstForm;
		this.genus = EnumSet.of(genus);
		this.verb = verb;
		this.derivation = derivation;
		this.properties = MapPropertySet.withValues(properties).derive(verb.getProperties().values()).derive(Finiteness.Infinitive);
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
		return null;
	}

	@Override
	public Collection<String> getForm(Form form) {
		if (hasForm(form)) {
			return Arrays.asList(firstForm);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public boolean hasForm(Form form) {
		return form.hasProperties(Casus.Nominative) || form.hasProperties(Casus.Accusative);
	}

	@Override
	public Map<Form, Collection<String>> getForms() {
		Map<Form, Collection<String>> map = new HashMap<>(1);
		map.put(Form.withValues(Casus.Nominative), Arrays.asList(firstForm));
		map.put(Form.withValues(Casus.Accusative), Arrays.asList(firstForm));
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
	public Set<Genus> getGenus() {
		return genus;
	}

	@Override
	public PropertySet<LemmaProperty> getProperties() {
		return properties;
	}

	@Override
	public LemmaType getType() {
		return LemmaType.Infinitive;
	}

	@Override
	public boolean isDerivation() {
		return true;
	}

	@Override
	public Lemma getDerivedFrom() {
		return verb;
	}

	@Override
	public Derivation getDerivationKind() {
		return derivation;
	}

	@Override
	public String toString() {
		return getNominalForm();
	}
}
