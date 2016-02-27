package com.zettelnet.earley.tree.binary;

import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.UnbinaryTerminalSyntaxTree;

public class TerminalBinarySyntaxTree<T, P extends Parameter> implements BinarySyntaxTree<T, P> {

	private final Terminal<T> symbol;
	private final T token;

	public TerminalBinarySyntaxTree(final Terminal<T> symbol, final T token) {
		this.symbol = symbol;
		this.token = token;
	}

	@Override
	public List<BinarySyntaxTreeVariant<T, P>> getVariants() {
		return Collections.emptyList();
	}

	@Override
	public Terminal<T> getRootSymbol() {
		return symbol;
	}

	@Override
	public boolean isTerminal() {
		return true;
	}

	@Override
	public T getToken() {
		return token;
	}

	@Override
	public boolean isFirst() {
		return true;
	}

	@Override
	public SyntaxTree<T, P> toNaturalTree() {
		return new UnbinaryTerminalSyntaxTree<>(this);
	}

	@Override
	public String toString() {
		return "[" + symbol + " " + token + "]";
	}
}
