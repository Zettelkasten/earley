package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.param.property.ValuesPropertyType;

public interface FormProperty extends Property {

	int ordinal();

	String name();

	String shortName();
	
	@Override
	ValuesPropertyType<? extends FormProperty> getType();
}
