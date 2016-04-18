package com.zettelnet.latin.lemma.conjugation;

import com.zettelnet.latin.derivation.DerivationType;
import com.zettelnet.latin.derivation.DistributingDerivationProvider;
import com.zettelnet.latin.lemma.CombinedProvider;
import com.zettelnet.latin.lemma.SimpleCombinedProvider;
import com.zettelnet.latin.lemma.conjugation.infinitive.FirstInfinitiveConjugation;
import com.zettelnet.latin.lemma.conjugation.infinitive.FourthInfinitiveConjugation;
import com.zettelnet.latin.lemma.conjugation.infinitive.ThirdIStemInfinitiveConjugation;
import com.zettelnet.latin.lemma.conjugation.infinitive.ThirdInfinitiveConjugation;
import com.zettelnet.latin.lemma.conjugation.participle.FirstParticipleConjugation;
import com.zettelnet.latin.lemma.conjugation.participle.FourthParticipleConjugation;
import com.zettelnet.latin.lemma.conjugation.participle.SecondParticipleConjugation;
import com.zettelnet.latin.lemma.conjugation.participle.ThirdIStemParticipleConjugation;
import com.zettelnet.latin.lemma.conjugation.participle.ThirdParticipleConjugation;
import com.zettelnet.latin.lemma.conjugation.supine.SupineConjugation;
import com.zettelnet.latin.lemma.verb.Verb;

public final class Conjugation {

	private Conjugation() {
	}

	public static final CombinedProvider<Verb> First = new SimpleCombinedProvider<>(new FirstConjugation(), new DistributingDerivationProvider<Verb>()
			.addProvider(DerivationType.Participle, new FirstParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new FirstInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
	public static final CombinedProvider<Verb> Second = new SimpleCombinedProvider<>(new SecondConjugation(), new DistributingDerivationProvider<Verb>()
			.addProvider(DerivationType.Participle, new SecondParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new FirstInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
	public static final CombinedProvider<Verb> Third = new SimpleCombinedProvider<>(new ThirdConjugation(), new DistributingDerivationProvider<Verb>()
			.addProvider(DerivationType.Participle, new ThirdParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new ThirdInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
	public static final CombinedProvider<Verb> ThirdIStem = new SimpleCombinedProvider<>(new ThirdIStemConjugation(), new DistributingDerivationProvider<Verb>()
			.addProvider(DerivationType.Participle, new ThirdIStemParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new ThirdIStemInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
	public static final CombinedProvider<Verb> Fourth = new SimpleCombinedProvider<>(new FourthConjugation(), new DistributingDerivationProvider<Verb>()
			.addProvider(DerivationType.Participle, new FourthParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new FourthInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
}
