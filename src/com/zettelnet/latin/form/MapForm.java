package com.zettelnet.latin.form;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public final class MapForm implements Form {

	public static Form valueOf(FormProperty... properties) {
		return new MapForm(Arrays.asList(properties));
	}

	public static Form valueOf(Collection<FormProperty> properties) {
		return new MapForm(properties);
	}

	// may not be modified
	private final Map<Object, FormProperty> data;

	private MapForm(final Collection<FormProperty> properties) {
		this.data = new HashMap<>(properties.size());
		for (FormProperty property : properties) {
			if (property != null) {
				data.put(property.getType(), property);
			}
		}
	}

	private MapForm(final Map<Object, FormProperty> data) {
		this.data = data;
	}

	@Override
	public boolean hasProperty(Object propertyType) {
		return data.containsKey(propertyType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U extends FormProperty> U getProperty(Object propertyType) {
		return (U) data.get(propertyType);
	}

	@Override
	public Collection<FormProperty> values() {
		return data.values();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Object propertyType : Form.ALL_PROPERTIES_SORTED) {
			FormProperty property = getProperty(propertyType);
			if (property != null) {
				str.append(' ');
				str.append(property.shortName());
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
		MapForm other = (MapForm) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

	@Override
	public String toStringShort() {
		return toString();
	}

	@Override
	public Form retainAll(Collection<?> properties) {
		Map<Object, FormProperty> newData = new HashMap<>(this.data);
		if (newData.keySet().retainAll(properties)) {
			return new MapForm(newData);
		} else {
			return this;
		}
	}

	@Override
	public boolean hasProperties(FormProperty... properties) {
		for (FormProperty property : properties) {
			if (getProperty(property.getType()) != property) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Form derive(Collection<? extends FormProperty> properties) {
		Map<Object, FormProperty> newData = new HashMap<>(this.data);
		for (FormProperty property : properties) {
			newData.put(property.getType(), property);
		}
		return new MapForm(newData);
	}

	@Override
	public int compareTo(Form other) {
		if (this.equals(other)) {
			return 0;
		} else {
			ListIterator<?> i = Form.ALL_PROPERTIES_SORTED.listIterator(Form.ALL_PROPERTIES_SORTED.size());
			while (i.hasPrevious()) {
				Object propertyType = i.previous();
				FormProperty thisProperty = this.getProperty(propertyType);
				FormProperty otherProperty = other.getProperty(propertyType);

				if (thisProperty != null && otherProperty != null && thisProperty.ordinal() != otherProperty.ordinal()) {
					return thisProperty.ordinal() - otherProperty.ordinal();
				}
			}
			throw new AssertionError("Form properties technically equal but form is not equal");
		}
	}
}
