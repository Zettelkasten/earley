package com.zettelnet.earley.tree;

import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.State;
import com.zettelnet.earley.param.Parameter;

public class InitialStateBinarySyntaxTree<T, P extends Parameter> implements BinarySyntaxTree<T, P> {

	private final Grammar<T, P> grammar;
	private final Collection<State<T, P>> completeStates;
	
	public InitialStateBinarySyntaxTree(final Grammar<T, P> grammar, final Collection<State<T, P>> completeStates) {
		this.grammar = grammar;
		this.completeStates = completeStates;
	}
	
	@Override
	public Collection<BinarySyntaxTreeVariant<T, P>> getVariants() {
		Collection<BinarySyntaxTreeVariant<T, P>> variants = new ArrayList<>();
		for (State<T, P> state : completeStates) {
			variants.add(new InitialVariant<>(grammar, state));
		}
		return variants;
	}

//	@Override
//	public SyntaxTree<T, P> toNaturalTree() {
//		SimpleSyntaxTree<T, P> parent = new SimpleSyntaxTree<>(grammar.getStartSymbol());
//		
//		for (BinarySyntaxTreeVariant<T, P> variant : getVariants()) {
//			SimpleSyntaxTree.Variant<T, P> tree = new SimpleSyntaxTree.Variant<>(variant.getChildProduction());
//			
//			if (variant instanceof EpsilonVariant) {
//				// do nothing
//			} else if (variant instanceof NonTerminalVariant) {
//				// add terminal symbol
//				tree.addChild(new TerminalSyntaxTree<>((Terminal<T>) variant.getSymbol()));
//				// process pre node
//				addChild(tree, variant.getPreNode());
//			} else if (variant instanceof TerminalVariant | variant instanceof InitialVariant) {
//				// add non terminal symbol
//				SyntaxTree<T, P> child = new SimpleSyntaxTree<>(variant.getSymbol());
//				tree.addChild(child);
//				// process child node
//				addChild(child, variant.getChildNode());
//				// process pre node
//				addChild(tree, variant.getPreNode());
//			} else {
//				throw new AssertionError("Unknown variant type!");
//			}
//			
//			parent.addVariant(tree);
//		}
//	}
//	
//	public void addChild(SimpleSyntaxTree<T, P> parent, BinarySyntaxTree<T, P> binaryChild) {
//		for (BinarySyntaxTreeVariant<T, P> binaryVariant : binaryChild.getVariants()) {
//			
//			if (binaryVariant instanceof EpsilonVariant) {
//				// yey, we have to do nuthing
//			} else if (binaryVariant instanceof TerminalVariant) {
//				parent.add
//			}
//		}
//	}
}
