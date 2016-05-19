package com.zettelnet.latin.lemma.simple.conjugation;

import java.util.EnumMap;
import java.util.Map;

public enum ConjugationStem {

	Present, Perfect, Supine;

	public static <T> Map<ConjugationStem, T> makeMap(T present, T perfect, T supine) {
		Map<ConjugationStem, T> map = new EnumMap<>(ConjugationStem.class);
		map.put(Present, present);
		map.put(Perfect, perfect);
		map.put(Supine, supine);
		return map;
	}
}
