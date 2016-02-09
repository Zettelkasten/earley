package com.zettelnet.earley.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.binary.BinarySyntaxTree;
import com.zettelnet.earley.tree.binary.BinarySyntaxTreeVariant;

public class UnbinaryNonTerminalSyntaxTree<T, P extends Parameter> implements SyntaxTree<T, P> {

	private final NonTerminal<T> symbol;
	private final BinarySyntaxTree<T, P> node;

	public UnbinaryNonTerminalSyntaxTree(final NonTerminal<T> symbol, final BinarySyntaxTree<T, P> node) {
		this.symbol = symbol;
		this.node = node;
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
	public Set<Production<T, P>> getProductions() {
		Set<Production<T, P>> set = new HashSet<>();
		for (BinarySyntaxTreeVariant<T, P> variant : node.getVariants()) {
			set.add(variant.getChildProduction());
		}
		return set;
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants() {
		// TODO Auto-generated method stub
		return Arrays.asList(getFirstVariant());
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants(Production<T, P> production) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SyntaxTreeVariant<T, P> getVariant(int[] variantDirections) {
		// TODO Auto-generated method stub
		return null;
	}

	public SyntaxTreeVariant<T, P> getFirstVariant() {
		final List<SyntaxTree<T, P>> children = new ArrayList<>();

		BinarySyntaxTree<T, P> binaryNode = node;
		do {
			BinarySyntaxTreeVariant<T, P> binaryVariant = binaryNode.getVariants().iterator().next();

			Symbol<T> symbol = binaryVariant.getSymbol();
			BinarySyntaxTree<T, P> childNode = binaryVariant.getChildNode();

			if (symbol != null) {
				SyntaxTree<T, P> child;
				if (childNode != null) {
					// non-terminal
					child = new UnbinaryNonTerminalSyntaxTree<>((NonTerminal<T>) symbol, childNode);
				} else {
					// terminal
					child = new UnbinaryTerminalSyntaxTree<>((Terminal<T>) symbol);
				}
				children.add(0, child);
			}

			binaryNode = binaryVariant.getPreNode();
		} while (binaryNode != null);

		Production<T, P> production = node.getVariants().iterator().next().getChildProduction();

		return new SimpleSyntaxTreeVariant<>(production, children);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(symbol);
		Iterable<SyntaxTreeVariant<T, P>> variants = getVariants();
		for (SyntaxTreeVariant<T, P> variant : variants) {
			str.append(" ");
			str.append(variant);
		}
		str.append("]");
		return str.toString();
	}
}
