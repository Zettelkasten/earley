package com.zettelnet.earley.translate;

import java.util.Collection;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public interface Translator<T, P extends Parameter, U, Q extends Parameter> {

	SyntaxTree<U, Q> translate(SyntaxTree<T, P> sourceTree);

	Collection<U> makeToken(Terminal<U> symbol, Q parameter);
	
	Set<TranslationTree<T, P, U, Q>> getTranslationTrees(SyntaxTreeVariant<T, P> sourceVariant);
}
