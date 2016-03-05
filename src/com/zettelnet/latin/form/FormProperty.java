package com.zettelnet.latin.form;

import com.zettelnet.earley.param.property.Property;

public interface FormProperty extends Property {

	int ordinal();

	String name();

	String shortName();
}
