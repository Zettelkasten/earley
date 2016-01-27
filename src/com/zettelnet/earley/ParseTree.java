package com.zettelnet.earley;

public class ParseTree<T> extends TreeNode<T> {

	public ParseTree(NonTerminal<T> value) {
		super(value);
	}

}
