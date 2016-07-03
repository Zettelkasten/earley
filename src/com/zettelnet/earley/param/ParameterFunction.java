package com.zettelnet.earley.param;

import com.zettelnet.earley.symbol.Symbol;

public interface ParameterFunction<T, P extends Parameter> {

	P passParameter(P parameter, Symbol<T> symbol);
}
