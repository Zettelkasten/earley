package com.zettelnet.latin.lemma.infinitive;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.lemma.property.LemmaProperty;
import com.zettelnet.latin.lemma.property.MapLemmaPropertySet;
import com.zettelnet.latin.lemma.verb.Verb;

public class SimpleInfinitive implements Infinitive {

	private final String firstForm;
	private final Set<Genus> genus;

	private final Verb verb;
	private final PropertySet<LemmaProperty> properties;

	public SimpleInfinitive(final String firstForm, final Genus genus, final Verb verb, final LemmaProperty... properties) {
		this.firstForm = firstForm;
		this.genus = EnumSet.of(genus);
		this.verb = verb;
		this.properties = MapLemmaPropertySet.valueOf(properties);
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
		return Arrays.asList(firstForm);
	}

	@Override
	public boolean hasForm(Form form) {
		return form.hasProperties(Casus.Nominative);
	}

	@Override
	public Map<Form, Collection<String>> getForms() {
		Map<Form, Collection<String>> map = new HashMap<>(1);
		map.put(Form.withValues(Casus.Nominative), Arrays.asList(firstForm));
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
	public Verb getDerivedFrom() {
		return verb;
	}

	@Override
	public String toString() {
		return getNominalForm();
	}
}
