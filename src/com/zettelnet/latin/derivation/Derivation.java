package com.zettelnet.latin.derivation;

import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;

public interface Derivation {

	public static Derivation withValues(DerivationType type, FormProperty... formProperties) {
		return SimpleDerivation.withValues(type, formProperties);
	}

	DerivationType getType();
	
	Form getForm();

}
