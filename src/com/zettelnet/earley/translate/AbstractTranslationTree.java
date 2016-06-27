package com.zettelnet.earley.translate;

import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public class AbstractTranslationTree<T, P extends Parameter, U, Q extends Parameter> implements TranslationTreeVariant<T, P, U, Q> {

	private final AbstractReference<T, P> reference;

	public AbstractTranslationTree(final AbstractReference<T, P> reference) {
		this.reference = reference;
	}

	@Override
	public Symbol<U> getRootSymbol() {
		return null;
	}

	@Override
	public boolean isTerminal() {
		return false; // actually we don't know this
	}

	@Override
	public boolean isAbstract() {
		return true;
	}

	@Override
	public AbstractReference<T, P> getAbstractReference() {
		return reference;
	}

	@Override
	public ParameterTranslator<P, Q> getParameterTranslator() {
		return null;
	}

	@Override
	public List<TranslationTreeVariant<T, P, U, Q>> getChildren() {
		return null;
	}

}
