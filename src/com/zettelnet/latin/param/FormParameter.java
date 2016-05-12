package com.zettelnet.latin.param;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.param.property.PropertySet;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;
import com.zettelnet.latin.token.Determination;

public final class FormParameter implements Parameter {

	public static Set<Property> deriveProperties(Set<Property> parentProperties, Set<Property> childProperties) {
		if (parentProperties == null) {
			return childProperties;
		} else if (childProperties == null) {
			return parentProperties;
		} else {
			Set<Property> set = new HashSet<>(parentProperties);
			set.retainAll(childProperties);
			return set;
		}
	}

	public static boolean isCompatable(Set<Property> parentProperties, Set<Property> childProperties) {
		return !deriveProperties(parentProperties, childProperties).isEmpty();
	}

	private static final Map<Class<? extends Property>, Set<Property>> DEFAULT_DATA = new HashMap<>();

	private static <T> Set<T> singleSet(T value) {
		Set<T> set = new HashSet<>(1);
		set.add(value);
		return set;
	}

	private static Map<Class<? extends Property>, Set<Property>> makeDataMap(Map<Class<? extends Property>, Set<Property>> sourceData, PropertySet<?> form) {
		final Map<Class<? extends Property>, Set<Property>> data = new HashMap<>(sourceData);
		for (Property property : form.values()) {
			Class<? extends Property> propertyType = property.getType();
			if (!data.containsKey(propertyType)) {
				data.put(propertyType, singleSet(property));
			} else {
				data.get(propertyType).retainAll(Arrays.asList(property));
			}
		}
		return data;
	}

	private static Map<Class<? extends Property>, Set<Property>> makeDataMap(Map<Class<? extends Property>, Set<Property>> sourceData, Map<Class<? extends Property>, Set<Property>> newData) {
		final Map<Class<? extends Property>, Set<Property>> data = new HashMap<>(sourceData);
		for (Map.Entry<Class<? extends Property>, Set<Property>> entry : newData.entrySet()) {
			Class<? extends Property> propertyType = entry.getKey();
			Set<Property> property = entry.getValue();
			if (!data.containsKey(propertyType)) {
				data.put(propertyType, property);
			} else {
				data.get(propertyType).retainAll(property);
			}
		}
		return data;
	}

	private static Map<Class<? extends Property>, Set<Property>> makeDataMap(Property... formProperties) {
		final Map<Class<? extends Property>, Set<Property>> data = new HashMap<>(formProperties.length);

		for (Property property : formProperties) {
			Class<? extends Property> propertyType = property.getType();
			if (!data.containsKey(propertyType)) {
				data.put(propertyType, new HashSet<>());
			}
			data.get(propertyType).add(property);
		}

		return data;
	}

	// neither the map itself nor it value sets may be modified;
	// represents all properties by key
	// property keys not contained in this map allow ANY value; empty value sets
	// are not allowed
	private final Map<Class<? extends Property>, Set<Property>> data;

	private final Determination cause;

	public FormParameter() {
		this(DEFAULT_DATA);
	}

	public FormParameter(final Property... formProperties) {
		this(makeDataMap(DEFAULT_DATA, makeDataMap(formProperties)));
	}

	public FormParameter(final PropertySet<?> properties) {
		this(makeDataMap(DEFAULT_DATA, properties));
	}

	public FormParameter(final Determination cause) {
		this(makeDataMap(DEFAULT_DATA, cause.getProperties()), cause);
	}

	public FormParameter(final Map<Class<? extends Property>, Set<Property>> data) {
		this(data, null);
	}

	public FormParameter(final Map<Class<? extends Property>, Set<Property>> data, final Determination cause) {
		this.data = data;
		this.cause = cause;
	}

	public boolean isCompatibleWith(FormParameter other) {
		for (Map.Entry<Class<? extends Property>, Set<Property>> entry : data.entrySet()) {
			Class<? extends Property> propertyType = entry.getKey();
			Set<Property> parentValue = entry.getValue();

			if (other.data.containsKey(propertyType)) {
				Set<Property> childValue = other.data.get(propertyType);

				if (!isCompatable(parentValue, childValue)) {
					return false;
				}
			}
		}

		return true;
	}

	public FormParameter deriveWith(FormParameter with) {
		return new FormParameter(makeDataMap(this.data, with.data), with.cause);
	}

	public FormParameter scanWith(FormParameter tokenParameter) {
		return new FormParameter(this.data, tokenParameter.getCause());
	}

	// warning: ignores duplicate parameters & non form properties
	public Form toForm() {
		Collection<FormProperty> properties = new HashSet<>();
		for (Set<Property> propertySet : data.values()) {
			Property first = propertySet.iterator().next();
			if (first instanceof FormProperty) {
				properties.add((FormProperty) first);
			}
		}
		return Form.withValues(properties);
	}

	public Determination getCause() {
		return cause;
	}

	public Set<Property> getProperty(Class<? extends Property> propertyType) {
		return data.get(propertyType);
	}

	public Map<Class<? extends Property>, Set<Property>> getProperties() {
		return data;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Set<Property> propertySet : data.values()) {
			str.append(' ');
			for (Iterator<Property> i = propertySet.iterator(); i.hasNext();) {
				str.append(i.next().shortName());
				if (i.hasNext()) {
					str.append('/');
				}
			}
		}

		if (str.length() == 0) {
			return "?";
		} else {
			return str.substring(1);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FormParameter other = (FormParameter) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

}
