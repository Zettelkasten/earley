package com.zettelnet.german.form;

import java.util.EnumMap;
import java.util.Map;

import com.zettelnet.earley.param.property.ValuesPropertyType;

public enum GermanGenus implements GermanFormProperty {

	Masculine("m"), Feminine("f"), Neuter("n");

	private final String shortName;

	public static final ValuesPropertyType<GermanGenus> TYPE = new ValuesPropertyType<>("g", values());

	private GermanGenus(final String shortName) {
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
	public ValuesPropertyType<GermanGenus> getType() {
		return TYPE;
	}

	public static <T> Map<GermanGenus, T> makeMap(T masculine, T feminine, T neuter) {
		Map<GermanGenus, T> map = new EnumMap<>(GermanGenus.class);
		map.put(Masculine, masculine);
		map.put(Feminine, feminine);
		map.put(Neuter, neuter);
		return map;
	}
}
