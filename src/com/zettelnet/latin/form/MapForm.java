package com.zettelnet.latin.form;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class MapForm implements Form {

	public static Form valueOf(FormProperty... properties) {
		return new MapForm(properties);
	}

	// may not be modified
	private final Map<Class<? extends FormProperty>, FormProperty> data;

	public MapForm(final FormProperty... properties) {
		this.data = new HashMap<>(properties.length);
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
		newData.keySet().retainAll(properties);
		return new MapForm(newData);
	}
}
