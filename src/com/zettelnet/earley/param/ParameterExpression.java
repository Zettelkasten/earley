package com.zettelnet.earley.param;

import java.util.Collection;

import com.zettelnet.earley.symbol.Terminal;

public interface ParameterExpression<T, P extends Parameter> {

	Collection<P> predict(P parameter, P childParameter);
	
	/**
	 * Modifiziert den Parameter je nach gescanntem Token. Gibt
	 * <code>false</code> zurück, wenn das Token nicht gescannt werden kann.
	 * 
	 * @param parameter
	 * @param token
	 */
	Collection<P> scan(P parameter, T token, Terminal<T> terminal);

	/**
	 * Modifiziert den Parameter der Elternregel anhand einer Kindregel. Gibt
	 * <code>false</code> zurück, falls der Kindkonstituent nicht kompatibel
	 * ist.
	 * 
	 * @param parameter
	 * @param completeWith
	 * @return
	 */
	Collection<P> complete(P parameter, P childParameter);
}
