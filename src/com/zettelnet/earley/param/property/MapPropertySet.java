package com.zettelnet.earley.param.property;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class MapPropertySet<T extends Property> implements PropertySet<T> {

	public static <T extends Property> PropertySet<T> withValues(@SuppressWarnings("unchecked") T... properties) {
		return new MapPropertySet<>(Arrays.asList(properties));
	}

	public static <T extends Property> PropertySet<T> withValues(Collection<? extends T> properties) {
		return new MapPropertySet<>(properties);
	}

	// may not be modified
	private final Map<Class<? extends Property>, T> data;

	private MapPropertySet(final Collection<? extends T> properties) {
		this.data = new HashMap<>(properties.size());
		for (T property : properties) {
			if (property != null) {
				data.put(property.getType(), property);
			}
		}
	}

	private MapPropertySet(final Map<Class<? extends Property>, T> data) {
		this.data = data;
	}

	@Override
	public boolean hasProperty(Class<? extends T> property) {
		return data.containsKey(property);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U extends T> U getProperty(Class<U> property) {
		return (U) data.get(property);
	}

	@Override
	public Collection<T> values() {
		return data.values();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Property property : data.values()) {
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
		if (!(obj instanceof PropertySet)) {
			return false;
		}
		PropertySet<?> other = (PropertySet<?>) obj;
		return this.values().equals(other.values());
	}

	@Override
	public PropertySet<T> retainAll(Collection<Class<? extends T>> properties) {
		Map<Class<? extends Property>, T> newData = new HashMap<>(this.data);
		if (newData.keySet().retainAll(properties)) {
			return new MapPropertySet<>(newData);
		} else {
			return this;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean hasProperties(T... properties) {
		for (T property : properties) {
			if (getProperty((Class<? extends T>) property.getType()) != property) {
				return false;
			}
		}
		return true;
	}

	@Override
	public PropertySet<T> derive(Collection<? extends T> properties) {
		Map<Class<? extends Property>, T> newData = new HashMap<>(this.data);
		for (T property : properties) {
			newData.put(property.getType(), property);
		}
		return new MapPropertySet<>(newData);
	}
}
