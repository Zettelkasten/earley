package com.zettelnet.earley.test.latin;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;

public final class FormParameter implements Parameter {

	public static Set<FormProperty> deriveProperties(Set<FormProperty> parentProperties, Set<FormProperty> childProperties) {
		Set<FormProperty> set = new HashSet<>(parentProperties);
		set.retainAll(childProperties);
		return set;
	}

	public static boolean isCompatable(Set<FormProperty> parentProperties, Set<FormProperty> childProperties) {
		return !deriveProperties(parentProperties, childProperties).isEmpty();
	}

	private static final Map<Class<? extends FormProperty>, Set<FormProperty>> DEFAULT_DATA = new HashMap<>();

	private static <T> Set<T> singleSet(T value) {
		Set<T> set = new HashSet<>(1);
		set.add(value);
		return set;
	}

	private static Map<Class<? extends FormProperty>, Set<FormProperty>> makeDataMap(Map<Class<? extends FormProperty>, Set<FormProperty>> sourceData, Form form) {
		final Map<Class<? extends FormProperty>, Set<FormProperty>> data = new HashMap<>(sourceData);
		for (FormProperty property : form.values()) {
			Class<? extends FormProperty> propertyType = property.getClass();
			if (!data.containsKey(propertyType)) {
				data.put(propertyType, singleSet(property));
			} else {
				data.get(propertyType).retainAll(Arrays.asList(property));
			}
		}
		return data;
	}

	private static Map<Class<? extends FormProperty>, Set<FormProperty>> makeDataMap(Map<Class<? extends FormProperty>, Set<FormProperty>> sourceData, Map<Class<? extends FormProperty>, Set<FormProperty>> newData) {
		final Map<Class<? extends FormProperty>, Set<FormProperty>> data = new HashMap<>(sourceData);
		for (Map.Entry<Class<? extends FormProperty>, Set<FormProperty>> entry : newData.entrySet()) {
			Class<? extends FormProperty> propertyType = entry.getKey();
			Set<FormProperty> property = entry.getValue();
			if (!data.containsKey(propertyType)) {
				data.put(propertyType, property);
			} else {
				data.get(propertyType).retainAll(property);
			}
		}
		return data;
	}

	private static Map<Class<? extends FormProperty>, Set<FormProperty>> makeDataMap(FormProperty... formProperties) {
		final Map<Class<? extends FormProperty>, Set<FormProperty>> data = new HashMap<>(formProperties.length);

		for (FormProperty property : formProperties) {
			Class<? extends FormProperty> propertyType = property.getClass();
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
	private final Map<Class<? extends FormProperty>, Set<FormProperty>> data;

	private final Determination cause;

	public FormParameter() {
		this(DEFAULT_DATA);
	}

	public FormParameter(final FormProperty... formProperties) {
		this(makeDataMap(DEFAULT_DATA, makeDataMap(formProperties)));
	}

	public FormParameter(final Form form) {
		this(makeDataMap(DEFAULT_DATA, form));
	}

	public FormParameter(final Determination cause) {
		this(makeDataMap(DEFAULT_DATA, cause.getForm()), cause);
	}

	public FormParameter(final Map<Class<? extends FormProperty>, Set<FormProperty>> data) {
		this(data, null);
	}

	public FormParameter(final Map<Class<? extends FormProperty>, Set<FormProperty>> data, final Determination cause) {
		this.data = data;
		this.cause = cause;
	}

	public boolean isCompatibleWith(FormParameter other) {
		for (Map.Entry<Class<? extends FormProperty>, Set<FormProperty>> entry : data.entrySet()) {
			Class<? extends FormProperty> propertyType = entry.getKey();
			Set<FormProperty> parentValue = entry.getValue();

			if (other.data.containsKey(propertyType)) {
				Set<FormProperty> childValue = other.data.get(propertyType);

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

	// warning: ignores duplicate parameters
	public Form toForm() {
		Collection<FormProperty> properties = new HashSet<>();
		for (Set<FormProperty> propertySet : data.values()) {
			properties.add(propertySet.iterator().next());
		}
		return Form.withValues(properties);
	}

	public Determination getCause() {
		return cause;
	}

	public Set<FormProperty> getProperty(Class<? extends FormProperty> propertyType) {
		return data.get(propertyType);
	}

	public Map<Class<? extends FormProperty>, Set<FormProperty>> getProperties() {
		return data;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Set<FormProperty> propertySet : data.values()) {
			str.append(' ');
			for (Iterator<FormProperty> i = propertySet.iterator(); i.hasNext();) {
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
