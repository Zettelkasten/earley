package com.zettelnet.earley.translate;

import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public interface TranslationTreeVariant<T, P extends Parameter, U, Q extends Parameter> {

	Symbol<U> getRootSymbol();
	
	boolean isTerminal();
	
	boolean isAbstract();
	
	AbstractReference<T, P> getAbstractReference();

	ParameterTranslator<P, Q> getParameterTranslator();

	List<TranslationTreeVariant<T, P, U, Q>> getChildren();
}
