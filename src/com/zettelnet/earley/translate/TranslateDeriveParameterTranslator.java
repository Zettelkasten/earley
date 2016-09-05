package com.zettelnet.earley.translate;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.symbol.Symbol;

public class TranslateDeriveParameterTranslator<T, P extends Parameter, U, Q extends Parameter> implements ParameterTranslator<T, P, U, Q> {

	private final ParameterManager<U, Q> parameterManager;

	private final ParameterTranslator<T, P, U, Q> translator;

	private final ParameterFactory<U, Q> deriveWith;

	public TranslateDeriveParameterTranslator(final ParameterManager<U, Q> parameterManager, final ParameterTranslator<T, P, U, Q> translator, final ParameterFactory<U, Q> deriveWith) {
		this.parameterManager = parameterManager;
		this.translator = translator;
		this.deriveWith = deriveWith;
	}

	@Override
	public Q translateParameter(P sourceParameter, Symbol<T> sourceSymbol, Symbol<U> targetSymbol) {
		Q translated = translator.translateParameter(sourceParameter, sourceSymbol, targetSymbol);
		return parameterManager.copyParameter(translated, deriveWith.makeParameter(targetSymbol), targetSymbol);
	}
}
