package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import com.zettelnet.earley.print.TokenSetPrinter;
import com.zettelnet.earley.token.TokenScanner;
import com.zettelnet.earley.token.Tokenizer;
import com.zettelnet.earley.token.WhitespaceTokenizer;
import com.zettelnet.latin.token.LatinTokenPrinter;
import com.zettelnet.latin.token.Token;
import com.zettelnet.latin.token.morpheus.MorpheusTokenScanner;

public class MorpheusTokenScannerTest {

	public static void main(String[] args) throws FileNotFoundException {
		TokenScanner<Token> scanner = new MorpheusTokenScanner();
		Tokenizer<Token> tokenizer = new WhitespaceTokenizer<>(scanner);
		List<Token> tokens = tokenizer.tokenize("servus cantat");

		new TokenSetPrinter<>(new LatinTokenPrinter(), tokens).print(new PrintStream("tokens.html"));
	}
}
