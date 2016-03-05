package com.zettelnet.latin.lemma;

public interface LemmaType {

	public static final LemmaType Noun = new SimpleLemmaType("Noun");
	public static final LemmaType Verb = new SimpleLemmaType("Adjective");
	public static final LemmaType Adverb = new SimpleLemmaType("Adverb");
	public static final LemmaType Adjective = new SimpleLemmaType("Adjective");

}
