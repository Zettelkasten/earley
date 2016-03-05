package com.zettelnet.earley.param.property;

import java.util.Map;

import com.zettelnet.earley.param.Parameter;

public class PropertyParameter<T extends Property> implements Parameter {

	private final PropertyParameterManager<T> manager;

	// may not be modified
	private final Map<Class<? extends T>, T> data;

	public PropertyParameter(final PropertyParameterManager<T> manager) {
		this(manager, manager.DEFAULT_DATA);
	}

	public PropertyParameter(final PropertyParameterManager<T> manager, final PropertySet<T> values) {
		this(manager, manager.makeDataMap(manager.DEFAULT_DATA, values));
	}

	private PropertyParameter(final PropertyParameterManager<T> manager, Map<Class<? extends T>, T> data) {
		this.manager = manager;
		this.data = data;
	}

	public boolean isCompatibleWith(PropertyParameter<T> other) {
		for (Map.Entry<Class<? extends T>, T> entry : data.entrySet()) {
			Class<? extends T> propertyType = entry.getKey();
			T parentValue = entry.getValue();
			T childValue = other.data.get(propertyType);

			if (parentValue != null && childValue != null && !parentValue.equals(childValue)) {
				return false;
			}
		}

		return true;
	}

	public PropertyParameter<T> deriveWith(PropertyParameter<T> with) {
		return new PropertyParameter<T>(manager, manager.makeDataMap(this.data, with.data));
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (T property : data.values()) {
			if (property != null) {
				str.append(' ');
				str.append(property);
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
		PropertyParameter<?> other = (PropertyParameter<?>) obj;
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
