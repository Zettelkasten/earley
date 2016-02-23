package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public class EpsilonSeedVariant<T, P extends Parameter> extends AbstractBinarySyntaxTreeVariant<T, P> {

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
	public Production<T, P> getChildProduction() {
		return null;
	}

	@Override
	public BinarySyntaxTree<T, P> getChildNode() {
		return null;
	}
}
