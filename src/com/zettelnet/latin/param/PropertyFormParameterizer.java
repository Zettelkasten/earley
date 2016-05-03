package com.zettelnet.latin.param;

import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.earley.param.TokenParameterizer;
import com.zettelnet.earley.param.property.PropertyParameter;
import com.zettelnet.earley.param.property.PropertyParameterManager;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.form.FormProperty;
import com.zettelnet.latin.lemma.LemmaTerminal;
import com.zettelnet.latin.token.Determination;
import com.zettelnet.latin.token.Token;

@Deprecated
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

			Form form = determination.toForm();
			PropertyParameter<FormProperty> parameter = manager.makeParameter(form);
			parameters.add(parameter);
		}

		return parameters;
	}
}
