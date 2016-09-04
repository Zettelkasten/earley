package com.zettelnet.earley.test;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.property.GermanParticipleAuxiliary;
import com.zettelnet.german.lemma.simple.SimpleGermanVerb;
import com.zettelnet.german.lemma.simple.conjugation.GermanConjugation;
import com.zettelnet.german.lemma.simple.conjugation.GermanConjugationStem;

public class GermanConjugationTest {

	public static void main(String[] args) {
//		GermanLemma verb = new SimpleGermanVerb("fragen", "frag", "fragt", "gefragt", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave);
//		GermanLemma verb = new SimpleGermanVerb("kommen", GermanConjugationStem.makeCollectionMap("komm", "kam", "gekommen", "komm", "käm"),
//				GermanConjugation.Strong, GermanParticipleAuxiliary.ToBe);
//		GermanLemma verb = new SimpleGermanVerb("fallen", GermanConjugationStem.makeCollectionMap("fall", "fiel", "gefallen", "fäll", "fiel"),
//				GermanConjugation.Strong, GermanParticipleAuxiliary.ToBe);
//		GermanLemma verb = new SimpleGermanVerb("blasen", GermanConjugationStem.makeCollectionMap("blas", "blies", "geblasen", "bläst", "blies", "blas"),
//				GermanConjugation.Strong, GermanParticipleAuxiliary.ToHave);
		GermanLemma verb = new SimpleGermanVerb("fressen", GermanConjugationStem.makeCollectionMap("fress", "fraß", "gefressen", "friss", "fräß", "friss"),
				GermanConjugation.Strong, GermanParticipleAuxiliary.ToHave);

		for (Map.Entry<GermanForm, Collection<String>> entry : new TreeMap<>(verb.getForms()).entrySet()) {
			GermanForm form = entry.getKey();

			Collection<String> values = entry.getValue();
			System.out.printf("[%s] %s%n", form, values);
		}
	}
}
