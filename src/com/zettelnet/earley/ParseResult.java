package com.zettelnet.earley;

import java.util.Collection;
import java.util.SortedMap;

import com.zettelnet.earley.input.InputPosition;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;

public interface ParseResult<T, P extends Parameter> {

	boolean isComplete();

	SortedMap<InputPosition<T>, Chart<T, P>> getCharts();

	Collection<State<T, P>> getCompleteStates();

	SyntaxTree<T, P> getSyntaxTree();
}
