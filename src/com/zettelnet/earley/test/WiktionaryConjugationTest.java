package com.zettelnet.earley.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.conjugation.Conjugation;
import com.zettelnet.latin.lemma.verb.SimpleVerb;
import com.zettelnet.latin.lemma.verb.Verb;

public class WiktionaryConjugationTest {

	public static void main(String[] args) throws FileNotFoundException {
//		File file = new File("E:\\wiktionary-tests\\cant_o.txt");
//		Verb verb = new SimpleVerb("cant", "cant_av", "cantat", Conjugation.First);
//		File file = new File("E:\\wiktionary-tests\\audi_o.txt");
//		Verb verb = new SimpleVerb("aud", "aud_iv", "aud_it", Conjugation.Fourth);
//		File file = new File("E:\\wiktionary-tests\\ag_o.txt");
//		Verb verb = new SimpleVerb("ag", "_eg", "_act", Conjugation.Third);
//		File file = new File("E:\\wiktionary-tests\\capi_o.txt");
//		Verb verb = new SimpleVerb("cap", "c_ep", "capt", Conjugation.ThirdIStem);
		File file = new File("E:\\wiktionary-tests\\habe_o.txt");
		Verb verb = new SimpleVerb("hab", "habu", "habit", Conjugation.Second);
		
		Map<Form, Collection<String>> forms = new HashMap<>();

		Scanner in = new Scanner(file, "UTF-8");

		Form formBase = Form.withValues();
		List<Form> formColumns = Arrays.asList(
				Form.withValues(Person.First, Numerus.Singular),
				Form.withValues(Person.Second, Numerus.Singular),
				Form.withValues(Person.Third, Numerus.Singular),
				Form.withValues(Person.First, Numerus.Plural),
				Form.withValues(Person.Second, Numerus.Plural),
				Form.withValues(Person.Third, Numerus.Plural));

		mainLoop: while (in.hasNextLine()) {
			String line = in.nextLine();
			Deque<String> split = new LinkedList<>(Arrays.asList(line.split("\t")));

			boolean done = false;
			while (!done && !split.isEmpty()) {
				switch (split.getFirst()) {
				case "singular":
				case "plural":
				case "first":
				case "second":
				case "third":
					// ignore
					break;
				case "indicative":
					formBase = formBase.derive(Mood.Indicative);
					break;
				case "subjunctive":
					formBase = formBase.derive(Mood.Subjunctive);
					break;
				case "imperative":
					formBase = formBase.derive(Mood.Imperative);
					break;
				case "active":
					formBase = formBase.derive(Voice.Active);
					break;
				case "passive":
					formBase = formBase.derive(Voice.Passive);
					break;
				case "present":
					formBase = formBase.derive(Tense.Present);
					break;
				case "imperfect":
					formBase = formBase.derive(Tense.Imperfect);
					break;
				case "future":
					formBase = formBase.derive(Tense.Future);
					break;
				case "perfect":
					formBase = formBase.derive(Tense.Perfect);
					break;
				case "pluperfect":
					formBase = formBase.derive(Tense.Pluperfect);
					break;
				case "future perfect":
					formBase = formBase.derive(Tense.FuturePerfect);
					break;
				case "non-finite forms":
					break mainLoop;
				default:
					done = true;
					break;
				}
				if (!done) {
					split.pollFirst();
				}
			}

			for (Iterator<Form> columnIterator = formColumns.iterator(); columnIterator.hasNext() && !split.isEmpty();) {
				Form formColumn = columnIterator.next();
				Form form = formBase.derive(formColumn.getPerson(), formColumn.getNumerus());

				if (!forms.containsKey(form)) {
					forms.put(form, new ArrayList<>());
				}

				for (String value : split.pollFirst().split("\\s*,\\s*")) {
					if (!"—".equals(value)) {
						value = value.replaceAll("ā", "_a");
						value = value.replaceAll("ē", "_e");
						value = value.replaceAll("ō", "_o");
						value = value.replaceAll("ū", "_u");
						value = value.replaceAll("ī", "_i");
						forms.get(form).add(value);
					}
				}
			}
		}

		for (Iterator<Collection<String>> i = forms.values().iterator(); i.hasNext();) {
			if (i.next().isEmpty()) {
				i.remove();
			}
		}

		for (Mood mood : Mood.values()) {
			for (Tense tense : Tense.values()) {
				for (Voice voice : Voice.values()) {
					for (Numerus numerus : Numerus.values()) {
						for (Person person : Person.values()) {
							Form form = Form.withValues(person, numerus, tense, mood, voice);
							if (verb.hasForm(form)) {
								Collection<String> selfForm = verb.getForm(form);
								Collection<String> wiktionaryForm = forms.get(form);

								if (!selfForm.equals(wiktionaryForm)) {
									System.out.println(form + ": " + selfForm + "\tvs\twiktionary " + wiktionaryForm);
								}
							}
						}
					}
				}
			}
		}

		forms.keySet().removeAll(verb.getForms().keySet());
		System.out.println("===== wiktionary only forms =====");
		for (Map.Entry<Form, Collection<String>> entry : forms.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		in.close();
	}
}
