package com.zettelnet.german.derivation;

import com.zettelnet.german.form.GermanForm;
import com.zettelnet.german.form.GermanFormProperty;

public interface GermanDerivation {

	public static GermanDerivation withValues(GermanDerivationType type, GermanForm form) {
		return SimpleGermanDerivation.withValues(type, form);
	}

	public static GermanDerivation withValues(GermanDerivationType type, GermanFormProperty... formProperties) {
		return SimpleGermanDerivation.withValues(type, formProperties);
	}

	GermanDerivationType getType();

	GermanForm getForm();
}
