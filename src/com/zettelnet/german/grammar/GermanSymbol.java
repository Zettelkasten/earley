package com.zettelnet.german.grammar;

import com.zettelnet.earley.symbol.NonTerminal;
import com.zettelnet.earley.symbol.SimpleNonTerminal;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.german.lemma.GermanLemmaTerminal;
import com.zettelnet.german.lemma.GermanLemmaType;
import com.zettelnet.german.token.GermanToken;

public final class GermanSymbol {

	private GermanSymbol() {
	}

	public static final NonTerminal<GermanToken> Sentence = new SimpleNonTerminal<>("S");
	public static final NonTerminal<GermanToken> NounPhrase = new SimpleNonTerminal<>("NP");
	public static final NonTerminal<GermanToken> NounPhraseOpt = new SimpleNonTerminal<>("[NP]");
	public static final NonTerminal<GermanToken> NounForm = new SimpleNonTerminal<>("NF");
	public static final NonTerminal<GermanToken> AdjectivePhrase = new SimpleNonTerminal<>("AP");
	public static final NonTerminal<GermanToken> AdjectivePhraseOpt = new SimpleNonTerminal<>("[AP]");

	public static final NonTerminal<GermanToken> VerbPhrase = new SimpleNonTerminal<>("VP");
	public static final NonTerminal<GermanToken> VerbForm = new SimpleNonTerminal<>("VF");
	public static final NonTerminal<GermanToken> Arguments = new SimpleNonTerminal<>("Args");
	public static final NonTerminal<GermanToken> AdverbalPhrase = new SimpleNonTerminal<>("AdvP");
	public static final NonTerminal<GermanToken> AdverbalPhraseVar = new SimpleNonTerminal<>("AdvP*");

	public static final Terminal<GermanToken> Verb = new GermanLemmaTerminal(GermanLemmaType.Verb);
	public static final Terminal<GermanToken> Noun = new GermanLemmaTerminal(GermanLemmaType.Noun);
	public static final Terminal<GermanToken> Adverb = new GermanLemmaTerminal(GermanLemmaType.Adverb);
	public static final Terminal<GermanToken> Adjective = new GermanLemmaTerminal(GermanLemmaType.Adjective);
	public static final Terminal<GermanToken> Conjunction = new GermanLemmaTerminal(GermanLemmaType.Conjunction);
	public static final Terminal<GermanToken> Infinitive = new GermanLemmaTerminal(GermanLemmaType.Infinitive);
	public static final Terminal<GermanToken> Participle = new GermanLemmaTerminal(GermanLemmaType.Participle);
	public static final Terminal<GermanToken> Gerund = new GermanLemmaTerminal(GermanLemmaType.Gerund);
	public static final Terminal<GermanToken> Supine = new GermanLemmaTerminal(GermanLemmaType.Supine);
	public static final Terminal<GermanToken> Preposition = new GermanLemmaTerminal(GermanLemmaType.Preposition);
	public static final Terminal<GermanToken> Interjection = new GermanLemmaTerminal(GermanLemmaType.Interjection);
	public static final Terminal<GermanToken> Pronoun = new GermanLemmaTerminal(GermanLemmaType.Pronoun);
	public static final NonTerminal<GermanToken> PronounOpt = new SimpleNonTerminal<>("[pron]");
}
