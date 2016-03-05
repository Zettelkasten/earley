package com.zettelnet.earley.param.property;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SingletonParameterFactory;

public final class PropertyParameterManager<T extends Property> implements ParameterManager<PropertyParameter<T>> {

	final Map<Class<? extends T>, T> DEFAULT_DATA;

	public PropertyParameterManager(final Collection<Class<? extends T>> allProperties) {
		DEFAULT_DATA = new HashMap<>(allProperties.size());
		for (Class<? extends T> propertyType : allProperties) {
			DEFAULT_DATA.put(propertyType, null);
		}
	}

	Map<Class<? extends T>, T> makeDataMap(Map<Class<? extends T>, T> sourceData, Map<Class<? extends T>, T> newData) {
		Map<Class<? extends T>, T> data = new HashMap<>(sourceData);
		for (Class<? extends T> propertyType : sourceData.keySet()) {
			T newProperty = newData.get(propertyType);
			if (newProperty != null) {
				data.put(propertyType, newProperty);
			}
		}
		return data;
	}

	Map<Class<? extends T>, T> makeDataMap(Map<Class<? extends T>, T> sourceData, PropertySet<T> values) {
		Map<Class<? extends T>, T> data = new HashMap<>(sourceData);
		for (Class<? extends T> propertyType : sourceData.keySet()) {
			if (values.hasProperty(propertyType)) {
				data.put(propertyType, values.getProperty(propertyType));
			}
		}
		return data;
	}

	@Override
	public PropertyParameter<T> makeParameter() {
		return new PropertyParameter<>(this);
	}

	@Override
	public PropertyParameter<T> copyParameter(PropertyParameter<T> source) {
		return source;
	}

	@Override
	public PropertyParameter<T> copyParameter(PropertyParameter<T> source, PropertyParameter<T> with) {
		return source.deriveWith(with);
	}

	@Override
	public boolean isCompatible(PropertyParameter<T> parent, PropertyParameter<T> child) {
		return parent.isCompatibleWith(child);
	}

	public PropertyParameter<T> makeParameter(PropertySet<T> values) {
		return new PropertyParameter<>(this, values);
	}

	public ParameterFactory<PropertyParameter<T>> makeParameterFactory(PropertySet<T> values) {
		return new SingletonParameterFactory<>(makeParameter(values));
	}
}
