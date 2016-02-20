package com.zettelnet.earley.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.tree.binary.BinarySyntaxTree;
import com.zettelnet.earley.tree.binary.BinarySyntaxTreeVariant;

public class UnbinaryNonTerminalSyntaxTree<T, P extends Parameter> implements SyntaxTree<T, P> {

	private final NonTerminal<T> symbol;
	private final BinarySyntaxTree<T, P> node;

	public UnbinaryNonTerminalSyntaxTree(final NonTerminal<T> symbol, final BinarySyntaxTree<T, P> node) {
		this.symbol = symbol;
		this.node = node;
	}

	@Override
	public NonTerminal<T> getRootSymbol() {
		return symbol;
	}

	@Override
	public boolean isTerminal() {
		return false;
	}
	
	@Override
	public T getToken() {
		return null;
	}
	
	@Override
	public Set<Production<T, P>> getProductions() {
		Set<Production<T, P>> set = new HashSet<>();
		for (BinarySyntaxTreeVariant<T, P> variant : node.getVariants()) {
			set.add(variant.getChildProduction());
		}
		return set;
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants() {
		return getVariantsSet();
	}

	@Override
	public Iterable<SyntaxTreeVariant<T, P>> getVariants(Production<T, P> production) {
		// TODO Auto-generated method stub
		return null;
	}

	// Implemented using Depth-First-Search
	@Override
	public Set<SyntaxTreeVariant<T, P>> getVariantsSet() {
		final Set<SyntaxTreeVariant<T, P>> output = new HashSet<>();

		final Deque<Iterator<BinarySyntaxTreeVariant<T, P>>> iterators = new LinkedList<>();
		final Deque<SyntaxTree<T, P>> list = new LinkedList<>();
		Production<T, P> production = null;

		// seed
		iterators.addFirst(this.node.getVariants().iterator());

		while (!iterators.isEmpty()) {
			Iterator<BinarySyntaxTreeVariant<T, P>> iterator = iterators.getFirst();

			if (iterator.hasNext()) {
				BinarySyntaxTreeVariant<T, P> variant = iterator.next();
				list.addFirst(toNaturalChildTree(variant));

				// get production -> TODO NOT WORKING PROPERLY
				if (iterators.size() == 1) {
					production = variant.getChildProduction();
				}

				BinarySyntaxTree<T, P> preNode = variant.getPreNode();
				if (preNode == null) {
					output.add(new SimpleSyntaxTreeVariant<>(production, createStrippedCopy(list)));
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

	private final Map<BinarySyntaxTreeVariant<T, P>, SyntaxTree<T, P>> childTreeCache = new WeakHashMap<>();

	private SyntaxTree<T, P> toNaturalChildTree(BinarySyntaxTreeVariant<T, P> variant) {
//		if (childTreeCache.containsKey(variant)) {
//			return childTreeCache.get(variant);
//		} else {
			SyntaxTree<T, P> returnValue;

			Symbol<T> symbol = variant.getSymbol();
			BinarySyntaxTree<T, P> childNode = variant.getChildNode();

			if (symbol == null) {
				returnValue = null;
			} else {
				if (childNode != null) {
					// non-terminal
					returnValue = new UnbinaryNonTerminalSyntaxTree<>((NonTerminal<T>) symbol, childNode);
				} else {
					// terminal
					returnValue = new UnbinaryTerminalSyntaxTree<>((Terminal<T>) symbol, variant.getToken());
				}
			}

//			childTreeCache.put(variant, returnValue);
			return returnValue;
//		}
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
				production = binaryVariant.getChildProduction();
			}

			Symbol<T> symbol = binaryVariant.getSymbol();
			BinarySyntaxTree<T, P> childNode = binaryVariant.getChildNode();

			if (symbol != null) {
				SyntaxTree<T, P> child;
				if (childNode != null) {
					// non-terminal
					child = new UnbinaryNonTerminalSyntaxTree<>((NonTerminal<T>) symbol, childNode);
				} else {
					// terminal
					child = new UnbinaryTerminalSyntaxTree<>((Terminal<T>) symbol, binaryVariant.getToken());
				}
				children.add(0, child);
			}

			binaryNode = binaryVariant.getPreNode();
		} while (binaryNode != null);

		return new SimpleSyntaxTreeVariant<>(production, children);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		str.append(symbol);
		Iterable<SyntaxTreeVariant<T, P>> variants = getVariants();
		for (SyntaxTreeVariant<T, P> variant : variants) {
			str.append(" ");
			str.append(variant);
		}
		str.append("]");
		return str.toString();
	}
}
