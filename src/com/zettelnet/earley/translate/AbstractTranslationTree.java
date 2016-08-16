package com.zettelnet.earley.translate;

import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public class AbstractTranslationTree<T, P extends Parameter, U, Q extends Parameter> implements TranslationTreeVariant<T, P, U, Q> {

	private final AbstractReference<T, P> reference;
	private final double probability;

	public AbstractTranslationTree(final AbstractReference<T, P> reference, final double probability) {
		this.reference = reference;
		this.probability = probability;
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
	public ParameterTranslator<T, P, U, Q> getParameterTranslator() {
		return null;
	}

	@Override
	public List<TranslationTree<T, P, U, Q>> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public double getLocalProbability() {
		return probability;
	}
}
