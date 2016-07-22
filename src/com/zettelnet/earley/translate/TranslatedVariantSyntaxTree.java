package com.zettelnet.earley.translate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.NoSuchSyntaxTreeException;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;
import com.zettelnet.earley.tree.SyntaxTrees;
import com.zettelnet.earley.tree.TerminalSyntaxTreeVariant;

public class TranslatedVariantSyntaxTree<T, P extends Parameter, U, Q extends Parameter> implements SyntaxTree<U, Q> {

	private final Translator<T, P, U, Q> translator;

	private final TranslationTree<T, P, U, Q> translation;
	private final SyntaxTreeVariant<T, P> sourceVariant;

	public TranslatedVariantSyntaxTree(final Translator<T, P, U, Q> translator, final TranslationTree<T, P, U, Q> translation, final SyntaxTreeVariant<T, P> sourceVariant) {
		this.translator = translator;
		
		this.translation = translation;
		this.sourceVariant = sourceVariant;
	}

	@Override
	public Iterable<SyntaxTreeVariant<U, Q>> getVariants() {
		Set<SyntaxTreeVariant<U, Q>> variants = new HashSet<>();
		Symbol<T> sourceSymbol = sourceVariant.getRootSymbol();
		P sourceParameter = sourceVariant.getParameter();
			for (TranslationTreeVariant<T, P, U, Q> translationVariant : translation.getVariants()) {
				if (translationVariant.isAbstract()) {
					variants.addAll(translator.translate(translationVariant.getAbstractReference().getSourceTree(sourceVariant)).getVariantsSet());
				} else {
					Q parameter = translationVariant.getParameterTranslator().translateParameter(sourceParameter, sourceSymbol, translationVariant.getRootSymbol());
					if (translationVariant.isTerminal()) {
						Terminal<U> symbol = (Terminal<U>) translationVariant.getRootSymbol();
						for (U token : translator.makeToken(symbol, parameter)) {
							variants.add(new TerminalSyntaxTreeVariant<>(symbol, parameter, token));
						}
					} else {
						variants.add(new TranslatedSyntaxTreeVariant<>(translator, sourceVariant, translationVariant, parameter));
					}
				}
			}
		return variants;
	}

	@Override
	public SyntaxTreeVariant<U, Q> getVariant(Iterator<Integer> variantDirections) throws NoSuchSyntaxTreeException {
		throw new NoSuchSyntaxTreeException();
	}

	@Override
	public String toString() {
		return SyntaxTrees.toString(this);
	}
}
