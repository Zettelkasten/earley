package com.zettelnet.earley.translate;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterFactory;

public class Translation<T, P extends Parameter, U, Q extends Parameter> {

	private final Production<T, P> production;
	private final ParameterFactory<T, P> keyParameter;
	
	private final TranslationTree<T, P, U, Q> tree;
	
	public Translation(final Production<T, P> production, final ParameterFactory<T, P> keyParameter, final TranslationTree<T, P, U, Q> tree) {
		this.production = production;
		this.keyParameter = keyParameter;
		this.tree = tree;
	}
	
	public Production<T, P> key() {
		return production;
	}
	
	public P keyParameter() {
		return keyParameter.makeParameter(key().key());
	}
	
	public TranslationTree<T, P, U, Q> value() {
		return tree;
	}
}
