package com.zettelnet.latin.lemma.declension;

import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;

public final class Declension {

	public static final FormProvider<DeclinableLemma> First = new FirstDeclension();
	public static final FormProvider<DeclinableLemma> Second = new SecondDeclension();
	public static final FormProvider<DeclinableLemma> Third = new ThirdDeclension();
	public static final FormProvider<DeclinableLemma> ThirdIStem = new ThirdIStemDeclension();
	public static final FormProvider<DeclinableLemma> ThirdMixed = new ThirdMixedDeclension();
	public static final FormProvider<DeclinableLemma> Forth = new FourthDeclension();
	public static final FormProvider<DeclinableLemma> Fifth = new FifthDeclension();
}
