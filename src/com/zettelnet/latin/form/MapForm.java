package com.zettelnet.latin.form;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class MapForm implements Form {

	public static Form valueOf(FormProperty... properties) {
		return new MapForm(Arrays.asList(properties));
	}

	public static Form valueOf(Collection<FormProperty> properties) {
		return new MapForm(properties);
	}

	// may not be modified
	private final Map<Class<? extends FormProperty>, FormProperty> data;

	private MapForm(final Collection<FormProperty> properties) {
		this.data = new HashMap<>(properties.size());
		for (FormProperty property : properties) {
			if (property != null) {
				data.put(property.getClass(), property);
			}
		}
	}

	private MapForm(final Map<Class<? extends FormProperty>, FormProperty> data) {
		this.data = data;
	}

	@Override
	public <T extends FormProperty> boolean hasProperty(Class<T> property) {
		return data.containsKey(property);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends FormProperty> T getProperty(Class<T> property) {
		return (T) data.get(property);
	}

	@Override
	public Collection<FormProperty> values() {
		return data.values();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (FormProperty property : data.values()) {
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
	public Form retainAll(Collection<Class<? extends FormProperty>> properties) {
		Map<Class<? extends FormProperty>, FormProperty> newData = new HashMap<>(this.data);
		if (newData.keySet().retainAll(properties)) {
			return new MapForm(newData);
		} else {
			return this;
		}
	}

	@Override
	public boolean hasProperties(FormProperty... properties) {
		for (FormProperty property : properties) {
			if (getProperty(property.getClass()) != property) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Form derive(Collection<? extends FormProperty> properties) {
		Map<Class<? extends FormProperty>, FormProperty> newData = new HashMap<>(this.data);
		for (FormProperty property : properties) {
			newData.put(property.getClass(), property);
		}
		return new MapForm(newData);
	}
}
