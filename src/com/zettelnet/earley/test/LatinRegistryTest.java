package com.zettelnet.earley.test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.print.ChartSetPrinter;
import com.zettelnet.earley.print.TokenSetPrinter;
import com.zettelnet.earley.print.TranslationPrinter;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.token.TokenFactory;
import com.zettelnet.earley.token.Tokenizer;
import com.zettelnet.earley.token.WhitespaceTokenizer;
import com.zettelnet.earley.translate.SimpleTranslator;
import com.zettelnet.earley.translate.TranslationSet;
import com.zettelnet.earley.translate.Translator;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTrees;
import com.zettelnet.earley.tree.TreeViews;
import com.zettelnet.german.form.GermanDeterminerType;
import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.simple.SimpleGermanArticle;
import com.zettelnet.german.token.GermanDetermination;
import com.zettelnet.german.token.GermanToken;
import com.zettelnet.latin.grammar.LatinGrammar;
import com.zettelnet.latin.lemma.property.Meaning;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.token.LatinTokenPrinter;
import com.zettelnet.latin.token.Token;

public class LatinRegistryTest {

	public static void main(String[] args) throws IOException {
		// final PrintStream out = new PrintStream(new
		// FileOutputStream("debug.txt"));
		final PrintStream out = System.out;

		Grammar<Token, FormParameter> grammar = LatinGrammar.makeGrammar();
		Tokenizer<Token> tokenizer = new WhitespaceTokenizer<>(LatinRegistry.INSTANCE);

		String input = "servus cantat carmen pulchrum";

		out.printf("(S) Processing \"%s\" %n", input);

		List<Token> tokens = tokenizer.tokenize(input);

		out.println("(1) Tokenized:");
		out.println(tokens);

		new TokenSetPrinter<>(LatinTokenPrinter.INSTANCE, tokens).print(new PrintStream("tokens.html"));

		GrammarParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());
		ParseResult<Token, FormParameter> result = parser.parse(tokens);

		out.println("(2) Parsed:");
		out.println(result.getSyntaxTree());

		new ChartSetPrinter<>(result.getCharts(), tokens).print(new PrintStream("parse.html"));

		out.println("(X) Best parse match:");
		out.println(SyntaxTrees.getTreeView(result.getSyntaxTree(), TreeViews.bestProbability(), SyntaxTrees.INDENTED));
		out.println(SyntaxTrees.getTreeView(result.getSyntaxTree(), TreeViews.bestProbability(), SyntaxTrees.COMPACT_TREE));
		out.println(SyntaxTrees.toString(result.getSyntaxTree(), SyntaxTrees.COMPACT_TREE));

		TranslationSet<Token, FormParameter, GermanToken, FormParameter> germanTranslations = LatinGrammar.makeGermanTranslations();
		TokenFactory<GermanToken, FormParameter> germanTokenFactory = new TokenFactory<GermanToken, FormParameter>() {
			@Override
			public Collection<GermanToken> makeToken(Terminal<GermanToken> terminal, FormParameter parameter) {
				Set<Meaning> meanings = parameter.getProperty(Meaning.TYPE);
				GermanForm form = GermanForm.fromParameter(parameter);

				if (meanings.isEmpty() || form == null) {
					if (terminal.toString().equals("Article")) {
						try {
							return Arrays.asList(new GermanToken(SimpleGermanArticle.DEFINITE_ARTICLE.getForm(GermanForm.fromParameter(parameter)).iterator().next(), new GermanDetermination(null, parameter.toForm())));
						} catch (Throwable e) {
							return Arrays.asList(new GermanToken("DE_WARNING_{" + terminal + ":" + parameter + "}", new GermanDetermination(null, parameter.toForm())));
						}
					}
					return Arrays.asList(new GermanToken("DE_{" + terminal + ":" + parameter + "}", new GermanDetermination(null, parameter.toForm())));
				} else {
					Collection<GermanToken> tokens = new HashSet<>();
					for (Meaning meaning : meanings) {
						if (!form.hasProperty(GermanDeterminerType.TYPE)) {
							form = form.derive(GermanDeterminerType.DefiniteArticle);
						}
						
						try {
							for (GermanLemma translation : LatinRegistry.getTranslation(meaning.getLemma())) {
								if (translation.getForm(form).isEmpty()) {
									return Arrays.asList(new GermanToken("DE_WARNING_{" + terminal + ":" + parameter + "}", new GermanDetermination(null, form)));
								}
								for (String value : translation.getForm(form)) {
									tokens.add(new GermanToken(value, new GermanDetermination(translation, form)));
								}
							}
						} catch (Throwable e) {
							tokens.add(new GermanToken("DE_WARNING_{" + terminal + ":" + parameter + "}", new GermanDetermination(null, form)));
						}
					}
					return tokens;
				}
			}
		};

		Translator<Token, FormParameter, GermanToken, FormParameter> germanTranslator = new SimpleTranslator<>(grammar, germanTokenFactory, germanTranslations);

		SyntaxTree<GermanToken, FormParameter> translated = germanTranslator.translate(result.getSyntaxTree());

		new TranslationPrinter<>(result.getSyntaxTree(), germanTranslator).print(new PrintStream("translate.html"));

		out.println("(3) Translated:");
		out.println(translated);

		out.println("(X) Best translation match:");
		out.println(SyntaxTrees.getTreeView(translated, TreeViews.bestProbability(), SyntaxTrees.INDENTED));
		out.println(SyntaxTrees.getTreeView(translated, TreeViews.bestProbability(), SyntaxTrees.COMPACT_TREE));
		out.println(SyntaxTrees.getTreeView(translated, TreeViews.bestProbability(), SyntaxTrees.DETAILED_TREE));

		out.println("(4) Traversed:");
		out.println(SyntaxTrees.traverse(translated, TreeViews.bestProbability()));

		JufoHelper.present(result.getSyntaxTree());

		out.close();
	}
}
