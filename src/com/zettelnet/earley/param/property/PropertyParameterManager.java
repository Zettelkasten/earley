package com.zettelnet.earley.param.property;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SingletonParameterFactory;

public final class PropertyParameterManager<T extends Property> implements ParameterManager<PropertyParameter<T>> {

	final Map<Object, T> DEFAULT_DATA;

	public PropertyParameterManager(final Collection<Class<? extends T>> allProperties) {
		DEFAULT_DATA = new HashMap<>(allProperties.size());
		for (Class<? extends T> propertyType : allProperties) {
			DEFAULT_DATA.put(propertyType, null);
		}
	}

	Map<Object, T> makeDataMap(Map<Object, T> sourceData, Map<Object, T> newData) {
		Map<Object, T> data = new HashMap<>(sourceData);
		for (Object propertyType : sourceData.keySet()) {
			T newProperty = newData.get(propertyType);
			if (newProperty != null) {
				data.put(propertyType, newProperty);
			}
		}
		return data;
	}

	Map<Object, T> makeDataMap(Map<Object, T> sourceData, PropertySet<T> values) {
		Map<Object, T> data = new HashMap<>(sourceData);
		for (Object propertyType : sourceData.keySet()) {
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
	public PropertyParameter<T> scanParameter(PropertyParameter<T> parameter, PropertyParameter<T> tokenParameter) {
		return parameter;
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
