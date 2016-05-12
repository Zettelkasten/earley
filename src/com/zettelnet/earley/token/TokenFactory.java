package com.zettelnet.earley.token;

import java.util.Collection;

import com.zettelnet.earley.param.Parameter;

public interface TokenFactory<T, P extends Parameter> {

	Collection<T> makeToken(P parameter);
}
