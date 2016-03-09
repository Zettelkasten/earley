package com.zettelnet.latin.lemma;

import com.zettelnet.latin.derivation.DerivationProvider;

public interface CombinedProvider<T> extends FormProvider<T>, DerivationProvider<T> {

}
