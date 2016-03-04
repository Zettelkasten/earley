package com.zettelnet.earley.tree.binary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.State;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.UnbinaryNonTerminalSyntaxTree;

public class InitialStateBinarySyntaxTree<T, P extends Parameter> implements BinarySyntaxTree<T, P> {

	private final Grammar<T, P> grammar;
	private final Collection<State<T, P>> completeStates;

	public InitialStateBinarySyntaxTree(final Grammar<T, P> grammar, final Collection<State<T, P>> completeStates) {
		this.grammar = grammar;
		this.completeStates = completeStates;
	}

	@Override
	public List<BinarySyntaxTreeVariant<T, P>> getVariants() {
		List<BinarySyntaxTreeVariant<T, P>> variants = new ArrayList<>();
		for (State<T, P> state : completeStates) {
			variants.addAll(new NonTerminalBinarySyntaxTree<>(state).getVariants());
		}
		return variants;
	}

	@Override
	public boolean isFirst() {
		return true;
	}

	@Override
	public NonTerminal<T> getRootSymbol() {
		return grammar.getStartSymbol();
	}
	
	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public T getToken() {
		return null;
	}

	@Override
	public P getTokenParameter() {
		return null;
	}
	
	@Override
	public SyntaxTree<T, P> toNaturalTree() {
		return new UnbinaryNonTerminalSyntaxTree<>(this);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (isFirst()) {
			str.append("[" + getRootSymbol() + " ");
		} else {
			str.append("[n ");
		}
		Collection<BinarySyntaxTreeVariant<T, P>> variants = getVariants();
		for (BinarySyntaxTreeVariant<T, P> variant : variants) {
			str.append(" ");
			str.append(variant);
		}
		str.append("]");
		return str.toString();
	}
}
