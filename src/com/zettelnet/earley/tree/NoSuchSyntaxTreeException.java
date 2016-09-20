package com.zettelnet.earley.tree;

public class NoSuchSyntaxTreeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoSuchSyntaxTreeException() {
		super();
	}

	public NoSuchSyntaxTreeException(String msg) {
		super(msg);
	}
}
