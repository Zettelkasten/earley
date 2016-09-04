package com.zettelnet.german.lemma.simple.conjugation;

import java.util.EnumMap;
import java.util.Map;

public enum GermanConjugationStem {

	Present, Past, Participle2;

	public static <T> Map<GermanConjugationStem, T> makeMap(T present, T past, T participle2) {
		Map<GermanConjugationStem, T> map = new EnumMap<>(GermanConjugationStem.class);
		map.put(Present, present);
		map.put(Past, past);
		map.put(Participle2, participle2);
		return map;
	}
}
