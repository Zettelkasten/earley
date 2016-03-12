package com.zettelnet.latin.lemma.conjugation;

import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.verb.Verb;

public final class Conjugation {

	public static final FormProvider<Verb> First = new FirstConjugation();
	public static final FormProvider<Verb> Second = new SecondConjugation();
	public static final FormProvider<Verb> Third = new ThirdConjugation();
	public static final FormProvider<Verb> ThirdIStem = new ThirdIStemConjugation();
	public static final FormProvider<Verb> Fourth = new FourthConjugation();
}
