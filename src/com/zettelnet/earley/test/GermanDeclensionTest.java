package com.zettelnet.earley.test;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.lemma.simple.SimpleGermanNoun;
import com.zettelnet.german.lemma.simple.declension.GermanDeclension;

public class GermanDeclensionTest {

	public static void main(String[] args) {
		
		// SimpleGermanNoun noun = new SimpleGermanNoun("Tag", "Tages", "Tag", GermanDeclension.Strong, GermanGenus.Masculine);
//		SimpleGermanNoun noun = new SimpleGermanNoun("Mutter", "Mutter", "Mütter", GermanDeclension.Strong, GermanGenus.Feminine);
		SimpleGermanNoun noun = new SimpleGermanNoun("Jahr", "Jahres", "Jahre", GermanDeclension.Strong, GermanGenus.Neuter);
		
		for (Map.Entry<GermanForm, Collection<String>> entry : noun.getForms().entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
