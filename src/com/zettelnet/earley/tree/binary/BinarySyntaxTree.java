package com.zettelnet.earley.tree.binary;

import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;

public interface BinarySyntaxTree<T, P extends Parameter> {

	List<BinarySyntaxTreeVariant<T, P>> getVariants();
	
	SyntaxTree<T, P> toNaturalTree();
}
