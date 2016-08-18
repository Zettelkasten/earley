package com.zettelnet.earley.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;

public interface SyntaxTree<T, P extends Parameter> {

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
	
	default Iterable<SyntaxTreeVariant<T, P>> getVariantsByProbability() {
		Collection<SyntaxTreeVariant<T, P>> collection = new PriorityQueue<>(SyntaxTrees.PROBABILITY_COMPARATOR);
		collection.addAll(getVariantsSet());
		return collection;
	}

	SyntaxTreeVariant<T, P> getVariant(Iterator<Integer> variantDirections) throws NoSuchSyntaxTreeException;
}
