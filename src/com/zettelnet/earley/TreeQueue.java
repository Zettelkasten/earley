package com.zettelnet.earley;

import java.util.Queue;

import com.zettelnet.earley.param.Parameter;

public class TreeQueue<T, P extends Parameter> {

	private final ParseTree<T> tree;
	private final Queue<TreeState<T, P>> queue;

	public TreeQueue(final ParseTree<T> tree, final Queue<TreeState<T, P>> queue) {
		this.tree = tree;
		this.queue = queue;
	}

	public ParseTree<T> getTree() {
		return tree;
	}

	public Queue<TreeState<T, P>> getQueue() {
		return queue;
	}
}
