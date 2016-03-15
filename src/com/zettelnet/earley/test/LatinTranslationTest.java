package com.zettelnet.earley.test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.process.ProcessableGrammar;
import com.zettelnet.earley.test.latin.Determination;
import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.earley.test.latin.TranslatableLatinGrammar;
import com.zettelnet.earley.token.Tokenizer;
import com.zettelnet.earley.token.WhitespaceTokenizer;

public class LatinTranslationTest {

	public static void main(String[] args) {
		ProcessableGrammar<Token, FormParameter, String> grammar = TranslatableLatinGrammar.makeGrammar();
		Tokenizer<Token> tokenizer = new WhitespaceTokenizer<>(LatinRegistry.INSTANCE);
		EarleyParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());
		
		String raw = "servus dicet dominum cantare";

		List<Token> tokens = tokenizer.tokenize(raw);
		
		for (Token token : tokens) {
			System.out.println(token.getContent() + ": ");
			for (Determination determination : token.getDeterminations()) {
				System.out.println("\t" + determination + "; " + determination.getLemmaType());
			}
		}
		
		EarleyParseResult<Token, FormParameter> result = parser.parse(tokens);

		try {
			new ChartSetPrinter<>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));
		} catch (IOException e) {
		}

		System.out.println(result.getSyntaxTree());
		System.out.println(grammar.process(result.getSyntaxTree()));

		JufoHelper.present(result, tokens);
	}
}
