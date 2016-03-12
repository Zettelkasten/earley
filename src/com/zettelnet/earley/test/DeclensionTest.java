package com.zettelnet.earley.test;

import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.declension.ThirdDeclension;
import com.zettelnet.latin.lemma.noun.Noun;
import com.zettelnet.latin.lemma.noun.SimpleNoun;

public class DeclensionTest {

	public static void main(String[] args) {
		Noun noun = new SimpleNoun("turris", "turr", new ThirdDeclension(), Genus.Feminine);

		for (Genus genus : Genus.values()) {
			for (Numerus numerus : Numerus.values()) {
				for (Casus casus : Casus.values()) {
					Form form = Form.withValues(casus, numerus, genus);
					if (noun.hasForm(form)) {
						System.out.println(form + ": " + noun.getForm(form));
					}
				}
			}
		}
	}
}
