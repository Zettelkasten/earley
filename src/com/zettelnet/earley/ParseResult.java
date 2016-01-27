package com.zettelnet.earley;

import java.util.Collection;
import java.util.List;

import com.zettelnet.earley.param.Parameter;

public interface ParseResult<T, P extends Parameter> {

	boolean isComplete();

	List<Chart<T, P>> getCharts();

	Collection<ParseTree<T>> getTreeForest();
}
