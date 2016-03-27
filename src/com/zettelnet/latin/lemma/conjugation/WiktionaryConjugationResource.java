package com.zettelnet.latin.lemma.conjugation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.verb.Verb;

public class WiktionaryConjugationResource implements FormProvider<Verb> {

	private static final List<Form> formColumns = Arrays.asList(
			Form.withValues(Person.First, Numerus.Singular),
			Form.withValues(Person.Second, Numerus.Singular),
			Form.withValues(Person.Third, Numerus.Singular),
			Form.withValues(Person.First, Numerus.Plural),
			Form.withValues(Person.Second, Numerus.Plural),
			Form.withValues(Person.Third, Numerus.Plural));

	private final Map<Form, Collection<String>> forms;

	public WiktionaryConjugationResource(final Scanner in) {
		this.forms = new HashMap<>();

		Form formBase = Form.withValues();

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
					if (!"—".equals(value) && !value.contains(" ")) {
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
	}
	
	private Form retainForm(Form form) {
		return form.retainAll(Arrays.asList(Person.class, Numerus.class, Tense.class, Mood.class, Voice.class));
	}

	@Override
	public Collection<String> getForm(Verb lemma, Form form) {
		return forms.getOrDefault(retainForm(form), Collections.emptyList());
	}

	@Override
	public boolean hasForm(Verb lemma, Form form) {
		return forms.containsKey(retainForm(form));
	}

	@Override
	public Map<Form, Collection<String>> getForms(Verb lemma) {
		return forms;
	}
}
