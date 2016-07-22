package com.zettelnet.earley.tree;

import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;

public class TerminalSyntaxTreeVariant<T, P extends Parameter> implements SyntaxTreeVariant<T, P> {

	private final Terminal<T> symbol;
	private final P parameter;
	private final T token;

	public TerminalSyntaxTreeVariant(final Terminal<T> symbol, final P parameter, final T token) {
		this.symbol = symbol;
		this.parameter = parameter;
		this.token = token;
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
	public Production<T, P> getProduction() {
		return null;
	}

	@Override
	public P getParameter() {
		return parameter;
	}

	@Override
	public T getToken() {
		return token;
	}

	@Override
	public List<SyntaxTree<T, P>> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return SyntaxTrees.toString(this);
	}
}
