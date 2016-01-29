package com.zettelnet.earley.input;

import java.util.BitSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class DynamicInputPositionInitializer<T> implements InputPositionInitializer<T> {

	@Override
	public SortedSet<InputPosition<T>> getInputPositions(List<T> tokens) {
		SortedSet<InputPosition<T>> positions = new TreeSet<>();
		BitSet set = new BitSet();
		while (set.cardinality() <= tokens.size()) {
			DynamicInputPosition<T> inputPosition = new DynamicInputPosition<>(tokens, (BitSet) set.clone());
			positions.add(inputPosition);
			addOne(set);
		}
		return positions;
	}

	private static void addOne(BitSet set) {
		boolean borrow = true;
		for (int i = 0; borrow; i++) {
			if (!set.get(i)) {
				set.set(i);
				borrow = false;
			} else {
				set.clear(i);
			}
		}
	}
}
