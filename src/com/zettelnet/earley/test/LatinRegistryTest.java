package com.zettelnet.earley.test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.token.TokenSetPrinter;
import com.zettelnet.earley.token.Tokenizer;
import com.zettelnet.earley.token.WhitespaceTokenizer;
import com.zettelnet.latin.grammar.LatinGrammar;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.token.LatinTokenPrinter;
import com.zettelnet.latin.token.Token;

public class LatinRegistryTest {

	public static void main(String[] args) throws IOException {
		System.in.read();
		
		Grammar<Token, FormParameter> grammar = LatinGrammar.makeGrammar();
		Tokenizer<Token> tokenizer = new WhitespaceTokenizer<>(LatinRegistry.INSTANCE);

		List<Token> tokens = tokenizer.tokenize("servus cantat dominum ridere");

		
		GrammarParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());
		ParseResult<Token, FormParameter> result = parser.parse(tokens);

		new ChartSetPrinter<>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));

		 System.out.println(result.getSyntaxTree());

		new TokenSetPrinter<>(LatinTokenPrinter.INSTANCE, tokens).print(new PrintStream("tokens.html"));

		JufoHelper.present(result, tokens);
	}
}
