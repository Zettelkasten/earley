package com.zettelnet.earley.tree;

import java.util.Arrays;
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
	public Set<Production<T, P>> getProductions() {
		return Collections.emptySet();
	}

	private SyntaxTreeVariant<T, P> getVariant() {
		return new TerminalSyntaxTreeVariant<>(this, node.getTokenParameter(), node.getToken());
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants() {
		return Arrays.asList(getVariant());
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants(Production<T, P> production) {
		return Collections.emptyList();
	}

	@Override
	public SyntaxTreeVariant<T, P> getVariant(Iterator<Integer> variantDirections) throws NoSuchSyntaxTreeException {
		return getVariant();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(getRootSymbol());
		Iterable<SyntaxTreeVariant<T, P>> variants = getVariants();
		for (SyntaxTreeVariant<T, P> variant : variants) {
			str.append(" ");
			str.append(variant);
		}
		str.append("]");
		return str.toString();
	}
}
