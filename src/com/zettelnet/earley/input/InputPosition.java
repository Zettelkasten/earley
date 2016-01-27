package com.zettelnet.earley.input;

public interface InputPosition<T> {

	/**
	 * Returns <code>true</code>, if this InputPosition is the first
	 * InputPosition representing no Token used.
	 * 
	 * @return whether this InputPosition represents no Tokens used
	 */
	boolean isClean();

	boolean isComplete();

	Iterable<T> getAvailableTokens();

	InputPosition<T> nextPosition(T usedToken);

}
