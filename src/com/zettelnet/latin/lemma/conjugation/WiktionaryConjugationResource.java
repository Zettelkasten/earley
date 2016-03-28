package com.zettelnet.latin.lemma.conjugation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.zettelnet.earley.param.property.PropertySetValueResource;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.verb.Verb;

public class WiktionaryConjugationResource implements FormProvider<Verb>, PropertySetValueResource<Form> {

	public static final String FINITE_FORMS = "FiniteForms";
	public static final String INFINITIVES = "Infinitives";
	public static final String PARTICIPLES = "Participles";
	public static final String SUPINE = "Supine";

	private static final List<Form> finiteColumns = Arrays.asList(
			Form.withValues(Person.First, Numerus.Singular),
			Form.withValues(Person.Second, Numerus.Singular),
			Form.withValues(Person.Third, Numerus.Singular),
			Form.withValues(Person.First, Numerus.Plural),
			Form.withValues(Person.Second, Numerus.Plural),
			Form.withValues(Person.Third, Numerus.Plural));

	private static final List<Form> infinitiveAndParticipleColumns = Arrays.asList(
			Form.withValues(Tense.Present, Voice.Active),
			Form.withValues(Tense.Perfect, Voice.Active),
			Form.withValues(Tense.Future, Voice.Active),
			Form.withValues(Tense.Present, Voice.Passive),
			Form.withValues(Tense.Perfect, Voice.Passive),
			Form.withValues(Tense.Future, Voice.Passive));

	private static final List<Form> gerundColumns = Arrays.asList(
			Form.withValues(Casus.Nominative),
			Form.withValues(Casus.Genitive),
			Form.withValues(Casus.Dative),
			Form.withValues(Casus.Accusative));
	
	private static final List<Form> supineColumns = Arrays.asList(
			Form.withValues(Casus.Accusative),
			Form.withValues(Casus.Ablative));

	private final Map<String, Map<Form, Collection<String>>> data;
	
	private final Map<Form, Collection<String>> forms;
	private final Map<Form, Collection<String>> infinitives;
	private final Map<Form, Collection<String>> participles;
	private final Map<Form, Collection<String>> supine;

	private static enum NonFiniteType {
		Infinitive, Participle, GerundAndSupine;
	}

	public WiktionaryConjugationResource(final Scanner in) {
		this.data = new LinkedHashMap<>();

		this.forms = new HashMap<>();
		data.put(FINITE_FORMS, forms);
		this.infinitives = new HashMap<>();
		data.put(INFINITIVES, infinitives);
		this.participles = new HashMap<>();
		data.put(PARTICIPLES, participles);
		this.supine = new HashMap<>();
		data.put(SUPINE, supine);

		Form formBase = Form.withValues();
		NonFiniteType nonFiniteType = null;

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
					continue mainLoop;
				case "infinitives":
					nonFiniteType = NonFiniteType.Infinitive;
					break;
				case "participles":
					nonFiniteType = NonFiniteType.Participle;
					break;
				case "verbal nouns":
					nonFiniteType = NonFiniteType.GerundAndSupine;
					break;
				case "gerund":
				case "supine":
				case "nominative":
				case "genitive":
				case "dative/ablative":
				case "accusative":
				case "ablative":
					// ignore
					break;
				default:
					done = true;
					break;
				}
				if (!done) {
					split.pollFirst();
				}
			}

			if (nonFiniteType == null) {
				// finite type
				addRow(forms, finiteColumns, formBase, split);
			} else {
				// infinitive, participle or gerund / supine
				switch (nonFiniteType) {
				case Infinitive:
					addRow(infinitives, infinitiveAndParticipleColumns, Form.withValues(Casus.Nominative), split);
					break;
				case Participle:
					addRow(participles, infinitiveAndParticipleColumns, Form.withValues(Casus.Nominative, Genus.Masculine), split);
					break;
				case GerundAndSupine:
					addRow(infinitives, gerundColumns, Form.withValues(Tense.Present, Voice.Active), split);
					// ablative = dative
					infinitives.put(Form.withValues(Tense.Present, Voice.Active, Casus.Ablative), infinitives.get(Form.withValues(Tense.Present, Voice.Active, Casus.Dative)));
					
					addRow(supine, supineColumns, Form.withValues(), split);
					break;
				default:
					throw new AssertionError("Unknown non-finite type");
				}
			}
		}

		for (Map<Form, Collection<String>> section : data.values()) {
			for (Iterator<Collection<String>> i = section.values().iterator(); i.hasNext();) {
				if (i.next().isEmpty()) {
					i.remove();
				}
			}
		}
	}

	private static void addRow(Map<Form, Collection<String>> forms, List<Form> columns, Form formBase, Deque<String> split) {
		for (Iterator<Form> columnIterator = columns.iterator(); columnIterator.hasNext() && !split.isEmpty();) {
			Form formColumn = columnIterator.next();
			Form form = formBase.derive(formColumn);

			if (!forms.containsKey(form)) {
				forms.put(form, new HashSet<>());
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

	private Form retainFiniteForm(Form form) {
		return form.retainAll(Arrays.asList(Person.class, Numerus.class, Tense.class, Mood.class, Voice.class));
	}

	@Override
	public Collection<String> getForm(Verb lemma, Form form) {
		return forms.getOrDefault(retainFiniteForm(form), Collections.emptyList());
	}

	@Override
	public boolean hasForm(Verb lemma, Form form) {
		return forms.containsKey(retainFiniteForm(form));
	}

	@Override
	public Map<Form, Collection<String>> getForms(Verb lemma) {
		return forms;
	}

	@Override
	public Collection<String> getValue(String section, Form key) {
		return data.get(section).get(key);
	}

	@Override
	public Map<Form, Collection<String>> getSection(String section) {
		return data.get(section);
	}

	@Override
	public Map<String, Map<Form, Collection<String>>> getSections() {
		return data;
	}

	@Override
	public boolean containsSection(String section) {
		return data.containsKey(section);
	}
}
