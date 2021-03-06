package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.simple.SimpleVerb;
import com.zettelnet.latin.lemma.simple.conjugation.Conjugation;

public class ConjugationTest {

	public static void main(String[] args) throws FileNotFoundException {
		// LemmaFactory<Verb> factory = new WiktionaryConjugationResource(new
		// Scanner(new File("E:\\wiktionary-tests\\cant_o.txt")));

		// Verb verb = factory.makeLemma();
		Lemma verb = new SimpleVerb("cant", "cant_av", "cantat", Conjugation.First);
		Form form = Form.withValues(Person.First, Numerus.Singular, Mood.Indicative, Voice.Active, Tense.Present);
		System.out.println(form);
		System.out.println(verb.getForm(form));
		
		System.out.println(verb.getNominalForm());
		for (Map.Entry<Form, Collection<String>> entry : new TreeMap<>(verb.getForms()).entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println();

		for (Map.Entry<Derivation, Collection<Lemma>> derivation : verb.getDerivations().entrySet()) {
			System.out.println("Derivation " + derivation.getKey() + " - " + derivation.getValue().iterator().next().getNominalForm());
			for (Map.Entry<Form, Collection<String>> entry : derivation.getValue().iterator().next().getForms().entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		}
	}
}
