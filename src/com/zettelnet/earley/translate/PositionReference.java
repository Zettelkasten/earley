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
		return parentTree.getChildren().get(position);
	}
}
