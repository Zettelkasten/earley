package com.zettelnet.latin.morph;

import java.util.Collection;

import com.zettelnet.latin.form.Form;

public interface MorphProvider {

	Collection<String> getMorph(final Form form);

}
