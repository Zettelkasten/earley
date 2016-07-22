package com.zettelnet.latin.param;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.translate.ParameterTranslator;

public class MappingTableParameterTranslator<T, U> implements ParameterTranslator<T, FormParameter, U, FormParameter> {

	private final FormParameterManager<U> parameterManager;

	private final Map<Property, Property> mappingTable;

	public MappingTableParameterTranslator(final FormParameterManager<U> translatedParameterManager) {
		this.parameterManager = translatedParameterManager;
		this.mappingTable = new HashMap<>();
	}

	public void registerMapping(Property source, Property result) {
		mappingTable.put(source, result);
	}

	@Override
	public FormParameter translateParameter(final FormParameter source, final Symbol<T> sourceSymbol, final Symbol<U> targetSymbol) {
		Set<Property> output = new HashSet<>();

		for (Set<? extends Property> propertySet : source.getProperties().values()) {
			for (Property property : propertySet) {
				output.add(translateProperty(property));
			}
		}

		// TODO this is an ugly fix to use symbol specific parameters
		return parameterManager.copyParameter(new FormParameter(output.toArray(new Property[output.size()])), targetSymbol);
	}

	public Property translateProperty(final Property source) {
		if (mappingTable.containsKey(source)) {
			return mappingTable.get(source);
		} else {
			return source;
		}
	}

}
