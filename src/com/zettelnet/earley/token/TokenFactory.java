package com.zettelnet.earley.token;

import java.util.Collection;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;

public interface TokenFactory<T, P extends Parameter> {

	Collection<T> makeToken(Terminal<T> symbol, P parameter);
}
