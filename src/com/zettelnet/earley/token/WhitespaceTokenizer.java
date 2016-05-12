package com.zettelnet.earley.token;

import java.util.ArrayList;
import java.util.List;

public class WhitespaceTokenizer<T> implements Tokenizer<T> {

	private final TokenScanner<T> factory;

	public WhitespaceTokenizer(final TokenScanner<T> tokenFactory) {
		this.factory = tokenFactory;
	}

	@Override
	public List<T> tokenize(String raw) {
		String[] split = raw.split("\\s+");
		List<T> tokens = new ArrayList<>();
		for (String content : split) {
			tokens.add(factory.makeToken(content));
		}
		return tokens;
	}

}
