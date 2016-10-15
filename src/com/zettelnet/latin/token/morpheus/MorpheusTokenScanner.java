package com.zettelnet.latin.token.morpheus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zettelnet.earley.token.TokenScanner;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Degree;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.lemma.DummyLemma;
import com.zettelnet.latin.lemma.Lemma;
import com.zettelnet.latin.lemma.LemmaType;
import com.zettelnet.latin.token.Determination;
import com.zettelnet.latin.token.Token;

/**
 * Represents a morphological analyzer scanning tokens by retrieving the info
 * from an XML resource. By default, the analyzer from
 * <a href="http://www.perseus.tufts.edu/hopper/morph">Perseus, called
 * Morpheus</a> is used.
 * 
 * @author Zettelkasten
 * @link <a href=
 *       "http://www.perseus.tufts.edu/hopper/xmlmorph?lang=la&lookup=servus">Example
 *       XML lookup for 'servus'</a>
 * @link <a href=
 *       "https://github.com/jfinkels/hopper/blob/master/reading/src/main/java/perseus/morph/MorphCode.java">Java
 *       Implementation about Morph Codes</a>
 *
 */
public class MorpheusTokenScanner implements TokenScanner<Token> {

	private final String resource;

	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private final DocumentBuilder builder;

	public MorpheusTokenScanner() {
		this("http://www.perseus.tufts.edu/hopper/xmlmorph?lang=la&lookup=%s");
	}

	public MorpheusTokenScanner(final String resourceBlueprint) {
		this.resource = resourceBlueprint;

		try {
			this.builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Failed to construct DocumentBuilder", e);
		}
	}

	public static String strip(String content) {
		return content.replaceAll("[_\\.]", "").toLowerCase();
	}

	public String receiveResource(String content) {
		String stripped = strip(content);
		return String.format(resource, stripped);
	}

	@Override
	public Token makeToken(String content) {
		Document doc;
		try {
			doc = builder.parse(receiveResource(content));
		} catch (SAXException | IOException e) {
			throw new RuntimeException("Failed to receive and parse resource", e);
		}

		Element analyses = (Element) doc.getElementsByTagName("analyses").item(0);

		Collection<Determination> determinations = new ArrayList<>();

		for (Node analysisNode = analyses.getFirstChild(); analysisNode != null; analysisNode = analysisNode.getNextSibling()) {
			if (analysisNode instanceof Element) {
				Element analysis = (Element) analysisNode;
				String lemmaName = parseFeature(analysis, "lemma", MorpheusTokenScanner::parseLemmaName);
				LemmaType lemmaType = parseFeature(analysis, "pos", MorpheusTokenScanner::parseLemmaType);
				Lemma lemma = new DummyLemma(lemmaName, lemmaType);

				Person person = parseFeatureIfExists(analysis, "person", MorpheusTokenScanner::parsePerson);
				Numerus numerus = parseFeatureIfExists(analysis, "number", MorpheusTokenScanner::parseNumerus);
				Tense tense = parseFeatureIfExists(analysis, "tense", MorpheusTokenScanner::parseTense);
				Mood mood = parseFeatureIfExists(analysis, "mood", MorpheusTokenScanner::parseMood);
				Voice voice = parseFeatureIfExists(analysis, "voice", MorpheusTokenScanner::parseVoice);
				Genus genus = parseFeatureIfExists(analysis, "gender", MorpheusTokenScanner::parseGenus);
				Casus casus = parseFeatureIfExists(analysis, "case", MorpheusTokenScanner::parseCasus);
				Degree degree = parseFeatureIfExists(analysis, "degree", MorpheusTokenScanner::parseDegree);

				Determination determination = new Determination(lemma, person, numerus, tense, mood, voice, genus, casus, degree);

				determinations.add(determination);
			}
		}

		return new Token(content, determinations);
	}

	private <T> T parseFeature(Element analysis, String featureKey, Function<String, T> featureMap) {
		return featureMap.apply(analysis.getElementsByTagName(featureKey).item(0).getTextContent());
	}

	private <T> T parseFeatureIfExists(Element analysis, String featureKey, Function<String, T> featureMap) {
		NodeList nodes = analysis.getElementsByTagName(featureKey);
		if (nodes.getLength() < 1) {
			return null;
		} else {
			return featureMap.apply(nodes.item(0).getTextContent());
		}
	}

	private static String parseLemmaName(String lemmaRaw) {
		return lemmaRaw;
	}

	private static LemmaType parseLemmaType(String posRaw) {
		switch (posRaw) {
		case "noun":
			return LemmaType.Noun;
		case "verb":
			return LemmaType.Verb;
		case "part":
			return LemmaType.Participle;
		case "adj":
			return LemmaType.Adjective;
		case "adv":
			return LemmaType.Adverb;
		case "conj":
			return LemmaType.Conjunction;
		case "prep":
			return LemmaType.Preposition;
		case "pron":
			return LemmaType.Pronoun;
		case "numeral":
			return LemmaType.Numeral;
		case "interj":
			return LemmaType.Interjection;
		case "punc":
			// Punctuation
		case "irreg":
			// Irregular
		case "func":
			// Functional
		case "adverbial":
		case "article":
		case "exclam":
			// Exclamation
		case "partic":
		default:
			// Particle
			throw new AssertionError("Unimplemented part Of speech type " + posRaw);
		}
	}

	private static Person parsePerson(String personRaw) {
		switch (personRaw) {
		case "1st":
			return Person.First;
		case "2nd":
			return Person.Second;
		case "3rd":
			return Person.Third;
		default:
			throw new AssertionError("Unknown person " + personRaw);
		}
	}

	private static Numerus parseNumerus(String numerusRaw) {
		switch (numerusRaw) {
		case "sg":
			return Numerus.Singular;
		case "pl":
			return Numerus.Plural;
		case "dual":
			throw new AssertionError("Unimplemented numerus type " + numerusRaw);
		default:
			throw new AssertionError("Unknown numerus " + numerusRaw);
		}
	}

	private static Tense parseTense(String tenseRaw) {
		switch (tenseRaw) {
		case "pres":
			return Tense.Present;
		case "imperf":
			return Tense.Imperfect;
		case "perf":
			return Tense.Perfect;
		case "plup":
			return Tense.Pluperfect;
		case "futperf":
			return Tense.FuturePerfect;
		case "fut":
			return Tense.Future;
		case "aor":
			// Aorist
		case "pastabs":
			// Past Absolute
			throw new AssertionError("Unimplemented tense type " + tenseRaw);
		default:
			throw new AssertionError("Unknown tense " + tenseRaw);
		}
	}

	private static Mood parseMood(String moodRaw) {
		switch (moodRaw) {
		case "ind":
			return Mood.Indicative;
		case "subj":
			return Mood.Subjunctive;
		case "imperat":
			return Mood.Imperative;
		case "inf":
		case "gerundive":
		case "supine":
		case "gerund":
			// TODO
			return null;
		case "opt":
			// Optative
			throw new AssertionError("Unimplemented mood type " + moodRaw);
		default:
			throw new AssertionError("Unknown mood " + moodRaw);
		}
	}

	private static Voice parseVoice(String voiceRaw) {
		switch (voiceRaw) {
		case "act":
			return Voice.Active;
		case "pass":
			return Voice.Passive;
		case "dep":
			// Deponent - TODO?
		case "mid":
			// Middle
		case "mp":
			// Medio Passive
			throw new AssertionError("Unimplemented voice type " + voiceRaw);
		default:
			throw new AssertionError("Unknown voice " + voiceRaw);
		}
	}

	private static Genus parseGenus(String genusRaw) {
		switch (genusRaw) {
		case "masc":
			return Genus.Masculine;
		case "fem":
			return Genus.Feminine;
		case "neut":
			return Genus.Neuter;
		default:
			throw new AssertionError("Unknown genus " + genusRaw);
		}
	}

	private static Casus parseCasus(String casusRaw) {
		switch (casusRaw) {
		case "nom":
			return Casus.Nominative;
		case "gen":
			return Casus.Genitive;
		case "dat":
			return Casus.Dative;
		case "acc":
			return Casus.Accusative;
		case "abl":
			return Casus.Ablative;
		case "voc":
			return Casus.Vocative;
		case "loc":
			return Casus.Locative;
		case "ins":
			// Instrumental
			throw new AssertionError("Unimplemented casus type " + casusRaw);
		default:
			throw new AssertionError("Unknown casus " + casusRaw);
		}
	}

	private static Degree parseDegree(String degreeRaw) {
		switch (degreeRaw) {
		case "pos":
			return Degree.Positive;
		case "comp":
			return Degree.Comparative;
		case "superl":
			return Degree.Superlative;
		default:
			throw new AssertionError("Unknown degree " + degreeRaw);
		}
	}
}
