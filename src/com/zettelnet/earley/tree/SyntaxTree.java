package com.zettelnet.earley.tree;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;

public interface SyntaxTree<T, P extends Parameter> {

	Symbol<T> getRootSymbol();

	boolean isTerminal();
	
	T getToken();

	Set<Production<T, P>> getProductions();

	Iterable<SyntaxTreeVariant<T, P>> getVariants();

	default Set<SyntaxTreeVariant<T, P>> getVariantsSet() {
		Iterable<SyntaxTreeVariant<T, P>> iterable = getVariants();
		if (iterable instanceof Set) {
			return (Set<SyntaxTreeVariant<T, P>>) iterable;
		} else {
			Set<SyntaxTreeVariant<T, P>> set = new HashSet<>();
			for (SyntaxTreeVariant<T, P> element : getVariants()) {
				set.add(element);
			}
			return set;
		}
	}

	default Set<SyntaxTreeVariant<T, P>> getVariantsSet(int maxAmount) {
		Set<SyntaxTreeVariant<T, P>> set = new HashSet<>();
		for (SyntaxTreeVariant<T, P> element : getVariants()) {
			if (--maxAmount < 0) {
				break;
			}
			set.add(element);
		}
		return set;
	}

	Iterable<SyntaxTreeVariant<T, P>> getVariants(Production<T, P> production);

	SyntaxTreeVariant<T, P> getVariant(Iterator<Integer> variantDirections) throws NoSuchSyntaxTreeException;
}
