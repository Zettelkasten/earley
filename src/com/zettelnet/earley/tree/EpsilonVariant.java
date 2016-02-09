package com.zettelnet.earley.tree;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public class EpsilonVariant<T, P extends Parameter> implements BinarySyntaxTreeVariant<T, P> {

	@Override
	public BinarySyntaxTree<T, P> getPreNode() {
		return null;
	}

	@Override
	public Symbol<T> getSymbol() {
		return null;
	}

	@Override
	public Production<T, P> getChildProduction() {
		return null;
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return null;
	}

	@Override
	public String toString() {
		return "EpsilonVariant";
	}
}