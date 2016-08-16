package com.zettelnet.earley.tree;

import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public interface SyntaxTreeVariant<T, P extends Parameter> {

	Symbol<T> getRootSymbol();

	boolean isTerminal();
	
	Production<T, P> getProduction();
	
	P getParameter();

	T getToken();

	List<SyntaxTree<T, P>> getChildren();
	
	double getLocalProbability();
}
