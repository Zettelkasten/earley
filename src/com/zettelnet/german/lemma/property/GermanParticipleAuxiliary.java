package com.zettelnet.german.lemma.property;

import com.zettelnet.earley.param.property.SimplePropertyType;

public enum GermanParticipleAuxiliary implements GermanLemmaProperty {

	ToHave("PartHave"), ToBe("PartBe");
	
	public static final Object TYPE = new SimplePropertyType("partAux");

	private final String shortName;

	private GermanParticipleAuxiliary(final String shortName) {
		this.shortName = shortName;
	}
	
	@Override
	public String shortName() {
		return shortName;
	}

	@Override
	public String toString() {
		return shortName();
	}
	@Override
	public Object getType() {
		return TYPE;
	}
}
