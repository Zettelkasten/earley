package com.zettelnet.earley.param;

import com.zettelnet.earley.Terminal;

public interface TokenParameterizer<T, P extends Parameter> {

	Iterable<P> getTokenParameters(T token, Terminal<T> terminal);
}
