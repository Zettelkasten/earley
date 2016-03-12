package com.zettelnet.latin.lemma.conjugation.derivation;

import java.util.Arrays;
import java.util.Map;

import com.zettelnet.latin.form.FormValueProvider;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.MapFormValueProvider;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.DeclinableLemma;
import com.zettelnet.latin.lemma.FormProvider;
import com.zettelnet.latin.lemma.VerbStem;

/**
 * Represents the <strong>first conjugation</strong>, also known as
 * <strong>a-conjugation</strong>.
 * 
 * @author Zettelkasten
 *
 */
public class FourthParticipleConjugation extends AbstractParticipleConjugation {

	private static class FirstFormEndings extends MapFormValueProvider<String> {
		public FirstFormEndings() {
			super(Arrays.asList(Tense.class, Voice.class, Genus.class));

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
			super(Arrays.asList(Tense.class, Voice.class));

			put("i_ent", Tense.Present, Voice.Active);
			put("", Tense.Perfect, Voice.Passive);
			put("_ur", Tense.Future, Voice.Active);
			put("iend", Tense.Future, Voice.Passive);
		}
	}

	public static final FormValueProvider<String> FIRST_FORM_ENDINGS = new FirstFormEndings();
	public static final FormValueProvider<String> STEM_ENDINGS = new StemEndings();
	public static final FormValueProvider<VerbStem> STEM_TYPES = new ParticipleStems();
	public static final FormValueProvider<Map<Genus, FormProvider<DeclinableLemma>>> FORM_PROVIDERS = new ParticipleFormProviders();

	public FourthParticipleConjugation() {
		super(FIRST_FORM_ENDINGS, STEM_ENDINGS, STEM_TYPES, FORM_PROVIDERS);
	}
}
