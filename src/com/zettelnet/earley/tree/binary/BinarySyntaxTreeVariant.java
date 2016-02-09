package com.zettelnet.earley.tree.binary;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;

/**
 * Represents a node of a <strong>binary syntax tree</strong>.
 *
 * There are four types of nodes to be distinguished:
 * <ol>
 * <li><strong>Initial nodes</strong>: Resolve to exactly one child node, and
 * the {@link Production} and {@link NonTerminal} type associated with the child
 * node. Only used as root.</li>
 * <li><strong>Non terminal nodes</strong>: Resolve to a pre node, one child
 * node and the {@link Production} and {@link NonTerminal} type associated with
 * the child node.</li>
 * <li><strong>Terminal nodes</strong>: Resolve to a pre node and a
 * {@link Terminal}.
 * <li><strong>Epsilon nodes</strong>: Resolve to nothing.
 * </ol>
 *
 * When traversed inorder, a natural syntax tree is generated.
 * 
 * @author Zettelkasten
 *
 * @param <T>
 *            The token type
 * @param
 * 			<P>
 *            The parameter type
 */
public interface BinarySyntaxTreeVariant<T, P extends Parameter> {

	BinarySyntaxTree<T, P> getPreNode();

	Symbol<T> getSymbol();
	
	default boolean isTerminal() {
		return getChildNode() == null;
	}

	Production<T, P> getChildProduction();

	BinarySyntaxTree<T, P> getChildNode();
}