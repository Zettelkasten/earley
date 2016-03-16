package com.zettelnet.earley.test;

import java.util.HashMap;
import java.util.Map;

import com.zettelnet.earley.test.latin.DeterminationRegistry;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.adjective.SimpleAdjective;
import com.zettelnet.latin.lemma.adverb.SimpleAdverb;
import com.zettelnet.latin.lemma.conjugation.Conjugation;
import com.zettelnet.latin.lemma.conjunction.SimpleConjunction;
import com.zettelnet.latin.lemma.declension.Declension;
import com.zettelnet.latin.lemma.noun.SimpleNoun;
import com.zettelnet.latin.lemma.property.Valency;
import com.zettelnet.latin.lemma.verb.SimpleVerb;

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
	
	public static String getTranslation(Lemma lemma) {
		if (TRANSLATIONS.containsKey(lemma)) {
			return TRANSLATIONS.get(lemma);
		} else if (lemma.isDerivation()) {
			return getTranslation(lemma.getDerivedFrom());
		} else {
			return "%" + lemma.getNominalForm() + "%";
		}
	}

	// Ostia Altera T1
	static {
		register(new SimpleVerb("cant", "cant_av", "cantat", Conjugation.First, Valency.Single), "sing");
		register(new SimpleVerb("cant", "cant_av", "cantat", Conjugation.First, Valency.Accusative), "sing");
		register(new SimpleVerb("r_id", "r_is", "r_is", Conjugation.Second, Valency.Single), "laugh");
		register(new SimpleConjunction("et"), "and");
		register(new SimpleVerb("cl_am", "cl_am_av", "cl_am_at", Conjugation.First, Valency.Single), "cry");
		register(new SimpleVerb("tac", "tacu", "tacit", Conjugation.Second, Valency.Single), "silence");
		register(new SimpleAdverb("autem"), "however");
		// TODO no passive
		register(new SimpleVerb("saev", "saevi", "saev_it", Conjugation.Fourth, Valency.Single), "fume");
		register(new SimpleConjunction("atque"), "and");
		register(new SimpleConjunction("ac"), "and");
		register(new SimpleAdverb("n_on"), "not");
		register(new SimpleAdverb("iam"), "already");
		register(new SimpleVerb("i_urg", "i_urg_av", "i_urg_at", Conjugation.First, Valency.Single), "argue");
		// TODO impersonal in the passive
		register(new SimpleVerb("ven", "v_en", "vent", Conjugation.Fourth, Valency.Single), "come");
		// TODO irregular short imperative
		register(new SimpleVerb("d_ic", "d_ix", "dict", Conjugation.Third, Valency.Single), "say");
		register(new SimpleVerb("d_ic", "d_ix", "dict", Conjugation.Third, Valency.Accusative), "say");
		// TODO quis?
		register(new SimpleConjunction("sed"), "but");
		// TODO irregular short imperative
		register(new SimpleVerb("maled_ic", "maled_ix", "maledict", Conjugation.Third, Valency.Single), "complain");
		register(new SimpleVerb("maled_ic", "maled_ix", "maledict", Conjugation.Third, Valency.Accusative), "offend");
		register(new SimpleAdverb("semper"), "always");
		register(new SimpleAdverb("deinde"), "upon");
		// TODO decet
		register(new SimpleConjunction("neque"), "and not");
		register(new SimpleAdverb("tandem"), "finally");
		register(new SimpleVerb("l_ud", "l_us", "l_us", Conjugation.Third, Valency.Accusative), "play");
		register(new SimpleVerb("iuv", "i_uv", "i_ut", Conjugation.First, Valency.Accusative), "please");
		
		// extra
		register(new SimpleVerb("ulul", "ulul_av", "ulul_at", Conjugation.First, Valency.Single), "howl");
	}

	// Ostia Altera T2
	static {
		// TODO ubi?
		// TODO est
		register(new SimpleNoun("dominus", "domin", Declension.Second, Genus.Masculine), "lord");
		register(new SimpleNoun("vilicus", "vilic", Declension.Second, Genus.Masculine), "custodian");
		register(new SimpleNoun("servus", "serv", Declension.Second, Genus.Masculine), "slave");
		register(new SimpleAdverb("tum"), "then");
		register(new SimpleNoun("caelum", "cael", Declension.Second, Genus.Neuter), "sky");
		register(new SimpleAdjective("obsc_urus", "obsc_ura", "obsc_urum", "obsc_ur", Declension.FirstAndSecond), "dark");
		
	}
	
	// Names
	static {
		// T1
		register(new SimpleNoun("Domitilla", "Domitill", Declension.First, Genus.Feminine), "Domitilla");
		register(new SimpleNoun("L_ucius", "L_uci", Declension.Second, Genus.Masculine), "Lucius");
		register(new SimpleNoun("Tertia", "Terti", Declension.First, Genus.Feminine), "Tertia");
		register(new SimpleNoun("Publius", "Publi", Declension.Second, Genus.Masculine), "Publius");
		register(new SimpleNoun("Polybius", "Polybi", Declension.Second, Genus.Masculine), "Polybius");
		// T2
		register(new SimpleNoun("Pomp_onius", "Pomp_oni", Declension.Second, Genus.Masculine), "Pomponius");
		register(new SimpleNoun("M_arcellus", "M_arcell", Declension.Second, Genus.Masculine), "Marcellus");
		register(new SimpleNoun("Sel_enus", "Sel_en", Declension.Second, Genus.Masculine), "Selenus");
	}

	// some nouns
	static {
		register(new SimpleNoun("carmen", "carmin", Declension.Third, Genus.Neuter), "song");
	}
	
	static {
		System.out.println("Initialized latin registry with " + INSTANCE.getSize() + " entries");
	}
}
