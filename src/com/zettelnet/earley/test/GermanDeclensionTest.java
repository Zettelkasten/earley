package com.zettelnet.earley.test;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.lemma.simple.SimpleGermanNoun;
import com.zettelnet.german.lemma.simple.declension.GermanDeclension;

public class GermanDeclensionTest {

	public static void main(String[] args) {
		
//		SimpleGermanNoun noun = new SimpleGermanNoun("Tag", "Tages", "Tag", GermanDeclension.Strong, GermanGenus.Masculine);
//		SimpleGermanNoun noun = new SimpleGermanNoun("Fürst", "Fürsten", "Fürsten", GermanDeclension.Weak, GermanGenus.Masculine);
//		SimpleGermanNoun noun = new SimpleGermanNoun("Staat", "Staats", "Staaten", GermanDeclension.Mixed, GermanGenus.Masculine);
		 
//		SimpleGermanNoun noun = new SimpleGermanNoun("Mutter", "Mutter", "Mütter", GermanDeclension.Strong, GermanGenus.Feminine);
//		SimpleGermanNoun noun = new SimpleGermanNoun("Rose", "Rose", "Rosen", GermanDeclension.Weak, GermanGenus.Feminine);
			
//		SimpleGermanNoun noun = new SimpleGermanNoun("Jahr", "Jahres", "Jahre", GermanDeclension.Strong, GermanGenus.Neuter);
		SimpleGermanNoun noun = new SimpleGermanNoun("Ohr", "Ohres", "Ohren", GermanDeclension.Mixed, GermanGenus.Neuter);
		
		for (Map.Entry<GermanForm, Collection<String>> entry : new TreeMap<>(noun.getForms()).entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
