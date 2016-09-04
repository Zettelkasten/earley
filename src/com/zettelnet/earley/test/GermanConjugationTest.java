package com.zettelnet.earley.test;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.property.GermanParticipleAuxiliary;
import com.zettelnet.german.lemma.simple.SimpleGermanVerb;
import com.zettelnet.german.lemma.simple.conjugation.GermanConjugation;

public class GermanConjugationTest {

	public static void main(String[] args) {
		GermanLemma verb = new SimpleGermanVerb("fragen", "frag", "fragt", "gefragt", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave);
		
		for (Map.Entry<GermanForm, Collection<String>> entry : new TreeMap<>(verb.getForms()).entrySet()) {
			GermanForm form = entry.getKey();
			
			Collection<String> values = entry.getValue();
			System.out.printf("[%s] %s%n", form, values);
		}
	}
}
