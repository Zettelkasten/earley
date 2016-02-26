package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParseResult;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.test.latin.Determination;
import com.zettelnet.earley.test.latin.DummyLemma;
import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.earley.test.latin.LatinGrammar;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.latin.Form;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Finiteness;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Valency;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.Lemma;

public class JufoExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {

		Grammar<Token, FormParameter> grammar = LatinGrammar.makeGrammar();

		EarleyParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());

		// lemmas

		Lemma dominus = new DummyLemma(Lemma.Type.Noun);
		Lemma serva = new DummyLemma(Lemma.Type.Noun);
		Lemma servus = new DummyLemma(Lemma.Type.Noun);
		Lemma plaustrum = new DummyLemma(Lemma.Type.Noun);
		Lemma forum = new DummyLemma(Lemma.Type.Noun);
		Lemma canto = new DummyLemma(Lemma.Type.Verb);
		Lemma rideo = new DummyLemma(Lemma.Type.Verb);
		Lemma amo = new DummyLemma(Lemma.Type.Verb);

		// tokens

		List<Token> tokens = new ArrayList<>();
		tokens.add(new Token("dominus", new Determination(dominus, Form.withValues(Casus.Nominative, Numerus.Singular, Genus.Masculine))));
		// tokens.add(new Token("serva", new Determination(serva,
		// Form.withValues(Casus.Nominative, Numerus.Singular,
		// Genus.Feminine))));
		// tokens.add(new Token("servi", new Determination(servus,
		// Form.withValues(Casus.Genitive, Numerus.Singular,
		// Genus.Masculine))));
//		tokens.add(new Token("forum",
//				new Determination(forum, Form.withValues(Casus.Nominative, Numerus.Singular, Genus.Masculine)),
//				new Determination(forum, Form.withValues(Casus.Vocative, Numerus.Singular, Genus.Neuter)),
//				new Determination(forum, Form.withValues(Casus.Accusative, Numerus.Singular, Genus.Neuter)),
//				new Determination(forum, Form.withValues(Casus.Genitive, Numerus.Plural, Genus.Neuter))));
		// tokens.add(new Token("cantat", new Determination(canto,
		// Form.withValues(Person.Third, Numerus.Singular, Tense.Present,
		// Mood.Indicative, Voice.Active, VerbType.Finite))));
		tokens.add(new Token("amat",
				new Determination(canto, Form.withValues(Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Accusative))));
		tokens.add(new Token("ridere", new Determination(rideo,
				Form.withValues(Tense.Present, Voice.Active, Finiteness.Infinitive))));
		tokens.add(new Token("plaustrum", new Determination(plaustrum,
				Form.withValues(Casus.Accusative, Numerus.Singular, Genus.Neuter))));

		EarleyParseResult<Token, FormParameter> result = parser.parse(tokens);

		new ChartSetPrinter<Token, FormParameter>(result.getCharts(), tokens).print(new PrintStream("E:\\temp.html"));

		System.out.println(result.getBinarySyntaxTree());

		System.out.println(result.getSyntaxTree());
	}
}
