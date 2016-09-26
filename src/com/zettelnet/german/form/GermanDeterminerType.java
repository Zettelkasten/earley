package com.zettelnet.german.form;

import com.zettelnet.earley.param.property.ValuesPropertyType;
import com.zettelnet.german.lemma.property.GermanLemmaProperty;

public enum GermanDeterminerType implements GermanFormProperty, GermanLemmaProperty {

	WithoutArticle("NoArt"), DefiniteArticle("Def"), IndefiniteArticle("Indef");

	public static final ValuesPropertyType<GermanDeterminerType> TYPE = new ValuesPropertyType<>("det", values());
	
	private final String shortName;

	private GermanDeterminerType(String shortName) {
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
	public ValuesPropertyType<GermanDeterminerType> getType() {
		return TYPE;
	}
}
