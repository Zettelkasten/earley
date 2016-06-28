package com.zettelnet.earley.translate;

import java.util.Set;

import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;

public interface TranslationSet<T, P extends Parameter, U, Q extends Parameter> {

	Set<Translation<T, P, U, Q>> getTranslations(Production<T, P> production);
}
