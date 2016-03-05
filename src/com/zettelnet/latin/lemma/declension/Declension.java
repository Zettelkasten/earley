package com.zettelnet.latin.lemma.declension;

import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Noun;

public final class Declension {

	public static final FormProvider<Noun> First = new FirstDeclension();
	public static final FormProvider<Noun> Second = new SecondDeclension();
	public static final FormProvider<Noun> Third = new ThirdDeclension();
	public static final FormProvider<Noun> ThirdI = new ThirdDeclensionI();
	public static final FormProvider<Noun> ThirdMixed = new ThirdDeclensionMixed();
	public static final FormProvider<Noun> Forth = new FourthDeclension();
	public static final FormProvider<Noun> Fifth = new FifthDeclension();
}