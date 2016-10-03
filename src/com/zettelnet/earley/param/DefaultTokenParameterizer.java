package com.zettelnet.earley.param;

import java.util.Collections;

import com.zettelnet.earley.symbol.Terminal;

public class DefaultTokenParameterizer<T> implements TokenParameterizer<T, DefaultParameter> {

	@Override
	public Iterable<DefaultParameter> getTokenParameters(T token, Terminal<T> terminal) {
		return Collections.singleton(DefaultParameter.INSTANCE);
	}
}
