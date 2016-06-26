package com.zettelnet.earley.translate;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;

public interface AbstractReference<T, P extends Parameter> {

	SyntaxTree<T, P> getSourceTree();
}
