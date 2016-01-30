package com.zettelnet.earley.input;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DynamicInputPosition<T> implements InputPosition<T>, Comparable<DynamicInputPosition<T>> {

	private final List<T> tokens;

	private final BitSet usedTokens;

	public DynamicInputPosition(final List<T> tokens, final BitSet usedTokens) {
		this.tokens = tokens;
		this.usedTokens = usedTokens;
	}

	private int getTokenIndex(T token) {
		return tokens.indexOf(token);
	}

	@Override
	public int compareTo(DynamicInputPosition<T> o) {
		return compare(this.usedTokens, o.usedTokens);
	}

	private static int compare(BitSet lhs, BitSet rhs) {
		if (lhs.equals(rhs)) {
			return 0;
		}
		BitSet xor = (BitSet) lhs.clone();
		xor.xor(rhs);
		int firstDifferent = xor.length() - 1;
		return rhs.get(firstDifferent) ? -1 : 1;
	}

	@Override
	public boolean isClean() {
		return usedTokens.cardinality() == 0;
	}

	@Override
	public boolean isComplete() {
		return usedTokens.cardinality() == tokens.size();
	}
	
	@Override
	public boolean isTokenAvailable(T token) {
		return !usedTokens.get(tokens.indexOf(token));
	}

	@Override
	public Collection<T> getAvailableTokens() {
		if (isComplete()) {
			return Collections.emptySet();
		} else {
			List<T> availableTokens = new ArrayList<>(tokens.size() - usedTokens.cardinality());
			for (int tokenIndex = 0; tokenIndex < tokens.size(); tokenIndex++) {
				if (!usedTokens.get(tokenIndex)) {
					availableTokens.add(tokens.get(tokenIndex));
				}
			}
			return availableTokens;
		}
	}

	@Override
	public InputPosition<T> nextPosition(T usedToken) {
		BitSet newSet = (BitSet) usedTokens.clone();
		newSet.set(getTokenIndex(usedToken));
		return new DynamicInputPosition<T>(tokens, newSet);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(tokens.size());
		for (int i = 0; i < tokens.size(); i++) {
			str.append(usedTokens.get(i) ? '1' : '0');
		}
		return str.toString();
	}
}
