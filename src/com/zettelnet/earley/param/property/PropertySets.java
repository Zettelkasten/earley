package com.zettelnet.earley.param.property;

import java.util.HashMap;
import java.util.Map;

public final class PropertySets {

	private PropertySets() {
	}

	public static <A extends Property, B extends Property> PropertySet<?> derive(PropertySet<A> a, PropertySet<B> b) {
		Map<Class<? extends Property>, Property> newData = new HashMap<>();
		for (A property : a.values()) {
			newData.put(property.getClass(), property);
		}
		for (B property : b.values()) {
			// will override
			newData.put(property.getClass(), property);
		}
		return MapPropertySet.withValues(newData.values());
	}
}
