package com.zettelnet.earley.translate;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.symbol.Symbol;

public class Translation<T, P extends Parameter, U, Q extends Parameter> {

	private final Symbol<T> keySymbol;
	private final ParameterFactory<T, P> keyParameter;
	
	private final TranslationTree<T, P, U, Q> tree;
	
	public Translation(final Symbol<T> keySymbol, final ParameterFactory<T, P> keyParameter, final TranslationTree<T, P, U, Q> tree) {
		this.keySymbol = keySymbol;
		this.keyParameter = keyParameter;
		this.tree = tree;
	}
	
	public Symbol<T> key() {
		return keySymbol;
	}
	
	public P keyParameter() {
		return keyParameter.makeParameter(keySymbol);
	}
	
	public TranslationTree<T, P, U, Q> value() {
		return tree;
	}
	
	@Override
	public String toString() {
		return tree.toString();
	}
}
