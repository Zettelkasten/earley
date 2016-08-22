package com.zettelnet.earley.print;

import java.io.PrintStream;
import java.util.List;

public class TokenSetPrinter<T> {

	private final TokenPrinter<T> tokenPrinter;
	private final List<T> tokens;

	public TokenSetPrinter(final TokenPrinter<T> tokenPrinter, final List<T> tokens) {
		this.tokenPrinter = tokenPrinter;
		this.tokens = tokens;
	}

	public void print(PrintStream out) {
		out.print("<html>");
		out.print("<head>");
		out.print("<meta charset='utf-8'>");
		out.print("<meta name='viewport' content='width=device-width, initial-scale=1'>");
		out.print("<link href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css' rel='stylesheet'>");
		out.print("<link href='src/main.css' rel='stylesheet'>");
		out.print("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		out.print("<script src='src/main.js'></script>");
		out.print("</head>");
		out.print("<body>");
		out.print("<div class='container'>");

		out.print("<table class='tokens'>");
		for (T token : tokens) {
			tokenPrinter.print(out, token);
		}
		out.print("</table>");

		out.print("</div>");
		out.print("</body>");
		out.print("</html>");
	}
}
