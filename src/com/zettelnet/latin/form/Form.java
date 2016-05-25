package com.zettelnet.latin.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.param.property.PropertySet;

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

		return properties;
	}

	public static Form withValues(FormProperty... properties) {
		return MapForm.valueOf(properties);
	}

	public static Form withValues(Collection<FormProperty> properties) {
		return MapForm.valueOf(properties);
	}

	public static Form nounForm(Casus casus, Numerus numerus, Genus genus) {
		return withValues(casus, numerus, genus);
	}

	public static Form nounForm(Casus casus, Numerus numerus, Genus genus, Person person) {
		return withValues(casus, numerus, genus, person);
	}

	public static Form verbForm(Person person, Numerus numerus, Tense tense, Mood mood, Voice voice) {
		return withValues(person, numerus, tense, mood, voice);
	}

	@Override
	boolean hasProperty(Object propertyType);

	@Override
	<U extends FormProperty> U getProperty(Object propertyType);

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

	@Override
	Form retainAll(Collection<?> properties);

	default Form retainAll(Object... properties) {
		return retainAll(Arrays.asList(properties));
	}

	default Form derive(Form withForm) {
		return derive(withForm.values());
	}

	@Override
	default Form derive(FormProperty... properties) {
		return derive(Arrays.asList(properties));
	}

	@Override
	Form derive(Collection<? extends FormProperty> properties);
}
