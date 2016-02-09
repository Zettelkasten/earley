package com.zettelnet.earley.tree;

import java.util.Collection;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public interface SyntaxTree<T, P extends Parameter> {

	Symbol<T> getRootSymbol();

	boolean isTerminal();

	Collection<SyntaxTreeVariant<T, P>> getVariants();
}
