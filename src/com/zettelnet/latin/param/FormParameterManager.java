package com.zettelnet.latin.param;

import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.symbol.Symbol;

public class FormParameterManager<T> implements ParameterManager<T, FormParameter> {

	@Override
	public FormParameter makeParameter(Symbol<T> symbol) {
		return new FormParameter();
	}

	@Override
	public FormParameter copyParameter(FormParameter source, Symbol<T> symbol) {
		return source;
	}

	@Override
	public FormParameter copyParameter(FormParameter source, FormParameter with, Symbol<T> symbol) {
		return source.deriveWith(with);
	}

	@Override
	public FormParameter scanParameter(FormParameter parameter, FormParameter tokenParameter) {
		return parameter.scanWith(tokenParameter);
	}

	@Override
	public boolean isCompatible(FormParameter parent, FormParameter child) {
		return parent.isCompatibleWith(child);
	}
}
