package com.zettelnet.latin.param;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.translate.ParameterTranslator;
import com.zettelnet.latin.token.Token;

public class MappingTableParameterTranslator implements ParameterTranslator<Token, FormParameter, FormParameter> {

	private final Map<Property, Property> mappingTable;

	public MappingTableParameterTranslator() {
		this.mappingTable = new HashMap<>();
	}
	
	public void registerMapping(Property source, Property result) {
		mappingTable.put(source, result);
	}

	@Override
	public FormParameter translateParameter(final FormParameter source, final Symbol<Token> symbol) {
		Set<Property> output = new HashSet<>();
		
		for (Set<? extends Property> propertySet : source.getProperties().values()) {
			for (Property property : propertySet) {
				output.add(translateProperty(property));
			}
		}
		
		// TODO this method does not use symbol specific parameters
		return new FormParameter(output.toArray(new Property[output.size()]));
	}
	
	public Property translateProperty(final Property source) {
		if (mappingTable.containsKey(source)) {
			return mappingTable.get(source);
		} else {
			return source;
		}
	}

}
