package com.zettelnet.latin.token;

import java.io.PrintStream;
import java.util.Collection;

import com.zettelnet.earley.print.TokenPrinter;

public class LatinTokenPrinter implements TokenPrinter<Token> {

	public static final TokenPrinter<Token> INSTANCE = new LatinTokenPrinter();

	@Override
	public void print(final PrintStream out, final Token token) {
		Collection<Determination> determinations = token.getDeterminations();
		out.print("<tr class='token'>");
		out.printf("<td class='token-content' row-span='%s'>%s</td>", determinations.size(), token.getContent());
		out.print("<td class='token-determinations'>");
		if (!determinations.isEmpty()) {
			out.print("<ul>");
			for (Determination determination : determinations) {
				printDetermination(out, determination);
			}
			out.print("</ul>");
		} else {
			out.printf("&empty;");
		}
		out.print("</td>");
		out.print("</tr>");
	}

	public void printDetermination(final PrintStream out, final Determination determination) {
		out.printf("<li class='token-determination' title='%s'>", determination.getProperties());
		out.printf("%s of %s (%s - %s)", determination.toForm(), determination.getLemma(), determination.getLemmaType(), determination.getLemma().getProperties());
		out.print("</li>");
	}
}
