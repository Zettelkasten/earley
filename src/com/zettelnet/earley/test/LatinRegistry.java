package com.zettelnet.earley.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zettelnet.german.form.GermanGenus;
import com.zettelnet.german.lemma.GermanLemma;
import com.zettelnet.german.lemma.property.GermanParticipleAuxiliary;
import com.zettelnet.german.lemma.simple.SimpleGermanAdjective;
import com.zettelnet.german.lemma.simple.SimpleGermanAdverb;
import com.zettelnet.german.lemma.simple.SimpleGermanNoun;
import com.zettelnet.german.lemma.simple.SimpleGermanVerb;
import com.zettelnet.german.lemma.simple.conjugation.GermanConjugation;
import com.zettelnet.german.lemma.simple.conjugation.GermanConjugationStem;
import com.zettelnet.german.lemma.simple.declension.GermanDeclension;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.property.Finiteness;
import com.zettelnet.latin.lemma.property.Valency;
import com.zettelnet.latin.lemma.simple.SimpleAdjective;
import com.zettelnet.latin.lemma.simple.SimpleAdverb;
import com.zettelnet.latin.lemma.simple.SimpleConjunction;
import com.zettelnet.latin.lemma.simple.SimpleNoun;
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
				new SimpleGermanVerb("singen", GermanConjugationStem.makeCollectionMap("sing", "sang", "gesungen", "sing", "säng", "sing"), GermanConjugation.Strong, GermanParticipleAuxiliary.ToHave));
		register(new SimpleVerb("cant", "cant_av", "cantat", Conjugation.First, Valency.Accusative),
				new SimpleGermanVerb("singen", GermanConjugationStem.makeCollectionMap("sing", "sang", "gesungen", "sing", "säng", "sing"), GermanConjugation.Strong, GermanParticipleAuxiliary.ToHave));
		register(new SimpleVerb("r_id", "r_is", "r_is", Conjugation.Second, Valency.Single),
				new SimpleGermanVerb("lachen", "lach", "lacht", "gelacht", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
		register(new SimpleConjunction("et"), null); // TODO
		register(new SimpleVerb("cl_am", "cl_am_av", "cl_am_at", Conjugation.First, Valency.Single),
				new SimpleGermanVerb("rufen", "ruf", "rief", "gerufen", GermanConjugation.Strong, GermanParticipleAuxiliary.ToHave));
		register(new SimpleVerb("tac", "tacu", "tacit", Conjugation.Second, Valency.Single),
				new SimpleGermanVerb("schweigen", "schweig", "schwieg", "geschwiegen", GermanConjugation.Strong, GermanParticipleAuxiliary.ToHave));
		register(new SimpleAdverb("autem"),
				new SimpleGermanAdverb("aber"));
		// TODO no passive
		register(new SimpleVerb("saev", "saevi", "saev_it", Conjugation.Fourth, Valency.Single), null); // TODO:
																										// wütend
																										// sein
		register(new SimpleConjunction("atque"), null); // TODO: und
		register(new SimpleConjunction("ac"), null); // TODO: und
		register(new SimpleAdverb("n_on"),
				new SimpleGermanAdverb("nicht"));
		register(new SimpleAdverb("iam"),
				new SimpleGermanAdverb("schon"));
		register(new SimpleVerb("i_urg", "i_urg_av", "i_urg_at", Conjugation.First, Valency.Single),
				new SimpleGermanVerb("streiten", "streit", "stritt", "gestritten", GermanConjugation.Strong, GermanParticipleAuxiliary.ToHave));
		// TODO impersonal in the passive
		register(new SimpleVerb("ven", "v_en", "vent", Conjugation.Fourth, Valency.Single),
				new SimpleGermanVerb("kommen", GermanConjugationStem.makeCollectionMap("komm", "kam", "gekommen", "komm", "käm", "komm"), GermanConjugation.Strong, GermanParticipleAuxiliary.ToBe));
		// TODO irregular short imperative
		register(new SimpleVerb("d_ic", "d_ix", "dict", Conjugation.Third, Valency.Accusative),
				new SimpleGermanVerb("sagen", "sag", "sagt", "gesagt", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
		// TODO quis?
		register(new SimpleConjunction("sed"), null); // TODO: aber, sondern
		// TODO irregular short imperative
		register(new SimpleVerb("maled_ic", "maled_ix", "maledict", Conjugation.Third, Valency.Single),
				new SimpleGermanVerb("lästern", "läster", "lästert", "gelästert", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
		register(new SimpleVerb("maled_ic", "maled_ix", "maledict", Conjugation.Third, Valency.Accusative),
				new SimpleGermanVerb("beleidigen", "beleidig", "beleidigt", "beleidigt", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave)); // TODO:
																																						// should
																																						// be
																																						// composition
																																						// with
																																						// prefix
		register(new SimpleAdverb("semper"),
				new SimpleGermanAdverb("immer"));
		register(new SimpleAdverb("deinde"),
				new SimpleGermanAdverb("darauf"));
		// TODO decet
		register(new SimpleConjunction("neque"), null); // TODO: und nicht
		register(new SimpleAdverb("tandem"),
				new SimpleGermanAdverb("endlich"));
		register(new SimpleVerb("l_ud", "l_us", "l_us", Conjugation.Third, Valency.Accusative),
				new SimpleGermanVerb("spielen", "spiel", "spielt", "gespielt", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
		register(new SimpleVerb("iuv", "i_uv", "i_ut", Conjugation.First, Valency.Single), null); // TODO:
																									// Spaß
																									// machen

		// extra
		register(new SimpleVerb("ulul", "ulul_av", "ulul_at", Conjugation.First, Valency.Single),
				new SimpleGermanVerb("heulen", "heul", "heult", "geheult", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
	}

	// Ostia Altera T2
	static {
		// TODO ubi?
		// TODO est
		Lemma sum = new SimpleVerb("sum", "fu", "fu", Conjugation.First, Valency.Copula);
		INSTANCE.register("est", new Determination(sum, Person.First, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active, Finiteness.Finite, Valency.Copula));

		register(new SimpleNoun("dominus", "domin", Declension.Second, Genus.Masculine),
				new SimpleGermanNoun("Herr", "Herren", "Herren", GermanDeclension.Weak, GermanGenus.Masculine));
		register(new SimpleNoun("vilicus", "vilic", Declension.Second, Genus.Masculine),
				new SimpleGermanNoun("Verwalter", "Verwalters", "Verwalter", GermanDeclension.Strong, GermanGenus.Masculine));
		register(new SimpleNoun("servus", "serv", Declension.Second, Genus.Masculine),
				new SimpleGermanNoun("Sklave", "Sklaven", "Sklaven", GermanDeclension.Weak, GermanGenus.Masculine));
		register(new SimpleAdverb("tum"), null); // TODO: dann
		register(new SimpleNoun("caelum", "cael", Declension.Second, Genus.Neuter),
				new SimpleGermanNoun("Himmel", "Himmels", "Himmel", GermanDeclension.Strong, GermanGenus.Masculine));
		register(new SimpleAdjective("obsc_urus", "obsc_ura", "obsc_urum", "obsc_ur", Declension.FirstAndSecond),
				new SimpleGermanAdjective("dunkel", GermanDeclension.Adjective));
		
		// TODO -que
		register(new SimpleNoun("serva", "serv", Declension.First, Genus.Feminine),
				new SimpleGermanNoun("Sklavin", "Sklavin", "Sklavinnen", GermanDeclension.Weak, GermanGenus.Feminine));
		// TODO c_ur?
		register(new SimpleVerb("lab_or", "lab_or_av", "lab_or_at", Conjugation.First, Valency.Single),
				new SimpleGermanVerb("arbeiten", "arbeit", "arbeitet", "gearbeitet", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
		register(new SimpleAdverb("subit_o"), null); // TODO: plötzlich
		register(new SimpleVerb("interrog", "interrog_av", "interrog_at", Conjugation.First),
				new SimpleGermanVerb("fragen", "frag", "fragt", "gefragt", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
		register(new SimpleNoun("plaustrum", "plaustr", Declension.Second, Genus.Neuter),
				new SimpleGermanNoun("Karren", "Karrens", "Karren", GermanDeclension.Weak, GermanGenus.Masculine));
		register(new SimpleNoun("fr_umentum", "fr_ument", Declension.Second, Genus.Neuter),
				new SimpleGermanNoun("Getreide", "Getreides", "Getreide", GermanDeclension.Weak, GermanGenus.Neuter)); // TODO:
																														// no
																														// plural
		// TODO enim
		register(new SimpleAdjective("m_aturus", "m_atura", "m_aturum", "m_atur", Declension.FirstAndSecond),
				new SimpleGermanAdjective("reif", GermanDeclension.Adjective));
		register(new SimpleAdverb("quoque"),
				new SimpleGermanAdverb("auch"));
		register(new SimpleNoun("domina", "domin", Declension.First, Genus.Feminine),
				new SimpleGermanNoun("Herrin", "Herrin", "Herinnen", GermanDeclension.Weak, GermanGenus.Feminine));
		register(new SimpleNoun("puella", "puell", Declension.First, Genus.Feminine),
				new SimpleGermanNoun("Mädchen", "Mädchens", "Mädchen", GermanDeclension.Weak, GermanGenus.Neuter));
		// TODO ecce!
		register(new SimpleAdverb("etiam"),
				new SimpleGermanAdverb("auch"));
		register(new SimpleAdverb("imm_o"), null); // TODO im Gegenteil
		register(new SimpleAdverb("v_er_o"),
				new SimpleGermanAdverb("wirklich"));
		register(new SimpleAdjective("c_unctus", "c_uncta", "c_unctum", "c_unct", Declension.FirstAndSecond),
				new SimpleGermanAdjective("alle", GermanDeclension.Adjective));
		// TODO adsum
		register(new SimpleAdjective("onustus", "onusta", "onustum", "onust", Declension.FirstAndSecond), 
				new SimpleGermanAdjective("beladen", GermanDeclension.Adjective));
		register(new SimpleAdjective("d_efessus", "d_efessa", "d_efessum", "d_efess", Declension.FirstAndSecond),
				new SimpleGermanAdjective("erschöpft", GermanDeclension.Adjective));
		register(new SimpleAdverb("iterum"),
				new SimpleGermanAdverb("wiederholt"));
		register(new SimpleAdverb("nunc"),
				new SimpleGermanAdverb("jetzt"));
		register(new SimpleAdjective("laetus", "laeta", "laetum", "laet", Declension.FirstAndSecond),
				new SimpleGermanAdjective("fröhlich", GermanDeclension.Adjective));
		register(new SimpleAdjective("salvus", "salva", "salvum", "salv", Declension.FirstAndSecond),
				new SimpleGermanAdjective("heil", GermanDeclension.Adjective));
	}

	// Names
	static {
		// TODO: German translations

		// T1
		register(new SimpleNoun("Domitilla", "Domitill", Declension.First, Genus.Feminine), null);
		register(new SimpleNoun("L_ucius", "L_uci", Declension.Second, Genus.Masculine), null);
		register(new SimpleNoun("Tertia", "Terti", Declension.First, Genus.Feminine), null);
		register(new SimpleNoun("Publius", "Publi", Declension.Second, Genus.Masculine), null);
		register(new SimpleNoun("Polybius", "Polybi", Declension.Second, Genus.Masculine), null);
		// T2
		register(new SimpleNoun("Pomp_onius", "Pomp_oni", Declension.Second, Genus.Masculine), null);
		register(new SimpleNoun("M_arcellus", "M_arcell", Declension.Second, Genus.Masculine), null);
		register(new SimpleNoun("Sel_enus", "Sel_en", Declension.Second, Genus.Masculine), null);
	}

	// additionals
	static {
		register(new SimpleNoun("carmen", "carmin", Declension.Third, Genus.Neuter),
				new SimpleGermanNoun("Lied", "Lieds", "Lieder", GermanDeclension.Strong, GermanGenus.Neuter));
		register(new SimpleVerb("d", "ded", "dat", Conjugation.First, Valency.AccusativeDative),
				new SimpleGermanVerb("geben", GermanConjugationStem.makeCollectionMap("geben", "geb", "gab", "gibst", "gäb", "gib"), GermanConjugation.Strong, GermanParticipleAuxiliary.ToBe));
		register(new SimpleVerb("am", "amav", "amat", Conjugation.First, Valency.Accusative),
				new SimpleGermanVerb("lieben", "lieb", "liebt", "geliebt", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
	}

	// subjunctions
	static {
		// register(new SimpleSubjunction("an", SubjunctionType.Question),
		// "ob");
		// register(new SimpleSubjunction("antequam",
		// SubjunctionType.Indicative), "bevor");
		// register(new SimpleSubjunction("antequam",
		// SubjunctionType.Subjunctive), "bevor");
		// register(new SimpleSubjunction("atque", SubjunctionType.Indicative),
		// "wie");
		// register(new SimpleSubjunction("ac", SubjunctionType.Indicative),
		// "wie");
		// register(new SimpleSubjunction("cum", SubjunctionType.Indicative),
		// "als");
		// register(new SimpleSubjunction("cum", SubjunctionType.Subjunctive),
		// "weil");
		// register(new SimpleSubjunction("cur", SubjunctionType.Question),
		// "warum");
		// register(new SimpleSubjunction("donec", SubjunctionType.Indicative),
		// "bis dann");
		// register(new SimpleSubjunction("dum", SubjunctionType.Indicative),
		// "solange");
		// register(new SimpleSubjunction("dum", SubjunctionType.Subjunctive),
		// "solange");
		// register(new SimpleSubjunction("etiamsi",
		// SubjunctionType.Indicative), "obwohl");
		// register(new SimpleSubjunction("etiamsi",
		// SubjunctionType.Subjunctive), "obwohl");
		// register(new SimpleSubjunction("etsi", SubjunctionType.Indicative),
		// "obwohl");
		// register(new SimpleSubjunction("etsi", SubjunctionType.Subjunctive),
		// "obwohl");
		// register(new SimpleSubjunction("ne", SubjunctionType.Subjunctive),
		// "dass nicht");
		// register(new SimpleSubjunction("nisi", SubjunctionType.Indicative),
		// "wenn nicht");
		// register(new SimpleSubjunction("nisi", SubjunctionType.Subjunctive),
		// "wenn nicht");
	}

	static {
		// extra text
		register(new SimpleVerb("sc_i", "sc_iv", "sc_itum", Conjugation.Fourth, Valency.Accusative),
				new SimpleGermanVerb("wissen", "wei�", "wusst", "gewusst", GermanConjugation.Strong, GermanParticipleAuxiliary.ToHave));

		register(new SimpleNoun("Marcus", "Marc", Declension.Second, Genus.Masculine),
				new SimpleGermanNoun("Markus", "Markus", null, GermanDeclension.Weak, GermanGenus.Masculine));

		register(new SimpleVerb("sper", "sper_av", "sperat", Conjugation.First, Valency.Accusative),
				new SimpleGermanVerb("hoffen", "hoff", "hofft", "gehofft", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
		
		register(new SimpleNoun("medicus", "medic", Declension.Second, Genus.Masculine),
				new SimpleGermanNoun("Arzt", "Arzts", "�rzte", GermanDeclension.Strong, GermanGenus.Masculine));

		register(new SimpleNoun("amica", "amic", Declension.First, Genus.Feminine),
				new SimpleGermanNoun("Freundin", "Freundin", "Freundinnen", GermanDeclension.Weak, GermanGenus.Feminine));
		
		// TODO translation
		register(new SimpleAdjective("aegrus", "aegra", "aegrum", "aegr", Declension.FirstAndSecond),
				new SimpleGermanAdjective("krank", GermanDeclension.Adjective));
		register(new SimpleAdjective("pulcher", "pulchra", "pulchrum", "pulchr", Declension.FirstAndSecond),
				new SimpleGermanAdjective("schön", GermanDeclension.Adjective));
		
		register(new SimpleVerb("serv", "serv_av", "serv_at", Conjugation.First, Valency.Single),
				new SimpleGermanVerb("retten", "rett", "rettet", "gerettet", GermanConjugation.Weak, GermanParticipleAuxiliary.ToHave));
	}

	static {
		System.out.println("Initialized latin registry with " + INSTANCE.getSize() + " entries");
	}
}
