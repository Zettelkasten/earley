package com.zettelnet.earley.tree;

import java.util.HashSet;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.tree.binary.BinarySyntaxTree;
import com.zettelnet.earley.tree.binary.BinarySyntaxTreeVariant;

public class UnbinaryInitialSyntaxTree<T, P extends Parameter> implements SyntaxTree<T, P> {

	private final NonTerminal<T> rootSymbol;
	private final BinarySyntaxTree<T, P> node;

	public UnbinaryInitialSyntaxTree(final NonTerminal<T> rootSymbol, final BinarySyntaxTree<T, P> node) {
		this.rootSymbol = rootSymbol;
		this.node = node;
	}

	@Override
	public NonTerminal<T> getRootSymbol() {
		return rootSymbol;
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public Set<Production<T, P>> getProductions() {
		Set<Production<T, P>> set = new HashSet<>();
		for (BinarySyntaxTreeVariant<T, P> variant : node.getVariants()) {
			set.add(variant.getChildProduction());
		}
		return set;
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants() {
		Set<SyntaxTreeVariant<T, P>> set = new HashSet<>();
		for (BinarySyntaxTreeVariant<T, P> binaryVariant : node.getVariants()) {
			SyntaxTree<T, P> tree = new UnbinaryNonTerminalSyntaxTree<T, P>((NonTerminal<T>) binaryVariant.getSymbol(), binaryVariant.getChildNode());
			set.addAll(tree.getVariantsSet());
		}
		return set;
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants(Production<T, P> production) {
		Set<SyntaxTreeVariant<T, P>> set = new HashSet<>();
		for (BinarySyntaxTreeVariant<T, P> binaryVariant : node.getVariants()) {
			SyntaxTree<T, P> tree = new UnbinaryNonTerminalSyntaxTree<T, P>((NonTerminal<T>) binaryVariant.getSymbol(), binaryVariant.getChildNode());
			for (SyntaxTreeVariant<T, P> variant : tree.getVariants(production)) {
				set.add(variant);
			}
		}
		return set;
	}

	@Override
	public SyntaxTreeVariant<T, P> getVariant(int[] variantDirections) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(rootSymbol);
		Iterable<SyntaxTreeVariant<T, P>> variants = getVariants();
		for (SyntaxTreeVariant<T, P> variant : variants) {
			str.append(" ");
			str.append(variant);
		}
		str.append("]");
		return str.toString();
	}

}
