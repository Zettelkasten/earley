package com.zettelnet.earley.tree;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public class InitialVariant<T, P extends Parameter> implements BinarySyntaxTreeVariant<T, P> {

	private final Grammar<T, P> grammar;
	private final State<T, P> state;
	
	public InitialVariant(final Grammar<T, P> grammar, final State<T, P> state) {
		this.grammar = grammar;
		this.state = state;
	}
	
	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		return null;
	}

	@Override
	public Symbol<T> getSymbol() {
		return grammar.getStartSymbol();
	}
	
	@Override
	public Production<T, P> getChildProduction() {
		return state.getProduction();
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return new StateSyntaxTree<>(state);
	}

		
}
