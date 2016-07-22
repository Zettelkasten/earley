package com.zettelnet.latin.form;

import java.util.EnumMap;
import java.util.Map;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum Genus implements FormProperty {

	Masculine("m"), Feminine("f"), Neuter("n");

	public static final ValuesPropertyType<Genus> TYPE = new ValuesPropertyType<>("g", values());

	private final String shortName;

	private Genus(final String shortName) {
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
	public ValuesPropertyType<Genus> getType() {
		return TYPE;
	}

	public static <T> Map<Genus, T> makeMap(T masculine, T feminine, T neuter) {
		Map<Genus, T> map = new EnumMap<>(Genus.class);
		map.put(Masculine, masculine);
		map.put(Feminine, feminine);
		map.put(Neuter, neuter);
		return map;
	}
}
