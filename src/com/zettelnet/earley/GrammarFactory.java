package com.zettelnet.earley;

import com.zettelnet.earley.param.Parameter;

public interface GrammarFactory<T, P extends Parameter> {

	Grammar<T, P> makeGrammar();
}
