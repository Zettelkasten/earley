package com.zettelnet.latin.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;

public interface Form extends PropertySet<FormProperty>, Comparable<Form> {

	public static final Set<Class<? extends FormProperty>> ALL_PROPERTIES = Collections.unmodifiableSet(new HashSet<>(getAllProperties()));
	public static final List<Class<? extends FormProperty>> ALL_PROPERTIES_SORTED = Collections.unmodifiableList(getAllProperties());

	static List<Class<? extends FormProperty>> getAllProperties() {
		List<Class<? extends FormProperty>> properties = new ArrayList<>();
		properties.add(Casus.class);
		properties.add(Person.class);
		properties.add(Numerus.class);
		properties.add(Genus.class);
		properties.add(Mood.class);
		properties.add(Tense.class);
		properties.add(Voice.class);
		properties.add(Comparison.class);

		// these values have to be here so Form#compareTo(..) and
		// Form#toString(..) work until LemmaProperties are no longer
		// FormProperties
		properties.add(Finiteness.class);
		properties.add(Valency.class);

		return properties;
	}

	public static Form withValues(FormProperty... properties) {
		return MapForm.valueOf(properties);
	}

	public static Form withValues(Collection<FormProperty> properties) {
		return MapForm.valueOf(properties);
	}

	public static Form nounForm(Casus casus, Numerus numerus, Genus genus) {
		return nounForm(casus, numerus, genus, null);
	}

	public static Form nounForm(Casus casus, Numerus numerus, Genus genus, Person person) {
		return withValues(casus, numerus, genus, person, null, null, null, null, null);
	}

	public static Form verbForm(Person person, Numerus numerus, Tense tense, Mood mood, Voice voice) {
		return verbForm(person, numerus, tense, mood, voice, null);
	}

	public static Form verbForm(Person person, Numerus numerus, Tense tense, Mood mood, Voice voice, Finiteness verbType) {
		return withValues(null, numerus, null, person, mood, tense, voice, null, verbType);
	}

	@Override
	<T extends FormProperty> boolean hasProperty(Class<T> property);

	@Override
	<T extends FormProperty> T getProperty(Class<T> property);

	String toStringShort();

	public default Casus getCasus() {
		return getProperty(Casus.class);
	}

	public default Numerus getNumerus() {
		return getProperty(Numerus.class);
	}

	public default Genus getGenus() {
		return getProperty(Genus.class);
	}

	public default Person getPerson() {
		return getProperty(Person.class);
	}

	public default Mood getMood() {
		return getProperty(Mood.class);
	}

	public default Tense getTense() {
		return getProperty(Tense.class);
	}

	public default Voice getVoice() {
		return getProperty(Voice.class);
	}

	public default Comparison getComparison() {
		return getProperty(Comparison.class);
	}

	public default Finiteness getVerbType() {
		return getProperty(Finiteness.class);
	}

	public default Valency getValency() {
		return getProperty(Valency.class);
	}

	Form retainAll(Collection<Class<? extends FormProperty>> properties);

	boolean hasProperties(FormProperty... properties);

	default Form derive(FormProperty... properties) {
		return derive(Arrays.asList(properties));
	}

	Form derive(Collection<? extends FormProperty> properties);
}
