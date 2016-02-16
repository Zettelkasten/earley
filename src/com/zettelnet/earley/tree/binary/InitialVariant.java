package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;

public class InitialVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final NonTerminal<T> rootSymbol;
	private final State<T, P> state;

	public InitialVariant(final NonTerminal<T> rootSymbol, final State<T, P> state) {
		this.rootSymbol = rootSymbol;
		this.state = state;
	}

	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		return null;
	}

	@Override
	public NonTerminal<T> getSymbol() {
		return rootSymbol;
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
