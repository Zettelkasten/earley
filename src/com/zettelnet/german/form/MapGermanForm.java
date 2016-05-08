package com.zettelnet.german.form;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public final class MapGermanForm implements GermanForm {

	public static GermanForm valueOf(GermanFormProperty... properties) {
		return new MapGermanForm(Arrays.asList(properties));
	}

	public static GermanForm valueOf(Collection<GermanFormProperty> properties) {
		return new MapGermanForm(properties);
	}

	// may not be modified
	private final Map<Class<? extends GermanFormProperty>, GermanFormProperty> data;

	private MapGermanForm(final Collection<GermanFormProperty> properties) {
		this.data = new HashMap<>(properties.size());
		for (GermanFormProperty property : properties) {
			if (property != null) {
				data.put(property.getClass(), property);
			}
		}
	}

	private MapGermanForm(final Map<Class<? extends GermanFormProperty>, GermanFormProperty> data) {
		this.data = data;
	}

	@Override
	public boolean hasProperty(Class<? extends GermanFormProperty> property) {
		return data.containsKey(property);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends GermanFormProperty> T getProperty(Class<T> property) {
		return (T) data.get(property);
	}

	@Override
	public Collection<GermanFormProperty> values() {
		return data.values();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (Class<? extends GermanFormProperty> propertyType : GermanForm.ALL_PROPERTIES_SORTED) {
			GermanFormProperty property = getProperty(propertyType);
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
		MapGermanForm other = (MapGermanForm) obj;
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
	public GermanForm retainAll(Collection<Class<? extends GermanFormProperty>> properties) {
		Map<Class<? extends GermanFormProperty>, GermanFormProperty> newData = new HashMap<>(this.data);
		if (newData.keySet().retainAll(properties)) {
			return new MapGermanForm(newData);
		} else {
			return this;
		}
	}

	@Override
	public boolean hasProperties(GermanFormProperty... properties) {
		for (GermanFormProperty property : properties) {
			if (getProperty(property.getClass()) != property) {
				return false;
			}
		}
		return true;
	}

	@Override
	public GermanForm derive(Collection<? extends GermanFormProperty> properties) {
		Map<Class<? extends GermanFormProperty>, GermanFormProperty> newData = new HashMap<>(this.data);
		for (GermanFormProperty property : properties) {
			newData.put(property.getClass(), property);
		}
		return new MapGermanForm(newData);
	}

	@Override
	public int compareTo(GermanForm other) {
		if (this.equals(other)) {
			return 0;
		} else {
			ListIterator<Class<? extends GermanFormProperty>> i = GermanForm.ALL_PROPERTIES_SORTED.listIterator(GermanForm.ALL_PROPERTIES_SORTED.size());
			while (i.hasPrevious()) {
				Class<? extends GermanFormProperty> propertyType = i.previous();
				GermanFormProperty thisProperty = this.getProperty(propertyType);
				GermanFormProperty otherProperty = other.getProperty(propertyType);

				if (thisProperty != null && otherProperty != null && thisProperty.ordinal() != otherProperty.ordinal()) {
					return thisProperty.ordinal() - otherProperty.ordinal();
				}
			}
			throw new AssertionError("Form properties technically equal but form is not equal");
		}
	}
}
