package com.zettelnet.earley.translate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.tree.NoSuchSyntaxTreeException;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;
import com.zettelnet.earley.tree.SyntaxTrees;

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
			for (TranslationTree<T, P, U, Q> translation : translator.getTranslationTrees(sourceVariant)) {
				TranslatedVariantSyntaxTree<T, P, U, Q> variantTree = new TranslatedVariantSyntaxTree<>(translator, translation, sourceVariant);
				variants.addAll(variantTree.getVariantsSet());
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
