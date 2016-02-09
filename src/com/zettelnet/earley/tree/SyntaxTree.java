package com.zettelnet.earley.tree;

import java.util.Collection;

import com.zettelnet.earley.Symbol;
import com.zettelnet.earley.param.Parameter;

public interface SyntaxTree<T, P extends Parameter> {

	Symbol<T> getRootSymbol();

	boolean isTerminal();

	Collection<SyntaxTreeVariant<T, P>> getVariants();
}
