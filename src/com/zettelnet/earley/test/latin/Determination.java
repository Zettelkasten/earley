package com.zettelnet.earley.test.latin;

import com.zettelnet.latin.Form;
import com.zettelnet.latin.FormProperty;
import com.zettelnet.latin.lemma.Lemma;

public class Determination {

	private final Lemma lemma;
	private final Form form;

	public Determination(final Lemma lemma, final FormProperty... formProperties) {
		this(lemma, Form.withValues(formProperties));
	}
	
	public Determination(final Lemma lemma, final Form form) {
		this.lemma = lemma;
		this.form = form;
	}

	public Lemma getLemma() {
		return lemma;
	}

	public Lemma.Type getLemmaType() {
		return lemma.getType();
	}

	public Form getForm() {
		return form;
	}
}
