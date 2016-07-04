package com.zettelnet.earley.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

// ALWAYS CONCRETE!
public class NonTerminalTranslatedSyntaxTreeVariant<T, P extends Parameter, U, Q extends Parameter> implements SyntaxTreeVariant<U, Q> {

	private final Translator<T, P, U, Q> translator;
	
	private final SyntaxTreeVariant<T, P> sourceVariant;
	private final TranslationTreeVariant<T, P, U, Q> translation;

	private final Q parameter;

	public NonTerminalTranslatedSyntaxTreeVariant(final Translator<T, P, U, Q> translator, final SyntaxTreeVariant<T, P> sourceVariant, final TranslationTreeVariant<T, P, U, Q> translation, final Q parameter) {
		this.translator = translator;
		
		this.sourceVariant = sourceVariant;
		this.translation = translation;

		this.parameter = parameter;
	}

	@Override
	public Symbol<U> getRootSymbol() {
		return translation.getRootSymbol();
	}

	@Override
	public boolean isTerminal() {
		return translation.isTerminal();
	}

	@Override
	public Production<U, Q> getProduction() {
		return null;
	}

	@Override
	public Q getParameter() {
		return parameter;
	}

	@Override
	public U getToken() {
		return null;
	}

	@Override
	public List<SyntaxTree<U, Q>> getChildren() {
		if (!isTerminal()) {
			List<TranslationTree<T, P, U, Q>> translationChildren = translation.getChildren();
			List<SyntaxTree<U, Q>> children = new ArrayList<>(translationChildren.size());

			for (TranslationTree<T, P, U, Q> translationChild : translationChildren) {
				new TranslatedVariantSyntaxTree<>(translator, translationChild, sourceVariant);
			}
			return children;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[var ");
		str.append(getRootSymbol());
		str.append(" ");
		for (SyntaxTree<U, Q> child : getChildren()) {
			str.append(child);
			str.append(" ");
		}
		str.append("]");
		return str.toString();
	}
}
