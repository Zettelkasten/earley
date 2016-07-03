package com.zettelnet.german.lemma;

public interface GermanLemmaType {

	public static final GermanLemmaType Noun = new SimpleGermanLemmaType("Noun");
	public static final GermanLemmaType Verb = new SimpleGermanLemmaType("Verb");
	public static final GermanLemmaType Participle = new SimpleGermanLemmaType("Participle");
	public static final GermanLemmaType Infinitive = new SimpleGermanLemmaType("Infinitive");
	public static final GermanLemmaType Gerund = new SimpleGermanLemmaType("Gerund");
	public static final GermanLemmaType Supine = new SimpleGermanLemmaType("Supine");
	public static final GermanLemmaType Adverb = new SimpleGermanLemmaType("Adverb");
	public static final GermanLemmaType Adjective = new SimpleGermanLemmaType("Adjective");
	public static final GermanLemmaType Article = new SimpleGermanLemmaType("Article");
	public static final GermanLemmaType Conjunction = new SimpleGermanLemmaType("Conjunction");
	public static final GermanLemmaType Subjunction = new SimpleGermanLemmaType("Subjunction");
	public static final GermanLemmaType Preposition = new SimpleGermanLemmaType("Preposition");
	public static final GermanLemmaType Interjection = new SimpleGermanLemmaType("Interjection");
	public static final GermanLemmaType Pronoun = new SimpleGermanLemmaType("Pronoun");

}
