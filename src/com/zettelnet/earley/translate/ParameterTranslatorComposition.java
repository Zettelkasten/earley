package com.zettelnet.earley.translate;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.param.ParameterFunction;
import com.zettelnet.earley.symbol.Symbol;

public class ParameterTranslatorComposition<T, P extends Parameter, U, Q extends Parameter> implements ParameterTranslator<T, P, U, Q> {

	private final ParameterTranslator<T, P, U, Q> translator;
	private final List<ParameterFunction<T, P>> nestedExpressions;

	@SafeVarargs
	public ParameterTranslatorComposition(final ParameterTranslator<T, P, U, Q> translator, final ParameterFunction<T, P>... nestedExpressions) {
		this.translator = translator;
		this.nestedExpressions = Arrays.asList(nestedExpressions);
	}

	@Override
	public Q translateParameter(final P sourceParameter, final Symbol<T> sourceSymbol, final Symbol<U> targetSymbol) {
		P parameter = sourceParameter;

		ListIterator<ParameterFunction<T, P>> i = nestedExpressions.listIterator(nestedExpressions.size());
		while (i.hasPrevious()) {
			ParameterFunction<T, P> function = i.previous();
			parameter = function.passParameter(parameter, sourceSymbol);
		}

		return translator.translateParameter(parameter, sourceSymbol, targetSymbol);
	}
}
