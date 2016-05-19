package com.zettelnet.latin.lemma.simple;

import java.util.EnumMap;
import java.util.Map;

public enum VerbStem {

	Present, Perfect, Supine;

	public static <T> Map<VerbStem, T> makeMap(T present, T perfect, T supine) {
		Map<VerbStem, T> map = new EnumMap<>(VerbStem.class);
		map.put(Present, present);
		map.put(Perfect, perfect);
		map.put(Supine, supine);
		return map;
	}
}
