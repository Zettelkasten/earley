package com.zettelnet.earley.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LinearInputPosition<T> implements InputPosition<T>, Comparable<LinearInputPosition<T>> {

	private final List<T> tokens;

	private final int index;

	public LinearInputPosition(final List<T> tokens, final int position) {
		this.tokens = tokens;
		this.index = position;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public int compareTo(LinearInputPosition<T> o) {
		return Integer.compare(this.index, o.index);
	}

	@Override
	public boolean isClean() {
		return index == 0;
	}

	@Override
	public boolean isComplete() {
		return index == tokens.size();
	}

	@Override
	public boolean isTokenAvailable(T token) {
		return index == tokens.indexOf(token);
	}

	@Override
	public Collection<T> getAvailableTokens() {
		if (isComplete()) {
			return Collections.emptySet();
		} else {
			return Arrays.asList(tokens.get(index));
		}
	}

	@Override
	public Collection<T> getUsedTokens() {
		List<T> used = new ArrayList<>(this.index);
		for (int tokenIndex = 0; tokenIndex < this.index; tokenIndex++) {
			used.add(tokens.get(tokenIndex));
		}
		return used;
	}

	@Override
	public InputPosition<T> nextPosition(T usedToken) {
		return new LinearInputPosition<>(tokens, index + 1);
	}

	@Override
	public String toString() {
		return String.valueOf(index);
	}
}
