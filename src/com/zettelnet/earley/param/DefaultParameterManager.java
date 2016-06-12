package com.zettelnet.earley.param;

import com.zettelnet.earley.symbol.Symbol;

public class DefaultParameterManager<T> implements ParameterManager<T, DefaultParameter> {

	@Override
	public DefaultParameter makeParameter(Symbol<T> symbol) {
		return DefaultParameter.INSTANCE;
	}

	@Override
	public DefaultParameter copyParameter(DefaultParameter origin, Symbol<T> symbol) {
		return DefaultParameter.INSTANCE;
	}

	@Override
	public DefaultParameter copyParameter(DefaultParameter origin, DefaultParameter with, Symbol<T> symbol) {
		return DefaultParameter.INSTANCE;
	}

	@Override
	public DefaultParameter scanParameter(DefaultParameter parameter, DefaultParameter tokenParameter) {
		return DefaultParameter.INSTANCE;
	}

	@Override
	public boolean isCompatible(DefaultParameter parent, DefaultParameter child) {
		// no state -> always compatible
		return true;
	}
}
