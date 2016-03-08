package com.zettelnet.earley.test;

import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.test.latin.DeterminationRegistry;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.SimpleAdverb;
import com.zettelnet.latin.lemma.SimpleConjunction;
import com.zettelnet.latin.lemma.SimpleNoun;
import com.zettelnet.latin.lemma.SimpleVerb;
import com.zettelnet.latin.lemma.conjugation.Conjugation;
import com.zettelnet.latin.lemma.declension.Declension;

public class LatinRegistry {

	public static DeterminationRegistry INSTANCE = new DeterminationRegistry();
	public static Map<Lemma, String> TRANSLATIONS = new HashMap<>();

	public static void register(Lemma lemma) {
		INSTANCE.register(lemma);
	}

	public static void register(Lemma lemma, String translation) {
		INSTANCE.register(lemma);
		TRANSLATIONS.put(lemma, translation);
	}

	// Ostia Altera T1
	static {
		register(new SimpleVerb("cant", "cant_av", "cantat", Conjugation.First), "sing");
		register(new SimpleVerb("r_id", "r_is", "r_is", Conjugation.Second), "laugh");
		register(new SimpleConjunction("et"), "and");
		register(new SimpleVerb("cl_am", "cl_am_av", "cl_am_at", Conjugation.First), "cry");
		register(new SimpleVerb("tac", "tacu", "tacit", Conjugation.Second), "silence");
		register(new SimpleConjunction("autem"), "however");
		// TODO no passive
		register(new SimpleVerb("saev", "saevi", "saev_it", Conjugation.Fourth), "fume");
		register(new SimpleConjunction("atque"), "and");
		register(new SimpleConjunction("ac"), "and");
		register(new SimpleAdverb("n_on"), "not");
		register(new SimpleAdverb("iam"), "already");
		register(new SimpleVerb("i_urg", "i_urg_av", "i_urg_at", Conjugation.First), "argue");
		// TODO impersonal in the passive
		register(new SimpleVerb("ven", "v_en", "vent", Conjugation.Fourth), "come");
		// TODO irregular short imperative
		register(new SimpleVerb("d_ic", "d_ix", "dict", Conjugation.Third), "say");
		// TODO quis?
		register(new SimpleConjunction("sed"), "but");
		// TODO irregular short imperative
		register(new SimpleVerb("maled_ic", "maled_ix", "maledict", Conjugation.Third), "offend");
		register(new SimpleAdverb("semper"), "always");
		register(new SimpleAdverb("deinde"), "upon");
		// TODO decet
		register(new SimpleConjunction("neque"), "and not");
		register(new SimpleAdverb("tandem"), "finally");
		register(new SimpleVerb("l_ud", "l_us", "l_us", Conjugation.Third), "play");
		register(new SimpleVerb("iuv", "i_uv", "i_ut", Conjugation.First), "please");
	}

	// some nouns
	static {
		register(new SimpleNoun("servus", "serv", Declension.Second, Genus.Masculine), "slave");
	}
}
