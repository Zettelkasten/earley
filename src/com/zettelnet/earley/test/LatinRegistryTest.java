package com.zettelnet.earley.test;

import java.util.List;

import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.earley.test.latin.LatinGrammar;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.earley.token.Tokenizer;
import com.zettelnet.earley.token.WhitespaceTokenizer;

public class LatinRegistryTest {

	public static void main(String[] args) {
		Grammar<Token, FormParameter> grammar = LatinGrammar.makeGrammar();
		Tokenizer<Token> tokenizer = new WhitespaceTokenizer<>(LatinRegistry.INSTANCE);

		List<Token> tokens = tokenizer.tokenize("cantaveris");

		GrammarParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());
		ParseResult<Token, FormParameter> result = parser.parse(tokens);

		System.out.println(result.getSyntaxTree());
	}
}
