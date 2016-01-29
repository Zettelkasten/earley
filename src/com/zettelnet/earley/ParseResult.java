package com.zettelnet.earley;

import java.util.Collection;
import java.util.SortedMap;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;

public interface ParseResult<T, P extends Parameter> {

	boolean isComplete();

	SortedMap<InputPosition<T>, Chart<T, P>> getCharts();

	Collection<ParseTree<T>> getTreeForest();
}
