package com.zettelnet.earley.tree;

import java.util.Arrays;
import java.util.Iterator;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.binary.BinarySyntaxTree;

/*
 * This is actually a DESIGN FLAW. Right now, there are no BinarySyntaxTreeVariants for terminal symbols,
 * but these are necessary, for instance because they hold information about the state probability.
 */
public class UnbinaryTerminalSyntaxTree<T, P extends Parameter> implements SyntaxTree<T, P> {

	private final BinarySyntaxTree<T, P> node;

	public UnbinaryTerminalSyntaxTree(final BinarySyntaxTree<T, P> node) {
		this.node = node;
	}

	private SyntaxTreeVariant<T, P> getVariant() {
		return new TerminalSyntaxTreeVariant<>((Terminal<T>) node.getRootSymbol(), node.getTokenParameter(), node.getToken(), 1);
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants() {
		return Arrays.asList(getVariant());
	}

	@Override
	public SyntaxTreeVariant<T, P> getVariant(Iterator<Integer> variantDirections) throws NoSuchSyntaxTreeException {
		return getVariant();
	}

	@Override
	public String toString() {
		return SyntaxTrees.toString(this);
	}
}
