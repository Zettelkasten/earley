package com.zettelnet.latin.grammar;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.GrammarFactory;
import com.zettelnet.latin.param.FormParameter;
import com.zettelnet.latin.token.Token;

public class LatinGrammarFactory implements GrammarFactory<Token, FormParameter> {
	
	@Override
	public Grammar<Token, FormParameter> makeGrammar() {
		return LatinGrammar.makeGrammar();
	}
}
