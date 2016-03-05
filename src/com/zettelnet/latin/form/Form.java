package com.zettelnet.latin.form;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.zettelnet.earley.param.property.PropertySet;

public interface Form extends PropertySet<FormProperty> {

	@Deprecated
	public static final Set<Class<? extends FormProperty>> ALL_PROPERTIES = getAllProperties();

	static Set<Class<? extends FormProperty>> getAllProperties() {
		Set<Class<? extends FormProperty>> properties = new HashSet<>();
		properties.add(Casus.class);
		properties.add(Numerus.class);
		properties.add(Genus.class);
		properties.add(Person.class);
		properties.add(Mood.class);
		properties.add(Tense.class);
		properties.add(Voice.class);
		properties.add(Comparison.class);
		properties.add(Finiteness.class);
		properties.add(Valency.class);

		return Collections.unmodifiableSet(properties);
	}

	public static Form withValues(FormProperty... properties) {
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

	Form retainAll(@SuppressWarnings("unchecked") Class<? extends FormProperty>... properties);
}
