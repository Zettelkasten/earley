package com.zettelnet.earley.test;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.simple.SimpleGermanArticle;
import com.zettelnet.german.lemma.simple.SimpleGermanNoun;
import com.zettelnet.german.lemma.simple.declension.GermanDeclension;

public class GermanDeclensionTest {

	public static void main(String[] args) {
		
//		GermanLemma noun = new SimpleGermanNoun("Tag", "Tages", "Tag", GermanDeclension.Strong, GermanGenus.Masculine);
//		GermanLemma noun = new SimpleGermanNoun("Fürst", "Fürsten", "Fürsten", GermanDeclension.Weak, GermanGenus.Masculine);
//		GermanLemma noun = new SimpleGermanNoun("Staat", "Staats", "Staaten", GermanDeclension.Mixed, GermanGenus.Masculine);
		 
//		GermanLemma noun = new SimpleGermanNoun("Mutter", "Mutter", "Mütter", GermanDeclension.Strong, GermanGenus.Feminine);
//		GermanLemma noun = new SimpleGermanNoun("Rose", "Rose", "Rosen", GermanDeclension.Weak, GermanGenus.Feminine);
			
//		GermanLemma noun = new SimpleGermanNoun("Jahr", "Jahres", "Jahre", GermanDeclension.Strong, GermanGenus.Neuter);
		GermanLemma noun = new SimpleGermanNoun("Ohr", "Ohres", "Ohren", GermanDeclension.Mixed, GermanGenus.Neuter);
		
		GermanLemma article = SimpleGermanArticle.DEFINITE_ARTICLE;
		
		for (Map.Entry<GermanForm, Collection<String>> entry : new TreeMap<>(noun.getForms()).entrySet()) {
			GermanForm form = entry.getKey();
			
			String determiner = article.getForm(form).iterator().next();
			Collection<String> values = entry.getValue();
			System.out.printf("[%s] %s %s%n", form, determiner, values);
		}
		
		Collection<GermanLemma> lemmas = LatinRegistry.getTranslation(LatinRegistry.INSTANCE.getDeterminations("servus").iterator().next().getLemma());
		System.out.println(lemmas.iterator().next().getForms());
	}
}
