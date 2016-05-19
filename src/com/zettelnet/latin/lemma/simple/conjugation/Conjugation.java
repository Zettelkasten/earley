package com.zettelnet.latin.lemma.simple.conjugation;

import com.zettelnet.latin.derivation.DerivationType;
import com.zettelnet.latin.derivation.DistributingDerivationProvider;
import com.zettelnet.latin.lemma.CombinedProvider;
import com.zettelnet.latin.lemma.SimpleCombinedProvider;
import com.zettelnet.latin.lemma.simple.conjugation.infinitive.FirstInfinitiveConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.infinitive.FourthInfinitiveConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.infinitive.SecondInfinitiveConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.infinitive.ThirdIStemInfinitiveConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.infinitive.ThirdInfinitiveConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.participle.FirstParticipleConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.participle.FourthParticipleConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.participle.SecondParticipleConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.participle.ThirdIStemParticipleConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.participle.ThirdParticipleConjugation;
import com.zettelnet.latin.lemma.simple.conjugation.supine.SupineConjugation;

public final class Conjugation {

	private Conjugation() {
	}

	public static final CombinedProvider<ConjugableLemma> First = new SimpleCombinedProvider<>(new FirstConjugation(), new DistributingDerivationProvider<ConjugableLemma>()
			.addProvider(DerivationType.Participle, new FirstParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new FirstInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
	public static final CombinedProvider<ConjugableLemma> Second = new SimpleCombinedProvider<>(new SecondConjugation(), new DistributingDerivationProvider<ConjugableLemma>()
			.addProvider(DerivationType.Participle, new SecondParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new SecondInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
	public static final CombinedProvider<ConjugableLemma> Third = new SimpleCombinedProvider<>(new ThirdConjugation(), new DistributingDerivationProvider<ConjugableLemma>()
			.addProvider(DerivationType.Participle, new ThirdParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new ThirdInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
	public static final CombinedProvider<ConjugableLemma> ThirdIStem = new SimpleCombinedProvider<>(new ThirdIStemConjugation(), new DistributingDerivationProvider<ConjugableLemma>()
			.addProvider(DerivationType.Participle, new ThirdIStemParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new ThirdIStemInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
	public static final CombinedProvider<ConjugableLemma> Fourth = new SimpleCombinedProvider<>(new FourthConjugation(), new DistributingDerivationProvider<ConjugableLemma>()
			.addProvider(DerivationType.Participle, new FourthParticipleConjugation())
			.addProvider(new DerivationType[] { DerivationType.Infinitive, DerivationType.Gerund }, new FourthInfinitiveConjugation())
			.addProvider(DerivationType.Supine, new SupineConjugation()));
}
