package com.zettelnet.latin.derivation;

import com.zettelnet.latin.form.Form;

public interface Derivation {

	DerivationType getType();
	
	Form getForm();
}
