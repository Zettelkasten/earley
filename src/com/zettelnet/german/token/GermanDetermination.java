package com.zettelnet.german.token;

import java.util.Collection;
import java.util.HashSet;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.GermanLemmaType;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;

public class GermanDetermination {

	private final GermanLemma lemma;
	private final PropertySet<?> properties;

	public GermanDetermination(final GermanLemma lemma, final Property... formProperties) {
		this(lemma, MapPropertySet.withValues(formProperties));
	}

	public GermanDetermination(final GermanLemma lemma, final PropertySet<?> propertySet) {
		this.lemma = lemma;
		this.properties = propertySet;
	}

	public GermanLemma getLemma() {
		return lemma;
	}

	public GermanLemmaType getLemmaType() {
		return lemma.getType();
	}

	public PropertySet<?> getProperties() {
		return properties;
	}

	// warning: ignores non form properties
	public Form toForm() {
		Collection<FormProperty> formProperties = new HashSet<>();
		for (Property property : this.properties.values()) {
			if (property instanceof FormProperty) {
				formProperties.add((FormProperty) property);
			}
		}
		return Form.withValues(formProperties);
	}

	@Override
	public String toString() {
		return "Determination [" + lemma + ", " + properties + "]";
	}
}
