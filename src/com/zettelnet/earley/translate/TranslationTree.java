package com.zettelnet.earley.translate;

import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public interface TranslationTree<T, P extends Parameter, U, Q extends Parameter> {

	Symbol<U> getRootSymbol();
	
	boolean isAbstract();
	
	AbstractReference<T, P> getAbstractReference();

	ParameterTranslator<P, Q> getParameterTranslator();

	List<TranslationTree<T, P, U, Q>> getChildren();
}
