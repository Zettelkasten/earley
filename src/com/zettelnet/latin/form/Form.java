package com.zettelnet.latin.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.earley.param.property.ValuesPropertyType;

public interface Form extends PropertySet<FormProperty>, Comparable<Form> {

	public static final Set<ValuesPropertyType<? extends FormProperty>> ALL_PROPERTIES = Collections.unmodifiableSet(new HashSet<>(getAllProperties()));
	public static final List<ValuesPropertyType<? extends FormProperty>> ALL_PROPERTIES_SORTED = Collections.unmodifiableList(getAllProperties());

	static List<ValuesPropertyType<? extends FormProperty>> getAllProperties() {
		List<ValuesPropertyType<? extends FormProperty>> properties = new ArrayList<>();
		properties.add(Casus.TYPE);
		properties.add(Person.TYPE);
		properties.add(Numerus.TYPE);
		properties.add(Genus.TYPE);
		properties.add(Mood.TYPE);
		properties.add(Tense.TYPE);
		properties.add(Voice.TYPE);
		properties.add(Degree.TYPE);

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
		return getProperty(Casus.TYPE);
	}

	public default Numerus getNumerus() {
		return getProperty(Numerus.TYPE);
	}

	public default Genus getGenus() {
		return getProperty(Genus.TYPE);
	}

	public default Person getPerson() {
		return getProperty(Person.TYPE);
	}

	public default Mood getMood() {
		return getProperty(Mood.TYPE);
	}

	public default Tense getTense() {
		return getProperty(Tense.TYPE);
	}

	public default Voice getVoice() {
		return getProperty(Voice.TYPE);
	}

	public default Degree getDegree() {
		return getProperty(Degree.TYPE);
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
