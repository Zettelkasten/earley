package com.zettelnet.earley.test;

import java.util.Collection;
import java.util.Map.Entry;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.SimpleVerb;
import com.zettelnet.latin.lemma.Verb;
import com.zettelnet.latin.lemma.conjugation.Conjugation;
import com.zettelnet.latin.lemma.conjugation.participle.FourthParticipleConjugation;

public class ParticipleTest {

	public static void main(String[] args) {
		Verb lemma = new SimpleVerb("aud", "aud_iv", "aud_it", Conjugation.Fourth, new FourthParticipleConjugation());
		for (Entry<Derivation, Collection<Lemma>> entry : lemma.getDerivations().entrySet()) {
			System.out.println("==== " + entry);
			for (Lemma derivation : entry.getValue()) {
				for (Genus genus : Genus.values()) {
					for (Numerus numerus : Numerus.values()) {
						for (Casus casus : Casus.values()) {
							Form form = Form.withValues(casus, numerus, genus);
							if (derivation.hasForm(form)) {
								System.out.println(" " + form + ": " + derivation.getForm(form));
							}
						}
					}
				}
			}
		}
	}
}
