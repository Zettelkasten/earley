package com.zettelnet.latin.grammar;

import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.latin.lemma.LemmaTerminal;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.token.Token;

public final class LatinSymbol {

	private LatinSymbol() {
	}
	
	public static final NonTerminal<Token> Sentence = new SimpleNonTerminal<>("S");
	public static final NonTerminal<Token> NounPhrase = new SimpleNonTerminal<>("NP");
	public static final NonTerminal<Token> NounPhraseOpt = new SimpleNonTerminal<>("[NP]");
	public static final NonTerminal<Token> NounForm = new SimpleNonTerminal<>("NF");
	public static final NonTerminal<Token> AdjectivePhrase = new SimpleNonTerminal<>("AP");
	public static final NonTerminal<Token> AdjectivePhraseOpt = new SimpleNonTerminal<>("[AP]");

	public static final NonTerminal<Token> VerbPhrase = new SimpleNonTerminal<>("VP");
	public static final NonTerminal<Token> VerbForm = new SimpleNonTerminal<>("VF");
	public static final NonTerminal<Token> Arguments = new SimpleNonTerminal<>("Args");
	public static final NonTerminal<Token> AdverbalPhrase = new SimpleNonTerminal<>("AdvP");
	public static final NonTerminal<Token> AdverbalPhraseVar = new SimpleNonTerminal<>("AdvP*");

	public static final Terminal<Token> Verb = new LemmaTerminal(LemmaType.Verb);
	public static final Terminal<Token> Noun = new LemmaTerminal(LemmaType.Noun);
	public static final Terminal<Token> Adverb = new LemmaTerminal(LemmaType.Adverb);
	public static final Terminal<Token> Adjective = new LemmaTerminal(LemmaType.Adjective);
	public static final Terminal<Token> Conjunction = new LemmaTerminal(LemmaType.Conjunction);
	public static final Terminal<Token> Subjunction = new LemmaTerminal(LemmaType.Subjunction);
	public static final Terminal<Token> Infinitive = new LemmaTerminal(LemmaType.Infinitive);
	public static final Terminal<Token> Participle = new LemmaTerminal(LemmaType.Participle);
	public static final Terminal<Token> Gerund = new LemmaTerminal(LemmaType.Gerund);
	public static final Terminal<Token> Supine = new LemmaTerminal(LemmaType.Supine);
	public static final Terminal<Token> Preposition = new LemmaTerminal(LemmaType.Preposition);
	public static final Terminal<Token> Interjection = new LemmaTerminal(LemmaType.Interjection);
	public static final Terminal<Token> Pronoun = new LemmaTerminal(LemmaType.Pronoun);
	public static final NonTerminal<Token> PronounOpt = new SimpleNonTerminal<>("[pron]");
}
