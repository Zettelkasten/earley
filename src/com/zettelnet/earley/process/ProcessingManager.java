package com.zettelnet.earley.process;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;

public interface ProcessingManager<T, P extends Parameter, R> {

	R process(SyntaxTree<T, P> tree);
}
