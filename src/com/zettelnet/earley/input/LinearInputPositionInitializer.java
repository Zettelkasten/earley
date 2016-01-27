package com.zettelnet.earley.input;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class LinearInputPositionInitializer<T> implements InputPositionInitializer<T> {

	@Override
	public SortedSet<InputPosition<T>> getInputPositions(List<T> tokens) {
		SortedSet<InputPosition<T>> positions = new TreeSet<>();
		int positionCount = tokens.size() + 1;
		for (int positionIndex = 0; positionIndex < positionCount; positionIndex++) {
			InputPosition<T> inputPosition = new LinearInputPosition<>(tokens, positionIndex);
			positions.add(inputPosition);
		}
		return positions;
	}

}
