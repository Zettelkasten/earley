package com.zettelnet.earley.test.latin;

import java.util.Collection;
import java.util.HashSet;

import com.zettelnet.earley.param.property.MapPropertySet;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;

public class Determination {

	private final Lemma lemma;
	private final PropertySet<?> properties;

	public Determination(final Lemma lemma, final Property... formProperties) {
		this(lemma, MapPropertySet.withValues(formProperties));
	}

	public Determination(final Lemma lemma, final PropertySet<?> propertySet) {
		this.lemma = lemma;
		this.properties = propertySet;
	}

	public Lemma getLemma() {
		return lemma;
	}

	public LemmaType getLemmaType() {
		return lemma.getType();
	}

	public PropertySet<?> getProperties() {
		return properties;
	}

	// warning: ignores non form properties
	public Form toForm() {
		Collection<FormProperty> formProperties = new HashSet<>();
		for (Property property : formProperties) {
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
