package com.zettelnet.earley.param;

import java.util.Collection;

import com.zettelnet.earley.Terminal;

public interface TokenParameterizer<T, P extends Parameter> {

	Collection<P> getTokenParameters(T token, Terminal<T> terminal);
}
