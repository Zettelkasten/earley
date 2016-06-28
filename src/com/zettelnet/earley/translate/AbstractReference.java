package com.zettelnet.earley.translate;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public interface AbstractReference<T, P extends Parameter> {

	SyntaxTree<T, P> getSourceTree(SyntaxTreeVariant<T, P> parentTree);
}
