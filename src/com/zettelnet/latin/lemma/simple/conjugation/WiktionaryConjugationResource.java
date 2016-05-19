package com.zettelnet.latin.lemma.simple.conjugation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.zettelnet.latin.derivation.Derivation;
import com.zettelnet.latin.derivation.DerivationProvider;
import com.zettelnet.latin.derivation.DerivationType;
import com.zettelnet.latin.derivation.DistributingDerivationProvider;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaFactory;
import com.zettelnet.latin.lemma.simple.SimpleGerund;
import com.zettelnet.latin.lemma.simple.SimpleVerb;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class WiktionaryConjugationResource implements LemmaFactory<ConjugableLemma>, FormProvider<ConjugableLemma> {

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

	private final Map<Form, Collection<String>> forms;
	private final Map<Form, Collection<String>> infinitives;
	private final Map<Form, Collection<String>> participles;
	private final Map<Form, Collection<String>> supine;

	private final DerivationProvider<ConjugableLemma> derivationProvider;

	private static enum NonFiniteType {
		Infinitive, Participle, GerundAndSupine;
	}

	public WiktionaryConjugationResource(final Scanner in) {
		this.derivationProvider = new DistributingDerivationProvider<ConjugableLemma>()
				.addProvider(DerivationType.Infinitive, new DerivationProvider<ConjugableLemma>() {
					private Form retainForm(Form form) {
						return form.retainAll(Arrays.asList(Tense.class, Voice.class));
					}

					@Override
					public Collection<Lemma> getDerivation(ConjugableLemma lemma, Derivation derivation) {
						Form baseForm = retainForm(derivation.getForm());
						List<Lemma> lemmas = new ArrayList<>();
						for (String firstForm : infinitives.get(baseForm.derive(Casus.Nominative))) {
							Lemma gerund = new SimpleGerund(firstForm, null, new FormProvider<DeclinableLemma>() {
								@Override
								public Collection<String> getForm(DeclinableLemma lemma, Form form) {
									return infinitives.get(baseForm.derive(form));
								}

								@Override
								public boolean hasForm(DeclinableLemma lemma, Form form) {
									return infinitives.containsKey(baseForm.derive(form.getCasus()));
								}

								public Set<Casus> getCasusSet() {
									return EnumSet.allOf(Casus.class);
								}

								@Override
								public Map<Form, Collection<String>> getForms(DeclinableLemma lemma) {
									Map<Form, Collection<String>> forms = new HashMap<>();
									for (Casus casus : getCasusSet()) {
										Form form = Form.withValues(casus);
										if (hasForm(lemma, form)) {
											forms.put(form, getForm(lemma, form));
										}
									}
									return forms;
								}

							}, Genus.Neuter, lemma, derivation);
							lemmas.add(gerund);
						}
						return lemmas;
					}

					@Override
					public boolean hasDerivation(ConjugableLemma lemma, Derivation derivation) {
						return !getDerivation(lemma, derivation).isEmpty();
					}

					@Override
					public Map<Derivation, Collection<Lemma>> getDerivations(ConjugableLemma lemma) {
						Map<Derivation, Collection<Lemma>> derivations = new HashMap<>();
						for (Form form : infinitives.keySet()) {
							Derivation derivation = Derivation.withValues(DerivationType.Infinitive, retainForm(form));
							derivations.put(derivation, getDerivation(lemma, derivation));
						}
						return derivations;
					}
				});
		// TODO: Add Participles, Supine

		this.forms = new HashMap<>();
		this.infinitives = new HashMap<>();
		this.participles = new HashMap<>();
		this.supine = new HashMap<>();

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
				case "ConjugableLemmaal nouns":
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

		// remove empty values
		for (Map<Form, Collection<String>> section : Arrays.asList(forms, infinitives, participles, supine)) {
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

	@Override
	public ConjugableLemma makeLemma() {
		return new SimpleVerb(null, this, derivationProvider);
	}

	private Form retainFiniteForm(Form form) {
		return form.retainAll(Arrays.asList(Person.class, Numerus.class, Tense.class, Mood.class, Voice.class));
	}

	@Override
	public Collection<String> getForm(ConjugableLemma lemma, Form form) {
		return forms.getOrDefault(retainFiniteForm(form), Collections.emptyList());
	}

	@Override
	public boolean hasForm(ConjugableLemma lemma, Form form) {
		return forms.containsKey(retainFiniteForm(form));
	}

	@Override
	public Map<Form, Collection<String>> getForms(ConjugableLemma lemma) {
		return forms;
	}
}
