package com.zettelnet.earley.test;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.conjugation.FirstConjugation;
import com.zettelnet.latin.lemma.verb.SimpleVerb;
import com.zettelnet.latin.lemma.verb.Verb;

public class ConjugationTest {

	public static void main(String[] args) {
		Verb verb = new SimpleVerb("cant", "cantav", "cantat", new FirstConjugation());
		System.out.println(verb.getNominalForm());
		for (Mood mood : Mood.values()) {
			for (Tense tense : Tense.values()) {
				for (Voice voice : Voice.values()) {
					for (Numerus numerus : Numerus.values()) {
						for (Person person : Person.values()) {
							Form form = Form.withValues(person, numerus, tense, mood, voice);
							if (verb.hasForm(form)) {
								System.out.println(form + ": " + verb.getForm(form));
							}
						}
					}
				}
			}
		}
	}
}
