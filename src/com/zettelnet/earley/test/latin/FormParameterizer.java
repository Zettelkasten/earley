package com.zettelnet.earley.test.latin;

import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.latin.Form;

public class FormParameterizer implements TokenParameterizer<Token, FormParameter> {

	@Override
	public Collection<FormParameter> getTokenParameters(Token token, Terminal<Token> terminal) {
		Collection<FormParameter> parameters = new ArrayList<>();

		for (Determination determination : token.getDeterminations()) {
			if (terminal instanceof LemmaTerminal) {
				LemmaTerminal lexemeTerminal = (LemmaTerminal) terminal;
				if (!lexemeTerminal.isCompatibleWith(determination)) {
					continue;
				}
			}

			Form form = determination.getForm();
			FormParameter parameter = new FormParameter(form);
			parameters.add(parameter);
		}

		return parameters;
	}
}
