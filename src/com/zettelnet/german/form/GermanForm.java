package com.zettelnet.german.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.param.property.PropertySet;

public interface GermanForm extends PropertySet<GermanFormProperty>, Comparable<GermanForm> {

	public static final Set<Object> ALL_PROPERTIES = Collections.unmodifiableSet(new HashSet<>(getAllProperties()));
	public static final List<Object> ALL_PROPERTIES_SORTED = Collections.unmodifiableList(getAllProperties());

	static List<Object> getAllProperties() {
		List<Object> properties = new ArrayList<>();
		properties.add(GermanCasus.TYPE);
		properties.add(GermanPerson.TYPE);
		properties.add(GermanNumerus.TYPE);
		properties.add(GermanGenus.TYPE);
		properties.add(GermanMood.TYPE);
		properties.add(GermanTense.TYPE);
		properties.add(GermanVoice.TYPE);
		properties.add(GermanComparison.TYPE);

		return properties;
	}

	public static GermanForm withValues(GermanFormProperty... properties) {
		return MapGermanForm.valueOf(properties);
	}

	public static GermanForm withValues(Collection<GermanFormProperty> properties) {
		return MapGermanForm.valueOf(properties);
	}

	public static GermanForm nounForm(GermanCasus casus, GermanNumerus numerus, GermanGenus genus) {
		return withValues(casus, numerus, genus);
	}

	public static GermanForm nounForm(GermanCasus casus, GermanNumerus numerus, GermanGenus genus, GermanPerson person) {
		return withValues(casus, numerus, genus, person);
	}

	public static GermanForm verbForm(GermanPerson person, GermanNumerus numerus, GermanTense tense, GermanMood mood, GermanVoice voice) {
		return withValues(person, numerus, tense, mood, voice);
	}

	@Override
	boolean hasProperty(Object propertyType);

	@Override
	<U extends GermanFormProperty> U getProperty(Object propertyType);

	String toStringShort();

	public default GermanCasus getCasus() {
		return getProperty(GermanCasus.TYPE);
	}

	public default GermanNumerus getNumerus() {
		return getProperty(GermanNumerus.TYPE);
	}

	public default GermanGenus getGenus() {
		return getProperty(GermanGenus.TYPE);
	}

	public default GermanPerson getPerson() {
		return getProperty(GermanPerson.TYPE);
	}

	public default GermanMood getMood() {
		return getProperty(GermanMood.TYPE);
	}

	public default GermanTense getTense() {
		return getProperty(GermanTense.TYPE);
	}

	public default GermanVoice getVoice() {
		return getProperty(GermanVoice.TYPE);
	}

	public default GermanComparison getComparison() {
		return getProperty(GermanComparison.TYPE);
	}

	@Override
	GermanForm retainAll(Collection<?> propertyTypes);

	default GermanForm retainAll(Object... propertyTypes) {
		return retainAll(Arrays.asList(propertyTypes));
	}

	default GermanForm derive(GermanForm withForm) {
		return derive(withForm.values());
	}

	@Override
	default GermanForm derive(GermanFormProperty... properties) {
		return derive(Arrays.asList(properties));
	}

	@Override
	GermanForm derive(Collection<? extends GermanFormProperty> properties);
}
