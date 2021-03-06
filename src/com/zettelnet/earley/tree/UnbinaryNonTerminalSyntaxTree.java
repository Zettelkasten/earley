package com.zettelnet.earley.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.tree.binary.BinarySyntaxTree;
import com.zettelnet.earley.tree.binary.BinarySyntaxTreeVariant;

public class UnbinaryNonTerminalSyntaxTree<T, P extends Parameter> implements SyntaxTree<T, P> {

	private final BinarySyntaxTree<T, P> node;

	public UnbinaryNonTerminalSyntaxTree(final BinarySyntaxTree<T, P> node) {
		this.node = node;
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants() {
		return getVariantsSet();
	}

	// Implemented using Depth-First-Search
	@Override
	public Set<SyntaxTreeVariant<T, P>> getVariantsSet() {
		final Set<SyntaxTreeVariant<T, P>> output = new HashSet<>();

		final Deque<Iterator<BinarySyntaxTreeVariant<T, P>>> iterators = new LinkedList<>();
		final Deque<SyntaxTree<T, P>> list = new LinkedList<>();

		BinarySyntaxTreeVariant<T, P> firstVariant = null;

		// seed
		iterators.addFirst(this.node.getVariants().iterator());

		while (!iterators.isEmpty()) {
			Iterator<BinarySyntaxTreeVariant<T, P>> iterator = iterators.getFirst();

			if (iterator.hasNext()) {
				BinarySyntaxTreeVariant<T, P> variant = iterator.next();
				list.addFirst(toNaturalChildTree(variant.getChildNode()));

				if (variant.isFirst()) {
					firstVariant = variant;
				}

				BinarySyntaxTree<T, P> preNode = variant.getPreNode();
				if (preNode == null) {
					output.add(new NonTerminalSyntaxTreeVariant<>((NonTerminal<T>) node.getRootSymbol(), firstVariant.getProduction(), firstVariant.getParameter(), createStrippedCopy(list), variant.getProbability()));
					list.removeFirst();
				} else {
					iterators.addFirst(preNode.getVariants().iterator());
				}
			} else {
				iterators.pollFirst();
				list.pollFirst();
			}
		}

		return output;
	}

	private SyntaxTree<T, P> toNaturalChildTree(BinarySyntaxTree<T, P> childNode) {
		if (childNode == null) {
			return null;
		} else {
			if (childNode.isTerminal()) {
				// terminal
				return new UnbinaryTerminalSyntaxTree<>(childNode);
			} else {
				// non-terminal
				return new UnbinaryNonTerminalSyntaxTree<>(childNode);
			}
		}
	}

	private List<SyntaxTree<T, P>> createStrippedCopy(Collection<SyntaxTree<T, P>> source) {
		List<SyntaxTree<T, P>> list = new ArrayList<>(source.size());
		for (SyntaxTree<T, P> element : source) {
			if (element != null) {
				list.add(element);
			}
		}
		return list;
	}

	@Override
	public SyntaxTreeVariant<T, P> getVariant(Iterator<Integer> variantDirections) throws NoSuchSyntaxTreeException {
		final List<SyntaxTree<T, P>> children = new ArrayList<>();

		Production<T, P> production = null;
		P parameter = null;
		double probability = -1;

		BinarySyntaxTree<T, P> binaryNode = node;
		do {
			int variantId = 0;
			if (variantDirections.hasNext()) {
				variantId = variantDirections.next();
			}
			List<BinarySyntaxTreeVariant<T, P>> variants = binaryNode.getVariants();
			if (variantId > variants.size()) {
				throw new NoSuchSyntaxTreeException();
			}
			BinarySyntaxTreeVariant<T, P> binaryVariant = binaryNode.getVariants().get(variantId);

			if (production == null) {
				production = binaryVariant.getProduction();
			}
			if (parameter == null) {
				parameter = binaryVariant.getParameter();
			}
			if (probability == -1) {
				probability = binaryVariant.getProbability();
			}

			SyntaxTree<T, P> child = toNaturalChildTree(binaryVariant.getChildNode());
			children.add(0, child);

			binaryNode = binaryVariant.getPreNode();
		} while (binaryNode != null);

		assert probability >= 0 && probability <= 1;
		return new NonTerminalSyntaxTreeVariant<>((NonTerminal<T>) node.getRootSymbol(), production, parameter, createStrippedCopy(children), probability);
	}

	@Override
	public String toString() {
		return SyntaxTrees.toString(this);
	}
}
