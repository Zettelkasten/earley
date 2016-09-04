package com.zettelnet.german.lemma;

import com.zettelnet.german.derivation.GermanDerivationProvider;

public interface CombinedGermanProvider<T> extends GermanFormProvider<T>, GermanDerivationProvider<T> {

}
