package com.zettelnet.earley.translate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.NoSuchSyntaxTreeException;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;
import com.zettelnet.earley.tree.TerminalSyntaxTreeVariant;

public class TranslatedSyntaxTree<T, P extends Parameter, U, Q extends Parameter> implements SyntaxTree<U, Q> {

	private final Translator<T, P, U, Q> translator;
	private final SyntaxTree<T, P> sourceTree;

	public TranslatedSyntaxTree(final Translator<T, P, U, Q> translator, final SyntaxTree<T, P> sourceTree) {
		this.translator = translator;
		this.sourceTree = sourceTree;
	}

	@Override
	public Iterable<SyntaxTreeVariant<U, Q>> getVariants() {
		Set<SyntaxTreeVariant<U, Q>> variants = new HashSet<>();
		for (SyntaxTreeVariant<T, P> sourceVariant : sourceTree.getVariants()) {
			P sourceParameter = sourceVariant.getParameter();
			for (TranslationTree<T, P, U, Q> translation : translator.getTranslationTrees(sourceVariant)) {
				for (TranslationTreeVariant<T, P, U, Q> translationVariant : translation.getVariants()) {
					if (translationVariant.isAbstract()) {
						variants.addAll(translator.translate(translationVariant.getAbstractReference().getSourceTree()).getVariantsSet());
					} else {
						Q parameter = translationVariant.getParameterTranslator().translateParameter(sourceParameter);
						if (translationVariant.isTerminal()) {
							Terminal<U> symbol = (Terminal<U>) translationVariant.getRootSymbol();
							for (U token : translator.makeToken(symbol, parameter)) {
								variants.add(new TerminalSyntaxTreeVariant<>(symbol, parameter, token));
							}
						} else {
							variants.add(new NonTerminalTranslatedSyntaxTreeVariant<>(translator, translationVariant, parameter));
						}
					}
				}
			}
		}
		return variants;

	}

	@Override
	public SyntaxTreeVariant<U, Q> getVariant(Iterator<Integer> variantDirections) throws NoSuchSyntaxTreeException {
		throw new NoSuchSyntaxTreeException();
	}

}
