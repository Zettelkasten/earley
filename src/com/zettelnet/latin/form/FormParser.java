package com.zettelnet.latin.form;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.param.property.PropertySetParser;
import com.zettelnet.earley.param.property.ValuesPropertyType;

public class FormParser implements PropertySetParser<Form> {

	public static final FormParser INSTANCE = makeDefault();

	private static FormParser makeDefault() {
		FormParser instance = new FormParser();
		instance.registerPropertyTypes(Form.ALL_PROPERTIES);
		return instance;
	}

	private static final String DELIMITER = "\\s+";

	private final Map<String, FormProperty> registeredProperties;

	public FormParser() {
		this.registeredProperties = new HashMap<>();
	}

	@Override
	public Form parse(String raw) throws IOException {
		Set<FormProperty> properties = new HashSet<>();

		String[] tokens = raw.split(DELIMITER);
		for (String token : tokens) {
			properties.add(parseToken(token));
		}

		return Form.withValues(properties);
	}

	private FormProperty parseToken(String token) {
		return registeredProperties.get(token);
	}

	public void registerProperty(FormProperty property) {
		registeredProperties.put(property.name(), property);
		registeredProperties.put(property.shortName(), property);
	}

	public void registerPropertyTypes(Collection<ValuesPropertyType<? extends FormProperty>> propertyTypes) {
		for (ValuesPropertyType<? extends FormProperty> propertyType : propertyTypes) {
			for (FormProperty property : propertyType.getValues()) {
				registerProperty(property);
			}
		}
	}
}
