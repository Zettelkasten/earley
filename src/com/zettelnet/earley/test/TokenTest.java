package com.zettelnet.earley.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenTest {

	public static void main(String[] args) {
		List<String> tokens = Arrays.asList("A", "B", "C", "D");

		int set = 0;

		printStateSet(set);
		System.out.println(" -> add A");
		System.out.println("        unused: " + unusedTokens(tokens, set));
		set = nextStates(tokens, set, "A");
		printStateSet(set);
		System.out.println("        unused: " + unusedTokens(tokens, set));
		System.out.println(" -> add C");
		set = nextStates(tokens, set, "C");
		printStateSet(set);
		System.out.println("        unused: " + unusedTokens(tokens, set));
		System.out.println(" -> add D");
		set = nextStates(tokens, set, "D");
		printStateSet(set);
		System.out.println("        unused: " + unusedTokens(tokens, set));

		/*
		 * 0001 means: all tokens unused, except for the last one
		 */
	}

	private static <T> int nextStates(List<T> tokens, int statesPosition, T resolved) {
		int nextPosition = statesPosition | (1 << tokens.indexOf(resolved));
		return nextPosition;
	}

	private static <T> Set<T> unusedTokens(List<T> tokens, int statesPosition) {
		Set<T> unused = new HashSet<>(tokens.size());
		int index = 0;
		int position = statesPosition ^ ((1 << tokens.size()) - 1);
		while (position > 0) {
			if ((position & 1) == 1) {
				unused.add(tokens.get(index));
			}
			position >>= 1;
			index++;
		}
		return unused;
	}

	private static void printStateSet(int position) {
		System.out.println(String.format("%7s %s", "S(" + position + ")", String.format("%16s", Integer.toBinaryString(position)).replace(' ', '0')));
	}

}
