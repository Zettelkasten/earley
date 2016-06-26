package com.zettelnet.earley.translate;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public class PositionReference<T, P extends Parameter> implements AbstractReference<T, P> {

	private final SyntaxTreeVariant<T, P> parentTree;
	private final int position;
	
	public PositionReference(final SyntaxTreeVariant<T, P> parentTree, final int position) {
		this.parentTree = parentTree;
		this.position = position;
	}
	
	@Override
	public SyntaxTree<T, P> getSourceTree() {
		return parentTree.getChildren().get(position);
	}
}
