package com.zettelnet.earley.translate;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public class PositionReference<T, P extends Parameter> implements AbstractReference<T, P> {

	private final int position;
	
	public PositionReference(final int position) {
		this.position = position;
	}
	
	@Override
	public SyntaxTree<T, P> getSourceTree(SyntaxTreeVariant<T, P> parentTree) {
		assert parentTree.getChildren().size() > position : String.format("Parent tree %s has insufficient child count for position %s", parentTree, position);
		return parentTree.getChildren().get(position);
	}
}
