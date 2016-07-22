package com.zettelnet.latin.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.token.TokenFactory;
import com.zettelnet.latin.form.Form;
import com.zettelnet.latin.lemma.property.Meaning;
import com.zettelnet.latin.param.FormParameter;

public class LatinTokenFactory implements TokenFactory<Token, FormParameter> {

	@Override
	public Collection<Token> makeToken(Terminal<Token> symbol, FormParameter parameter) {
		Collection<Token> tokens = new ArrayList<>();
		Form form = parameter.toForm();
		// TODO untested
		Set<Meaning> meaningSet = parameter.getProperty(Meaning.TYPE);
		for (Meaning meaning : meaningSet) {
			for (String value : meaning.getLemma().getForm(form)) {
				Determination determination = new Determination(meaning.getLemma(), form);
				tokens.add(new Token(value, determination));
			}
		}
		return tokens;
	}
}
