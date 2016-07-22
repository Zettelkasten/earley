package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.Property;
import com.zettelnet.earley.param.property.ValuesPropertyType;

public interface GermanFormProperty extends Property {

	int ordinal();

	String name();

	String shortName();

	@Override
	ValuesPropertyType<? extends GermanFormProperty> getType();
}
