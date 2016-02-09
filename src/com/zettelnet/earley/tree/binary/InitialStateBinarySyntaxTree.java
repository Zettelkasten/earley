package com.zettelnet.earley.tree.binary;

import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.earley.State;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.UnbinaryInitialSyntaxTree;

public class InitialStateBinarySyntaxTree<T, P extends Parameter> implements BinarySyntaxTree<T, P> {

	private final NonTerminal<T> rootSymbol;
	private final Collection<State<T, P>> completeStates;

	public InitialStateBinarySyntaxTree(final NonTerminal<T> rootSymbol, final Collection<State<T, P>> completeStates) {
		this.rootSymbol = rootSymbol;
		this.completeStates = completeStates;
	}

	@Override
	public Collection<BinarySyntaxTreeVariant<T, P>> getVariants() {
		Collection<BinarySyntaxTreeVariant<T, P>> variants = new ArrayList<>();
		for (State<T, P> state : completeStates) {
			variants.add(new InitialVariant<>(rootSymbol, state));
		}
		return variants;
	}

	@Override
	public SyntaxTree<T, P> toNaturalTree() {
		return new UnbinaryInitialSyntaxTree<>(rootSymbol, this);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[n");
		Collection<BinarySyntaxTreeVariant<T, P>> variants = getVariants();
		for (BinarySyntaxTreeVariant<T, P> variant : variants) {
			str.append(" ");
			str.append(variant);
		}
		str.append("]");
		return str.toString();
	}
}
