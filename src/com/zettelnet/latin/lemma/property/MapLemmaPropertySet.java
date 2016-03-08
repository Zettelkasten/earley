package com.zettelnet.latin.lemma.property;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.param.property.PropertySet;

public final class MapLemmaPropertySet implements PropertySet<LemmaProperty> {

	public static PropertySet<LemmaProperty> valueOf(LemmaProperty... properties) {
		return new MapLemmaPropertySet(Arrays.asList(properties));
	}

	public static PropertySet<LemmaProperty> valueOf(Collection<LemmaProperty> properties) {
		return new MapLemmaPropertySet(properties);
	}

	// may not be modified
	private final Map<Class<? extends LemmaProperty>, LemmaProperty> data;

	private MapLemmaPropertySet(final Collection<LemmaProperty> properties) {
		this.data = new HashMap<>(properties.size());
		for (LemmaProperty property : properties) {
			if (property != null) {
				data.put(property.getClass(), property);
			}
		}
	}

	private MapLemmaPropertySet(final Map<Class<? extends LemmaProperty>, LemmaProperty> data) {
		this.data = data;
	}

	@Override
	public <T extends LemmaProperty> boolean hasProperty(Class<T> property) {
		return data.containsKey(property);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends LemmaProperty> T getProperty(Class<T> property) {
		return (T) data.get(property);
	}

	@Override
	public Collection<LemmaProperty> values() {
		return data.values();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (LemmaProperty property : data.values()) {
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
		MapLemmaPropertySet other = (MapLemmaPropertySet) obj;
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
