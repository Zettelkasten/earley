package com.zettelnet.latin.lemma;

public interface LemmaType {

	public static final LemmaType Noun = new SimpleLemmaType("Noun");
	public static final LemmaType Verb = new SimpleLemmaType("Verb");
	public static final LemmaType Participle = new SimpleLemmaType("Participle");
	public static final LemmaType Infinitive = new SimpleLemmaType("Infinitive");
	public static final LemmaType Gerund = new SimpleLemmaType("Gerund");
	public static final LemmaType Supine = new SimpleLemmaType("Supine");
	public static final LemmaType Adverb = new SimpleLemmaType("Adverb");
	public static final LemmaType Adjective = new SimpleLemmaType("Adjective");
	public static final LemmaType Conjunction = new SimpleLemmaType("Conjunction");
	public static final LemmaType Subjunction = new SimpleLemmaType("Subjunction");
	public static final LemmaType Preposition = new SimpleLemmaType("Preposition");
	public static final LemmaType Interjection = new SimpleLemmaType("Interjection");
	public static final LemmaType Pronoun = new SimpleLemmaType("Pronoun");

}
