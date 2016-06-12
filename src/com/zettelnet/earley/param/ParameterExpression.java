package com.zettelnet.earley.param;

import java.util.Collection;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.State;
import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.Terminal;

/**
 * Represents a set of functions that handles how {@link Parameter}s behave when
 * the operations <i>prediction</i>, <i>scanning</i> and <i>completion</i> are
 * performed. For each individual operation, a set of context parameters is
 * provided.
 * <p>
 * Implementations of parameter expressions should return a collection of
 * possible parameters. Each parameter returned should be independent from the
 * input parameters.
 * 
 * @author Zettelkasten
 *
 * @param <T>
 *            The type of Tokens to be used
 * @param
 * 			<P>
 *            The type of Parameter to be handled
 */
public interface ParameterExpression<T, P extends Parameter> {

	/**
	 * Determines which parameter <code>&pi;<sub>*</sub></code> is used for
	 * newly created {@link State}s
	 * <code>{s = (Y(&pi;<sub>*</sub>) &rarr; &gamma;)}</code> when a state
	 * <code>s<sub>0</sub> = (X(&pi;<sub>0</sub>) &rarr; &alpha; Y(<strong>expr</strong>) &beta;)</code>
	 * is predicted using the {@link Production}
	 * <code>Y(&pi;<sub>n</sub>) &rarr; &gamma;</code>.
	 * 
	 * @param parentParameter
	 *            The parameter <code>&pi;<sub>0</sub></code>
	 * @param childParameter
	 *            The parameter <code>&pi;<sub>n</sub></code>
	 * @param childSymbol
	 *            The symbol <code>Y</code> that is predicted
	 * @return A set of possible parameters <code>&pi;<sub>*</sub></code>
	 */
	Collection<P> predict(P parentParameter, P childParameter, NonTerminal<T> childSymbol);

	Collection<P> scan(P parentParameter, NonTerminal<T> parentSymbol, T token, Terminal<T> terminal);

	Collection<P> complete(P parentParameter, NonTerminal<T> parentSymbol, P childParameter);
}
