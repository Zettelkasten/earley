package com.zettelnet.earley.tree;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.binary.BinarySyntaxTree;

public class UnbinaryTerminalSyntaxTree<T, P extends Parameter> implements SyntaxTree<T, P> {

	private final BinarySyntaxTree<T, P> node;
	
	public UnbinaryTerminalSyntaxTree(final BinarySyntaxTree<T, P> node) {
		this.node = node;
	}
	
	@Override
	public Terminal<T> getRootSymbol() {
		return (Terminal<T>) node.getRootSymbol();
	}

	@Override
	public boolean isTerminal() {
		return true;
	}

	@Override
	public T getToken() {
		return node.getToken();
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
	public SyntaxTreeVariant<T, P> getVariant(Iterator<Integer> variantDirections) throws NoSuchSyntaxTreeException {
		throw new NoSuchSyntaxTreeException();
	}
	
	@Override
	public String toString() {
		return "[" + getRootSymbol() + " " + getToken() + "]";
	}
}
