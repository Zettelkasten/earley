package com.zettelnet.earley.test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.token.TokenFactory;
import com.zettelnet.earley.token.TokenSetPrinter;
import com.zettelnet.earley.token.Tokenizer;
import com.zettelnet.earley.token.WhitespaceTokenizer;
import com.zettelnet.earley.translate.SimpleTranslator;
import com.zettelnet.earley.translate.TranslationPrinter;
import com.zettelnet.earley.translate.TranslationSet;
import com.zettelnet.earley.translate.Translator;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTrees;
import com.zettelnet.earley.tree.TreeViews;
import com.zettelnet.german.token.GermanDetermination;
import com.zettelnet.german.token.GermanToken;
import com.zettelnet.latin.grammar.LatinGrammar;
import com.zettelnet.latin.lemma.property.Meaning;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.token.LatinTokenPrinter;
import com.zettelnet.latin.token.Token;

public class LatinRegistryTest {

	public static void main(String[] args) throws IOException {
//		final PrintStream out = new PrintStream(new FileOutputStream("debug.txt"));
		final PrintStream out = System.out;
		
		Grammar<Token, FormParameter> grammar = LatinGrammar.makeGrammar();
		Tokenizer<Token> tokenizer = new WhitespaceTokenizer<>(LatinRegistry.INSTANCE);

		String input = "servus cantat dominum ridere";

		out.printf("(S) Processing \"%s\" %n", input);

		List<Token> tokens = tokenizer.tokenize(input);

		out.println("(1) Tokenized:");
		out.println(tokens);

		new TokenSetPrinter<>(LatinTokenPrinter.INSTANCE, tokens).print(new PrintStream("tokens.html"));

		GrammarParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());
		ParseResult<Token, FormParameter> result = parser.parse(tokens);

		out.println("(2) Parsed:");
		out.println(result.getSyntaxTree());

		out.println("(X) Best parse match:");
		out.println(SyntaxTrees.getTreeView(result.getSyntaxTree(), TreeViews.bestProbability()));

		new ChartSetPrinter<>(result.getCharts(), tokens).print(new PrintStream("parse.html"));

		TranslationSet<Token, FormParameter, GermanToken, FormParameter> germanTranslations = LatinGrammar.makeGermanTranslations();
		TokenFactory<GermanToken, FormParameter> germanTokenFactory = new TokenFactory<GermanToken, FormParameter>() {
			@Override
			public Collection<GermanToken> makeToken(Terminal<GermanToken> terminal, FormParameter parameter) {
				Set<Meaning> meanings = parameter.getProperty(Meaning.TYPE);

				if (meanings.isEmpty()) {
					return Arrays.asList(new GermanToken("DE_{" + terminal + ":" + parameter + "}", new GermanDetermination(null, parameter.toForm())));
				} else {
					Collection<GermanToken> tokens = new HashSet<>();
					for (Meaning meaning : meanings) {
						String content = LatinRegistry.getTranslation(meaning.getLemma());
						tokens.add(new GermanToken(content, new GermanDetermination(null, parameter.toForm())));
					}
					return tokens;
				}
			}
		};
		Translator<Token, FormParameter, GermanToken, FormParameter> germanTranslator = new SimpleTranslator<>(grammar, germanTokenFactory, germanTranslations);

		SyntaxTree<GermanToken, FormParameter> translated = germanTranslator.translate(result.getSyntaxTree());

		out.println("(3) Translated:");
		out.println(translated);

		out.println("(4) Traversed:");
		out.println(SyntaxTrees.traverse(translated));

		new TranslationPrinter<>(result.getSyntaxTree(), germanTranslator).print(new PrintStream("translate.html"));

		JufoHelper.present(result.getSyntaxTree());

		out.close();
	}
}
