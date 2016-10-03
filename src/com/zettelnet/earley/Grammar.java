package com.zettelnet.earley;

import java.util.Set;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterFactory;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Terminal;

public interface Grammar<T, P extends Parameter> {

	Set<NonTerminal<T>> getNonTerminals();

	Set<Terminal<T>> getTerminals();

	Set<Production<T, P>> getProductions();

	Set<Production<T, P>> getProductions(NonTerminal<T> key);

	NonTerminal<T> getStartSymbol();

	ParameterFactory<T, P> getStartSymbolParameter();

	ParameterManager<T, P> getParameterManager();
	
	TokenParameterizer<T, P> getParameterizer();

	ParameterExpression<T, P> getStartSymbolParameterExpression();
}
