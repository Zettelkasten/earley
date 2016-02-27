package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public class EpsilonSeedVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

	private final State<T, P> state;

	public EpsilonSeedVariant(State<T, P> state) {
		this.state = state;
	}

	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		return null;
	}

	@Override
	public Symbol<T> getSymbol() {
		return null;
	}

	@Override
	public T getToken() {
		return null;
	}

	@Override
	public boolean isFirst() {
		return true;
	}

	@Override
	public Production<T, P> getProduction() {
		return state.getProduction();
	}

	@Override
	public P getParameter() {
		return state.getParameter();
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return null;
	}
}
