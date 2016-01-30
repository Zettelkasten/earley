package com.zettelnet.earley;

import java.util.ArrayList;
import java.util.List;

import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;

public class TreeNode<T> {

	private final Symbol<T> value;
	private final List<TreeNode<T>> children = new ArrayList<>();

	public TreeNode(final NonTerminal<T> value) {
		this((Symbol<T>) value);
	}

	protected TreeNode(final Symbol<T> value) {
		this.value = value;
	}

	public Symbol<T> getValue() {
		return value;
	}

	public void addChild(TreeNode<T> node) {
		this.children.add(0, node);
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(value);

		for (TreeNode<T> child : children) {
			str.append(" ");
			str.append(child);
		}
		str.append("]");
		return str.toString();
	}
}
