package com.zettelnet.earley.input;

import java.util.List;
import java.util.SortedSet;

public interface InputPositionInitializer<T> {

	SortedSet<InputPosition<T>> getInputPositions(List<T> tokens);
}
