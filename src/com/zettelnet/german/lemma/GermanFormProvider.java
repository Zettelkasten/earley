package com.zettelnet.german.lemma;

import java.util.Collection;
import java.util.Map;

import com.zettelnet.german.form.GermanForm;

public interface GermanFormProvider<T> {

	Collection<String> getForm(T lemma, GermanForm form);

	boolean hasForm(T lemma, GermanForm form);

	Map<GermanForm, Collection<String>> getForms(T lemma);
}
