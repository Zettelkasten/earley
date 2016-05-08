package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.Property;

public interface GermanFormProperty extends Property {

	int ordinal();

	String name();

	String shortName();
}
