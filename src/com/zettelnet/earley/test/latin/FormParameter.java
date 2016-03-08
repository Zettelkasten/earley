package com.zettelnet.earley.test.latin;

import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;

public final class FormParameter implements Parameter {

	private static final Map<Class<? extends FormProperty>, FormProperty> DEFAULT_DATA;

	static {
		DEFAULT_DATA = new HashMap<>(Form.ALL_PROPERTIES.size());
		for (Class<? extends FormProperty> propertyType : Form.ALL_PROPERTIES) {
			DEFAULT_DATA.put(propertyType, null);
		}
	}

	private static Map<Class<? extends FormProperty>, FormProperty> makeDataMap(Map<Class<? extends FormProperty>, FormProperty> sourceData, Map<Class<? extends FormProperty>, FormProperty> newData) {
		Map<Class<? extends FormProperty>, FormProperty> data = new HashMap<>(sourceData);
		for (Class<? extends FormProperty> propertyType : sourceData.keySet()) {
			FormProperty newProperty = newData.get(propertyType);
			if (newProperty != null) {
				data.put(propertyType, newProperty);
			}
		}
		return data;
	}

	private static Map<Class<? extends FormProperty>, FormProperty> makeDataMap(Map<Class<? extends FormProperty>, FormProperty> sourceData, Form form) {
		Map<Class<? extends FormProperty>, FormProperty> data = new HashMap<>(sourceData);
		for (Class<? extends FormProperty> propertyType : sourceData.keySet()) {
			if (form.hasProperty(propertyType)) {
				data.put(propertyType, form.getProperty(propertyType));
			}
		}
		return data;
	}

	// may not be modified
	private final Map<Class<? extends FormProperty>, FormProperty> data;

	private final Determination cause;

	public FormParameter() {
		this(DEFAULT_DATA);
	}

	public FormParameter(final FormProperty... formProperties) {
		this(makeDataMap(DEFAULT_DATA, Form.withValues(formProperties)));
	}

	public FormParameter(final Form form) {
		this(makeDataMap(DEFAULT_DATA, form));
	}

	public FormParameter(final Determination cause) {
		this(makeDataMap(DEFAULT_DATA, cause.getForm()), cause);
	}

	private FormParameter(final Map<Class<? extends FormProperty>, FormProperty> data) {
		this(data, null);
	}

	private FormParameter(final Map<Class<? extends FormProperty>, FormProperty> data, final Determination cause) {
		this.data = data;
		this.cause = cause;
	}

	public boolean isCompatibleWith(FormParameter other) {
		for (Map.Entry<Class<? extends FormProperty>, FormProperty> entry : data.entrySet()) {
			Class<? extends FormProperty> propertyType = entry.getKey();
			FormProperty parentValue = entry.getValue();
			FormProperty childValue = other.data.get(propertyType);

			if (parentValue != null && childValue != null && !parentValue.equals(childValue)) {
				return false;
			}
		}

		return true;
	}

	public FormParameter deriveWith(FormParameter with) {
		return new FormParameter(makeDataMap(this.data, with.data), with.cause);
	}

	public Form toForm() {
		return Form.withValues(data.values());
	}
	
	public Determination getCause() {
		return cause;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for (FormProperty property : data.values()) {
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
		FormParameter other = (FormParameter) obj;
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
