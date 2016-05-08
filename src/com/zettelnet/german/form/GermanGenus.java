package com.zettelnet.german.form;

import java.util.EnumMap;
import java.util.Map;

public enum GermanGenus implements GermanFormProperty {

	Masculine("m"), Feminine("f"), Neuter("n");

	private final String shortName;

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

	public static <T> Map<GermanGenus, T> makeMap(T masculine, T feminine, T neuter) {
		Map<GermanGenus, T> map = new EnumMap<>(GermanGenus.class);
		map.put(Masculine, masculine);
		map.put(Feminine, feminine);
		map.put(Neuter, neuter);
		return map;
	}
}
