package com.zettelnet.earley.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.NonTerminal;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.PredicateTerminal;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.SimpleNonTerminal;
import com.zettelnet.earley.SimpleTerminal;
import com.zettelnet.earley.State;
import com.zettelnet.earley.Terminal;
import com.zettelnet.earley.latin.LatinParser;

public class LatinTest {

	public static void main(String[] args) {

		// CREATE GRAMMAR

		// terminals
		
		Terminal<String> nom = new PredicateTerminal<>("nom", (String token) -> {
			return token.endsWith("us");
		});
		Terminal<String> gen = new PredicateTerminal<>("gen", (String token) -> {
			return token.endsWith("i");
		});
		Terminal<String> dat = new PredicateTerminal<>("dat", (String token) -> {
			return token.endsWith("o");
		});
		Terminal<String> acc = new PredicateTerminal<>("acc", (String token) -> {
			return token.endsWith("um");
		});
		Terminal<String> abl = new PredicateTerminal<>("abl", (String token) -> {
			return token.endsWith("o");
		});
		Terminal<String> verb = new PredicateTerminal<>("verb", (String token) -> {
			return token.endsWith("t");
		});
		Terminal<String> inf = new PredicateTerminal<>("inf", (String token) -> {
			return token.endsWith("re");
		});

		NonTerminal sentence = new SimpleNonTerminal("S");
		NonTerminal sentenceInfNom = new SimpleNonTerminal("S<inf;nom>");
		NonTerminal sentenceInfAcc = new SimpleNonTerminal("S<inf;acc>");
		NonTerminal nounPhraseNom = new SimpleNonTerminal("NP<nom>");
		NonTerminal nounPhraseGen = new SimpleNonTerminal("NP<gen>");
		NonTerminal nounPhraseDat = new SimpleNonTerminal("NP<dat>");
		NonTerminal nounPhraseAcc = new SimpleNonTerminal("NP<acc>");
		NonTerminal nounPhraseAbl = new SimpleNonTerminal("NP<abl>");
		NonTerminal verbPhrase = new SimpleNonTerminal("VP");
		NonTerminal verbPhraseInf = new SimpleNonTerminal("VP<inf>");

		Set<Production> productions = new HashSet<>();
		productions.add(new Production(sentence, nounPhraseNom, verbPhrase));
		productions.add(new Production(nounPhraseNom, nom));
		productions.add(new Production(nounPhraseGen, gen));
		productions.add(new Production(nounPhraseDat, dat));
		productions.add(new Production(nounPhraseAcc, acc));
		productions.add(new Production(nounPhraseAbl, abl));
		productions.add(new Production(nounPhraseNom, nom, nounPhraseGen));
		productions.add(new Production(nounPhraseGen, gen, nounPhraseGen));
		productions.add(new Production(nounPhraseDat, dat, nounPhraseGen));
		productions.add(new Production(nounPhraseAcc, acc, nounPhraseGen));
		productions.add(new Production(nounPhraseAbl, abl, nounPhraseGen));
		productions.add(new Production(verbPhrase, verb));
		productions.add(new Production(verbPhrase, verb, nounPhraseNom));
		productions.add(new Production(verbPhrase, verb, nounPhraseDat));
		productions.add(new Production(verbPhrase, verb, nounPhraseAcc));
		productions.add(new Production(verbPhrase, verb, nounPhraseDat, nounPhraseAcc));
		productions.add(new Production(nounPhraseNom, sentenceInfNom));
		productions.add(new Production(nounPhraseAcc, sentenceInfAcc));
		productions.add(new Production(sentenceInfNom, nounPhraseNom, verbPhraseInf));
		productions.add(new Production(sentenceInfAcc, nounPhraseAcc, verbPhraseInf));
		productions.add(new Production(verbPhraseInf, inf));
		productions.add(new Production(verbPhraseInf, inf, nounPhraseNom));
		productions.add(new Production(verbPhraseInf, inf, nounPhraseDat));
		productions.add(new Production(verbPhraseInf, inf, nounPhraseAcc));
		productions.add(new Production(verbPhraseInf, inf, nounPhraseDat, nounPhraseAcc));

		LatinParser<String> lParser = new LatinParser<>(new Grammar(productions, sentence));
		EarleyParser<String> parser = new EarleyParser<>(new Grammar(productions, sentence));
		String string = "berthelmannus klassi meint seienum lehrerini wäre anstrengendum";
		// String string = "berthelmannus meint";
		ParseResult<String> result = parser.parse(Arrays.asList(string.split(" ")));
		System.out.println(result.isComplete());
		System.out.println(result.getTreeForest());

		try {
			ChartSetPrinter printer = new ChartSetPrinter(result.getCharts());
			printer.print(new PrintStream(new File("E:\\temp.html")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// berthelmannus klassi meint seienum lehrerini wäre anstrengendum
	}
}
