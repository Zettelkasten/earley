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

	public static <T extends Property> Set<T> deriveProperties(Set<T> parentProperties, Set<T> childProperties) {
		if (parentProperties == null) {
			return childProperties;
		} else if (childProperties == null) {
			return parentProperties;
		} else {
			Set<T> set = new HashSet<>(parentProperties);
			set.retainAll(childProperties);
			return set;
		}
	}

	public static <T extends Property> boolean isCompatable(Set<T> parentProperties, Set<T> childProperties) {
		return !deriveProperties(parentProperties, childProperties).isEmpty();
	}

	private static final Map<Object, Set<? extends Property>> DEFAULT_DATA = new HashMap<>();

	static <T> Set<T> singleSet(T value) {
		Set<T> set = new HashSet<>(1);
		set.add(value);
		return set;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Casts a <code>Set&lt;? extends Property&gt;</code> into a
	 * <code>Set&lt;Property&gt;</code> so that values can be added to the set.
	 * <p>
	 * <strong>It is the caller's job to ensure that the entries inserted into
	 * the returned Set are actually of the corresponding map type.
	 * 
	 * @param set
	 *            a set with wildcard parameter
	 * @return a casted set
	 */
	static <T extends Property> Set<T> unsafeCast(Set<? extends Property> set) {
		return (Set<T>) set;
	}

	private static Map<Object, Set<? extends Property>> makeDataMap(Map<Object, Set<? extends Property>> sourceData, PropertySet<?> form) {
		final Map<Object, Set<? extends Property>> data = new HashMap<>(sourceData);
		for (Property property : form.values()) {
			Object propertyType = property.getType();
			if (!data.containsKey(propertyType)) {
				data.put(propertyType, singleSet(property));
			} else {
				data.get(propertyType).retainAll(Arrays.asList(property));
			}
		}
		return data;
	}

	private static Map<Object, Set<? extends Property>> makeDataMap(Map<Object, Set<? extends Property>> sourceData, Map<Object, Set<? extends Property>> newData) {
		final Map<Object, Set<? extends Property>> data = new HashMap<>(sourceData);
		for (Map.Entry<Object, Set<? extends Property>> entry : newData.entrySet()) {
			Object propertyType = entry.getKey();
			Set<? extends Property> property = entry.getValue();
			if (!data.containsKey(propertyType)) {
				data.put(propertyType, property);
			} else {
				data.get(propertyType).retainAll(property);
			}
		}
		return data;
	}

	private static Map<Object, Set<? extends Property>> makeDataMap(Property... formProperties) {
		final Map<Object, Set<? extends Property>> data = new HashMap<>(formProperties.length);

		for (Property property : formProperties) {
			Object propertyType = property.getType();
			if (!data.containsKey(propertyType)) {
				data.put(propertyType, new HashSet<>());
			}
			unsafeCast(data.get(propertyType)).add(property);
		}

		return data;
	}

	// neither the map itself nor it value sets may be modified;
	// represents all properties by key
	// property keys not contained in this map allow ANY value; empty value sets
	// are not allowed
	/**
	 * This map represents the actual data of this property set. Every property
	 * type ( <code>Object</code>) is mapped to a set of properties (
	 * <code>Set&lt;? extends
	 * Property&gt;</code>). If a property type is not represented in this map,
	 * <i>any</i> value of that type is contained in this FormParameter. Empty
	 * value sets are not allowed, i.e. there must at least by one property per
	 * type in any FormParameter.
	 * <p>
	 * <strong>Neither the map itself nor its value sets may be modified, as
	 * FormParameters are immutable.</strong>
	 */
	private final Map<Object, Set<? extends Property>> data;

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

	public FormParameter(final Map<Object, Set<? extends Property>> data) {
		this(data, null);
	}

	public FormParameter(final Map<Object, Set<? extends Property>> data, final Determination cause) {
		this.data = data;
		this.cause = cause;
	}

	public boolean isCompatibleWith(FormParameter other) {
		for (Map.Entry<Object, Set<? extends Property>> entry : data.entrySet()) {
			Object propertyType = entry.getKey();
			Set<Property> parentValue = unsafeCast(entry.getValue());

			if (other.data.containsKey(propertyType)) {
				Set<Property> childValue = unsafeCast(other.data.get(propertyType));

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
		for (Set<? extends Property> propertySet : data.values()) {
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

	public <T extends Property> Set<T> getProperty(Object propertyType) {
		return unsafeCast(data.get(propertyType));
	}

	public Map<Object, Set<? extends Property>> getProperties() {
		return data;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Set<? extends Property> propertySet : data.values()) {
			str.append(' ');
			for (Iterator<? extends Property> i = propertySet.iterator(); i.hasNext();) {
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
