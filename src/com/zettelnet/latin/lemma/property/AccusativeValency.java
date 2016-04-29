package com.zettelnet.latin.lemma.property;

import com.zettelnet.earley.param.property.Property;

public enum AccusativeValency implements Property {

	Default("defAcc"), Omit("omitAcc");

	private final String shortName;

	private AccusativeValency(final String shortName) {
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
}
