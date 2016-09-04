package com.zettelnet.earley.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.simple.SimpleGermanNoun;
import com.zettelnet.german.lemma.simple.declension.GermanDeclension;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.SubjunctionType;
import com.zettelnet.latin.lemma.property.Valency;
import com.zettelnet.latin.lemma.simple.SimpleAdjective;
import com.zettelnet.latin.lemma.simple.SimpleAdverb;
import com.zettelnet.latin.lemma.simple.SimpleConjunction;
import com.zettelnet.latin.lemma.simple.SimpleNoun;
import com.zettelnet.latin.lemma.simple.SimpleSubjunction;
import com.zettelnet.latin.lemma.simple.SimpleVerb;
import com.zettelnet.latin.lemma.simple.conjugation.Conjugation;
import com.zettelnet.latin.lemma.simple.declension.Declension;
import com.zettelnet.latin.token.Determination;
import com.zettelnet.latin.token.DeterminationRegistry;

public final class LatinRegistry {

	private LatinRegistry() {
	}
	
	public static DeterminationRegistry INSTANCE = new DeterminationRegistry();
	public static Map<Lemma, GermanLemma> TRANSLATIONS = new HashMap<>();

	public static void register(Lemma lemma) {
		INSTANCE.register(lemma);
	}

	public static void register(Lemma lemma, GermanLemma translation) {
		INSTANCE.register(lemma);
		TRANSLATIONS.put(lemma, translation);
	}
	
	public static Collection<GermanLemma> getTranslation(Lemma lemma) {
		if (TRANSLATIONS.containsKey(lemma)) {
			return Arrays.asList(TRANSLATIONS.get(lemma));
		} else if (lemma.isDerivation()) {
			return getTranslation(lemma.getDerivedFrom());
		} else {
			return Collections.emptySet();
		}
	}

	// Ostia Altera T1
	static {
		register(new SimpleVerb("cant", "cant_av", "cantat", Conjugation.First, Valency.Single),
				new German"sing");
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
		register(new SimpleVerb("iuv", "i_uv", "i_ut", Conjugation.First, Valency.Single), "is fun");
		
		// extra
		register(new SimpleVerb("ulul", "ulul_av", "ulul_at", Conjugation.First, Valency.Single), "howl");
	}

	// Ostia Altera T2
	static {
		// TODO ubi?
		// TODO est
		Lemma sum = new SimpleVerb("sum", "fu", "fu", Conjugation.First, Valency.Copula);
		INSTANCE.register("est", new Determination(sum, Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Copula));

		register(new SimpleNoun("dominus", "domin", Declension.Second, Genus.Masculine), 
				new SimpleGermanNoun("Herr", "Herren", "Herren", GermanDeclension.Weak, GermanGenus.Masculine));
		register(new SimpleNoun("vilicus", "vilic", Declension.Second, Genus.Masculine), "custodian");
		register(new SimpleNoun("servus", "serv", Declension.Second, Genus.Masculine), "slave");
		register(new SimpleAdverb("tum"), "then");
		register(new SimpleNoun("caelum", "cael", Declension.Second, Genus.Neuter), "sky");
		register(new SimpleAdjective("obsc_urus", "obsc_ura", "obsc_urum", "obsc_ur", Declension.FirstAndSecond), "dark");
		// TODO -que
		register(new SimpleNoun("serva", "serv", Declension.First, Genus.Feminine), "slave");
		// TODO c_ur?
		register(new SimpleVerb("lab_or", "lab_or_av", "lab_or_at", Conjugation.First, Valency.Single), "work");
		register(new SimpleAdverb("subit_o"), "suddenly");
		register(new SimpleVerb("interrog", "interrog_av", "interrog_at", Conjugation.First), "ask");
		register(new SimpleNoun("plaustrum", "plaustr", Declension.Second, Genus.Neuter), "wagon");
		register(new SimpleNoun("fr_umentum", "fr_ument", Declension.Second, Genus.Neuter), "wheat");
		// TODO enim
		register(new SimpleAdjective("m_aturus", "m_atura", "m_aturum", "m_atur", Declension.FirstAndSecond), "ripe");
		register(new SimpleAdverb("quoque"), "also");
		register(new SimpleNoun("domina", "domin", Declension.First, Genus.Feminine), "lady");
		register(new SimpleNoun("puella", "puell", Declension.First, Genus.Feminine), "girl");
		// TODO ecce!
		register(new SimpleAdverb("etiam"), "and also");
		register(new SimpleAdverb("imm_o"), "on the contrary");
		register(new SimpleAdverb("v_er_o"), "truly");
		register(new SimpleAdjective("c_unctus", "c_uncta", "c_unctum", "c_unct", Declension.FirstAndSecond), "whole");
		// TODO adsum
		register(new SimpleAdjective("onustus", "onusta", "onustum", "onust", Declension.FirstAndSecond), "loaded");
		register(new SimpleAdjective("d_efessus", "d_efessa", "d_efessum", "d_efess", Declension.FirstAndSecond), "exhausted");
		register(new SimpleAdverb("iterum"), "again");
		register(new SimpleAdverb("nunc"), "now");
		register(new SimpleAdjective("laetus", "laeta", "laetum", "laet", Declension.FirstAndSecond), "happy");
		register(new SimpleAdjective("salvus", "salva", "salvum", "salv", Declension.FirstAndSecond), "safe");
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
		register(new SimpleVerb("d", "ded", "dat", Conjugation.First, Valency.AccusativeDative), "give");
		register(new SimpleVerb("am", "amav", "amat", Conjugation.First, Valency.Accusative), "love");
	}
	
	// subjunctions
	static {
		register(new SimpleSubjunction("an", SubjunctionType.Question), "ob");
		register(new SimpleSubjunction("antequam", SubjunctionType.Indicative), "bevor");
		register(new SimpleSubjunction("antequam", SubjunctionType.Subjunctive), "bevor");
		register(new SimpleSubjunction("atque", SubjunctionType.Indicative), "wie");
		register(new SimpleSubjunction("ac", SubjunctionType.Indicative), "wie");
		register(new SimpleSubjunction("cum", SubjunctionType.Indicative), "als");
		register(new SimpleSubjunction("cum", SubjunctionType.Subjunctive), "weil");
		register(new SimpleSubjunction("cur", SubjunctionType.Question), "warum");
		register(new SimpleSubjunction("donec", SubjunctionType.Indicative), "bis dann");
		register(new SimpleSubjunction("dum", SubjunctionType.Indicative), "solange");
		register(new SimpleSubjunction("dum", SubjunctionType.Subjunctive), "solange");
		register(new SimpleSubjunction("etiamsi", SubjunctionType.Indicative), "obwohl");
		register(new SimpleSubjunction("etiamsi", SubjunctionType.Subjunctive), "obwohl");
		register(new SimpleSubjunction("etsi", SubjunctionType.Indicative), "obwohl");
		register(new SimpleSubjunction("etsi", SubjunctionType.Subjunctive), "obwohl");
		register(new SimpleSubjunction("ne", SubjunctionType.Subjunctive), "dass nicht");
		register(new SimpleSubjunction("nisi", SubjunctionType.Indicative), "wenn nicht");
		register(new SimpleSubjunction("nisi", SubjunctionType.Subjunctive), "wenn nicht");
	}
	
	static {
		System.out.println("Initialized latin registry with " + INSTANCE.getSize() + " entries");
	}
}
