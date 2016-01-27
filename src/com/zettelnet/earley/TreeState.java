package com.zettelnet.earley;

import com.zettelnet.earley.param.Parameter;

public class TreeState<T, P extends Parameter> {

	private final State<T, P> state;
	private final TreeNode<T> branch;

	public TreeState(final State<T, P> state, final TreeNode<T> branch) {
		this.state = state;
		this.branch = branch;
	}

	public State<T, P> getState() {
		return state;
	}

	public TreeNode<T> getBranch() {
		return branch;
	}
}
