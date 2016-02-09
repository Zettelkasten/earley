package com.zettelnet.earley.tree.binary;

import java.util.Collection;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;

public interface BinarySyntaxTree<T, P extends Parameter> {

	Collection<BinarySyntaxTreeVariant<T, P>> getVariants();
	
	SyntaxTree<T, P> toNaturalTree();
}
