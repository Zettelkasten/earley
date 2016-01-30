package com.zettelnet.earley.param;

import com.zettelnet.earley.symbol.Terminal;

public interface TokenParameterizer<T, P extends Parameter> {

	Iterable<P> getTokenParameters(T token, Terminal<T> terminal);
}
