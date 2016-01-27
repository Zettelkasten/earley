package com.zettelnet.earley;

public class TerminalNode<T> extends TreeNode<T> {

	private final Terminal<T> symbol;
	
	public TerminalNode(final Terminal<T> symbol) {
		super(symbol);
		this.symbol = symbol;
	}
	
	public Terminal<T> getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return symbol.toString();
	}
}
