package com.zettelnet.earley.test;

import com.zettelnet.earley.test.latin.DeterminationRegistry;
import com.zettelnet.latin.lemma.SimpleAdverb;
import com.zettelnet.latin.lemma.SimpleConjunction;
import com.zettelnet.latin.lemma.SimpleVerb;
import com.zettelnet.latin.lemma.conjugation.Conjugation;

public class LatinRegistry {

	public static DeterminationRegistry INSTANCE = new DeterminationRegistry();

	// Ostia Altera T1
	static {
		INSTANCE.register(new SimpleVerb("cant", "cant_av", "cantat", Conjugation.First));
		INSTANCE.register(new SimpleVerb("r_id", "r_is", "r_is", Conjugation.Second));
		INSTANCE.register(new SimpleConjunction("et"));
		INSTANCE.register(new SimpleVerb("cl_am", "cl_am_av", "cl_am_at", Conjugation.First));
		INSTANCE.register(new SimpleVerb("tac", "tacu", "tacit", Conjugation.Second));
		INSTANCE.register(new SimpleConjunction("autem"));
		// TODO no passive
		INSTANCE.register(new SimpleVerb("saev", "saevi", "saev_it", Conjugation.Fourth));
		INSTANCE.register(new SimpleConjunction("atque"));
		INSTANCE.register(new SimpleConjunction("ac"));
		INSTANCE.register(new SimpleAdverb("n_on"));
		INSTANCE.register(new SimpleAdverb("iam"));
		INSTANCE.register(new SimpleVerb("i_urg", "i_urg_av", "i_urg_at", Conjugation.First));
		// TODO impersonal in the passive
		INSTANCE.register(new SimpleVerb("ven", "v_en", "vent", Conjugation.Fourth));
		// TODO irregular short imperative
		INSTANCE.register(new SimpleVerb("d_ic", "d_ix", "dict", Conjugation.Third));
		// TODO quis?
		INSTANCE.register(new SimpleConjunction("sed"));
		// TODO irregular short imperative
		INSTANCE.register(new SimpleVerb("maled_ic", "maled_ix", "maledict", Conjugation.Third));
		INSTANCE.register(new SimpleAdverb("semper"));
		INSTANCE.register(new SimpleAdverb("deinde"));
		// TODO decet
		INSTANCE.register(new SimpleConjunction("neque"));
		INSTANCE.register(new SimpleAdverb("tandem"));
		INSTANCE.register(new SimpleVerb("l_ud", "l_us", "l_us", Conjugation.Third));
		INSTANCE.register(new SimpleVerb("iuv", "i_uv", "i_ut", Conjugation.First));
	}
}
