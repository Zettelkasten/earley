package com.zettelnet.earley.input;

import java.util.Collection;
import java.util.Map;

public interface InputPosition<T> {

	/**
	 * Returns <code>true</code>, if this InputPosition is the first
	 * InputPosition representing no Token used.
	 * 
	 * @return whether this InputPosition represents no Tokens used
	 */
	boolean isClean();

	boolean isComplete();

	boolean isTokenAvailable(T token);

	Map<InputPosition<T>, T> getAvailableTokens();

	/**
	 * Should not be used because of duplicate tokens being equal.
	 * 
	 * @param usedToken
	 * @return
	 */
	@Deprecated
	InputPosition<T> nextPosition(T usedToken);

	Collection<T> getUsedTokens();

}
