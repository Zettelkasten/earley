package com.zettelnet.german.lemma.simple.conjugation;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public enum GermanConjugationStem {

	Present, Past, Participle2, SecondPresent, Subjunctive2, Imperative;

	public static <T> Map<GermanConjugationStem, T> makeMap(T present, T past, T participle2) {
		Map<GermanConjugationStem, T> map = new EnumMap<>(GermanConjugationStem.class);
		map.put(Present, present);
		map.put(Past, past);
		map.put(Participle2, participle2);
		// defaults
		map.put(SecondPresent, map.get(Present));
		map.put(Subjunctive2, map.get(Past));
		map.put(Imperative, map.get(Present));
		return map;
	}

	public static <T> Map<GermanConjugationStem, Collection<T>> makeCollectionMap(T present, T past, T participle2) {
		Map<GermanConjugationStem, Collection<T>> map = new EnumMap<>(GermanConjugationStem.class);
		map.put(Present, Arrays.asList(present));
		map.put(Past, Arrays.asList(past));
		map.put(Participle2, Arrays.asList(participle2));
		// defaults
		map.put(SecondPresent, map.get(Present));
		map.put(Subjunctive2, map.get(Past));
		map.put(Imperative, map.get(Present));
		return map;
	}

	public static <T> Map<GermanConjugationStem, T> makeMap(T present, T past, T participle2, T secondPresent, T subjunctive2, T imperative) {
		Map<GermanConjugationStem, T> map = new EnumMap<>(GermanConjugationStem.class);
		map.put(Present, present);
		map.put(Past, past);
		map.put(Participle2, participle2);
		map.put(SecondPresent, secondPresent);
		map.put(Subjunctive2, subjunctive2);
		map.put(Imperative, imperative);
		return map;
	}

	public static <T> Map<GermanConjugationStem, Collection<T>> makeCollectionMap(T present, T past, T participle2, T secondPresent, T subjunctive2, T imperative) {
		Map<GermanConjugationStem, Collection<T>> map = new EnumMap<>(GermanConjugationStem.class);
		map.put(Present, Arrays.asList(present));
		map.put(Past, Arrays.asList(past));
		map.put(Participle2, Arrays.asList(participle2));
		map.put(SecondPresent, Arrays.asList(secondPresent));
		map.put(Subjunctive2, Arrays.asList(subjunctive2));
		map.put(Imperative, Arrays.asList(imperative));
		return map;
	}
}
