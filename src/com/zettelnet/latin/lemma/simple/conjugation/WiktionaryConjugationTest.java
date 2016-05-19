package com.zettelnet.latin.lemma.simple.conjugation;

import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.CombinedProvider;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.simple.SimpleVerb;
import com.zettelnet.latin.lemma.simple.Verb;

public class WiktionaryConjugationTest {

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	public <T extends Lemma> void testProviders(T lemma, FormProvider<T> provider, FormProvider<T> expected) {
		Map<Form, Collection<String>> providerForms = new TreeMap<>(provider.getForms(lemma));

		for (Map.Entry<Form, Collection<String>> entry : providerForms.entrySet()) {
			Form form = entry.getKey();
			Collection<String> providerValue = entry.getValue();
			Collection<String> expectedValue = expected.getForm(lemma, form);

			collector.checkThat(String.format("Wrong mapping for %s %s", lemma, form), providerValue, equalTo(expectedValue));
		}

		Map<Form, Collection<String>> missingForms = new TreeMap<>(expected.getForms(lemma));
		missingForms.keySet().removeAll(providerForms.keySet());

		collector.checkThat("Provider is missing Forms", missingForms.size(), equalTo(0));
	}

	public void testWiktionaryVerb(String presentStem, String perfectStem, String supineStem, CombinedProvider<Verb> provider) {
		Verb verb = new SimpleVerb(presentStem, perfectStem, supineStem, provider);

		try {
			FormProvider<Verb> expected = new WiktionaryConjugationResource(new Scanner(new File(String.format("E:\\\\wiktionary-tests\\%s.txt", verb.getNominalForm())), "UTF-8"));

			testProviders(verb, provider, expected);
		} catch (FileNotFoundException e) {
			collector.addError(e);
		}
	}

	@Test
	public void test() {
		testWiktionaryVerb("ag", "_eg", "_act", Conjugation.Third);
		testWiktionaryVerb("aud", "aud_iv", "aud_it", Conjugation.Fourth);
		testWiktionaryVerb("cant", "cant_av", "cantat", Conjugation.First);
		testWiktionaryVerb("cap", "c_ep", "capt", Conjugation.ThirdIStem);
		testWiktionaryVerb("hab", "habu", "habit", Conjugation.Second);
		testWiktionaryVerb("laud", "laud_av", "laud_at", Conjugation.First);
		testWiktionaryVerb("r_id", "r_is", "r_is", Conjugation.Second);
		testWiktionaryVerb("terr", "terru", "territ", Conjugation.Second);
	}
}
