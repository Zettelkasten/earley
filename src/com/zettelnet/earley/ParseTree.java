package com.zettelnet.earley;

import com.zettelnet.earley.symbol.NonTerminal;

public class ParseTree<T> extends TreeNode<T> {

	public ParseTree(NonTerminal<T> value) {
		super(value);
	}

}
