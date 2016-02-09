package com.zettelnet.earley.tree;

import java.util.Collections;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;

public class UnbinaryTerminalSyntaxTree<T, P extends Parameter> implements SyntaxTree<T, P> {

	private final Terminal<T> symbol;

	public UnbinaryTerminalSyntaxTree(final Terminal<T> symbol) {
		this.symbol = symbol;
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
	public Set<Production<T, P>> getProductions() {
		return Collections.emptySet();
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants() {
		return Collections.emptyList();
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants(Production<T, P> production) {
		return Collections.emptyList();
	}

	@Override
	public SyntaxTreeVariant<T, P> getVariant(int[] variantDirections) {
		throw new NoSuchSyntaxTreeException();
	}
	
	@Override
	public String toString() {
		return "[" + symbol + "]";
	}
}
