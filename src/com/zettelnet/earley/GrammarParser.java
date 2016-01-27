package com.zettelnet.earley;

import java.util.List;

import com.zettelnet.earley.param.Parameter;

public interface GrammarParser<T, P extends Parameter> {

	ParseResult<T, P> parse(List<T> tokens);
}
