package com.zettelnet.earley.tree;

import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;

public class NonTerminalSyntaxTreeVariant<T, P extends Parameter> implements SyntaxTreeVariant<T, P> {

	private final NonTerminal<T> symbol;
	private final Production<T, P> production;
	private final P parameter;

	private final List<SyntaxTree<T, P>> children;

	public NonTerminalSyntaxTreeVariant(final NonTerminal<T> symbol, final Production<T, P> production, final P parameter, final List<SyntaxTree<T, P>> children) {
		this.symbol = symbol;
		this.production = production;
		this.parameter = parameter;
		this.children = children;
	}

	@Override
	public NonTerminal<T> getRootSymbol() {
		return symbol;
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public Production<T, P> getProduction() {
		return production;
	}

	@Override
	public P getParameter() {
		return parameter;
	}

	@Override
	public T getToken() {
		return null;
	}

	@Override
	public List<SyntaxTree<T, P>> getChildren() {
		return children;
	}
	
	@Override
	public double getLocalProbability() {
		return production.getProbability();
	}

	@Override
	public String toString() {
		return SyntaxTrees.toString(this);
	}
}
