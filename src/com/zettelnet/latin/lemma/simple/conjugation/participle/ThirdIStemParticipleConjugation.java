package com.zettelnet.latin.lemma.simple.conjugation.participle;

import java.util.Arrays;
import java.util.Map;

import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.simple.conjugation.ConjugationStem;
import com.zettelnet.latin.lemma.simple.declension.DeclinableLemma;

public class ThirdIStemParticipleConjugation extends AbstractParticipleConjugation {

	private static class FirstFormEndings extends MapFormValueProvider<String> {
		public FirstFormEndings() {
			super(Arrays.asList(Tense.TYPE, Voice.TYPE, Genus.TYPE));

			put("i_ens", Tense.Present, Voice.Active, Genus.Masculine);
			put("i_ens", Tense.Present, Voice.Active, Genus.Feminine);
			put("i_ens", Tense.Present, Voice.Active, Genus.Neuter);
			put("us", Tense.Perfect, Voice.Passive, Genus.Masculine);
			put("a", Tense.Perfect, Voice.Passive, Genus.Feminine);
			put("um", Tense.Perfect, Voice.Passive, Genus.Neuter);
			put("_urus", Tense.Future, Voice.Active, Genus.Masculine);
			put("_ura", Tense.Future, Voice.Active, Genus.Feminine);
			put("_urum", Tense.Future, Voice.Active, Genus.Neuter);
			put("iendus", Tense.Future, Voice.Passive, Genus.Masculine);
			put("ienda", Tense.Future, Voice.Passive, Genus.Feminine);
			put("iendum", Tense.Future, Voice.Passive, Genus.Neuter);
		}
	}

	private static class StemEndings extends MapFormValueProvider<String> {
		public StemEndings() {
			super(Arrays.asList(Tense.TYPE, Voice.TYPE));

			put("ient", Tense.Present, Voice.Active);
			put("", Tense.Perfect, Voice.Passive);
			put("_ur", Tense.Future, Voice.Active);
			put("iend", Tense.Future, Voice.Passive);
		}
	}

	public static final FormValueProvider<String> FIRST_FORM_ENDINGS = new FirstFormEndings();
	public static final FormValueProvider<String> STEM_ENDINGS = new StemEndings();
	public static final FormValueProvider<ConjugationStem> STEM_TYPES = new ParticipleStems();
	public static final FormValueProvider<Map<Genus, FormProvider<DeclinableLemma>>> FORM_PROVIDERS = new ParticipleFormProviders();

	public ThirdIStemParticipleConjugation() {
		super(FIRST_FORM_ENDINGS, STEM_ENDINGS, STEM_TYPES, FORM_PROVIDERS);
	}
}
