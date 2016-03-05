package com.zettelnet.earley.param.property;

import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.test.latin.Determination;
import com.zettelnet.earley.test.latin.LemmaTerminal;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;

public class PropertyFormParameterizer implements TokenParameterizer<Token, PropertyParameter<FormProperty>> {

	private final PropertyParameterManager<FormProperty> manager;

	public PropertyFormParameterizer(final PropertyParameterManager<FormProperty> manager) {
		this.manager = manager;
	}

	@Override
	public Collection<PropertyParameter<FormProperty>> getTokenParameters(Token token, Terminal<Token> terminal) {
		Collection<PropertyParameter<FormProperty>> parameters = new ArrayList<>();

		for (Determination determination : token.getDeterminations()) {
			if (terminal instanceof LemmaTerminal) {
				LemmaTerminal lexemeTerminal = (LemmaTerminal) terminal;
				if (!lexemeTerminal.isCompatibleWith(determination)) {
					continue;
				}
			}

			Form form = determination.getForm();
			PropertyParameter<FormProperty> parameter = manager.makeParameter(form);
			parameters.add(parameter);
		}

		return parameters;
	}
}
