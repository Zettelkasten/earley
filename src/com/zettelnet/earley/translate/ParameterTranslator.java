package com.zettelnet.earley.translate;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public interface ParameterTranslator<T, P extends Parameter, Q extends Parameter> {

	Q translateParameter(P sourceParameter, Symbol<T> sourceSymbol);
}
