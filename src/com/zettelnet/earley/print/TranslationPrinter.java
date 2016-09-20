package com.zettelnet.earley.print;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.Production;
import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;
import com.zettelnet.earley.translate.Translation;
import com.zettelnet.earley.translate.TranslationSet;
import com.zettelnet.earley.translate.TranslationTree;
import com.zettelnet.earley.translate.TranslationTreeVariant;
import com.zettelnet.earley.translate.Translator;
import com.zettelnet.earley.tree.SyntaxTree;
import com.zettelnet.earley.tree.SyntaxTreeVariant;

public class TranslationPrinter<T, P extends Parameter, U, Q extends Parameter> {

	private final SyntaxTree<T, P> source;
	private final Translator<T, P, U, Q> translator;

	public TranslationPrinter(final SyntaxTree<T, P> source, final Translator<T, P, U, Q> translator) {
		this.source = source;
		this.translator = translator;
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

		printTranslationSet(out, translator.getSourceGrammar(), translator.getTranslations());
		printTree(out, source);

		out.print("<div class='container footer'>");
		out.printf("<span class='generated'>Automatically generated on %s</span>", new Date());
		out.print("</div>");

		out.print("</div>");

		out.print("</body>");
		out.print("</html>");
	}

	public void printTranslationSet(PrintStream out, Grammar<T, P> sourceGrammar, TranslationSet<T, P, U, Q> translations) {
		out.print("<table>");

		for (Production<T, P> production : sourceGrammar.getProductions()) {
			for (Translation<T, P, U, Q> translation : translations.getTranslations(production)) {
				out.print("<tr>");
				out.printf("<td>%s</td>", production);
				out.print("<td>");
				printTranslation(out, translation);
				out.print("</td>");
				out.print("</tr>");
			}
		}
		out.print("<tr><td>-</td><td>-</td></tr>");
		for (Terminal<T> terminal : sourceGrammar.getTerminals()) {
			for (Translation<T, P, U, Q> translation : translations.getTranslations(terminal)) {
				out.print("<tr>");
				out.printf("<td>%s</td>", terminal);
				out.print("<td>");
				printTranslation(out, translation);
				out.print("</td>");
				out.print("</tr>");
			}
		}
		out.print("</table>");
	}

	public void printTranslation(PrintStream out, Translation<T, P, U, Q> translation) {
		out.printf("%s &#8658; ", translation.key());
		out.print(translation);
	}

	public void printTree(PrintStream out, SyntaxTree<T, P> source) {
		out.print("<div class='tree-translation'>");

		Set<SyntaxTreeVariant<T, P>> variants = source.getVariantsSet();
		boolean useList = variants.size() > 1;

		if (useList) {
			out.print("<ol class='source-variants'>");
		}

		for (SyntaxTreeVariant<T, P> sourceVariant : variants) {
			if (useList) {
				out.print("<li>");
			}

			printSourceSymbol(out, sourceVariant.getRootSymbol(), sourceVariant.getParameter());
			out.print(" &#8658; ");
			List<SyntaxTree<T, P>> subCalls = printTranslationTree(out, sourceVariant);
			for (SyntaxTree<T, P> subCall : subCalls) {
				printTree(out, subCall);
			}

			if (useList) {
				out.print("</li>");
			}
		}

		if (useList) {
			out.print("</ol>");
		}

		out.print("</div>");
	}

	public List<SyntaxTree<T, P>> printTranslationTree(PrintStream out, SyntaxTreeVariant<T, P> source, TranslationTree<T, P, U, Q> translation) {
		List<SyntaxTree<T, P>> subCalls = new ArrayList<>();

		Iterator<TranslationTreeVariant<T, P, U, Q>> i = translation.getVariants().iterator();
		while (i.hasNext()) {
			subCalls.addAll(printTranslationTreeVariant(out, source, i.next()));
			if (i.hasNext()) {
				out.print(" / ");
			}
		}

		return subCalls;
	}

	public List<SyntaxTree<T, P>> printTranslationTreeVariant(PrintStream out, SyntaxTreeVariant<T, P> sourceVariant, TranslationTreeVariant<T, P, U, Q> translationVariant) {
		Symbol<T> sourceSymbol = sourceVariant.getRootSymbol();
		P sourceParameter = sourceVariant.getParameter();

		if (translationVariant.isAbstract()) {
			SyntaxTree<T, P> referenced = translationVariant.getAbstractReference().getSourceTree(sourceVariant);

			Iterator<SyntaxTreeVariant<T, P>> referencedVariants = referenced.getVariants().iterator();
			SyntaxTreeVariant<T, P> firstVariant = referencedVariants.next();
			out.print("~");
			printSourceSymbol(out, firstVariant.getRootSymbol());
			if (referencedVariants.hasNext()) {
				out.print("...");
			}

			return Arrays.asList(referenced);
		} else {
			Q parameter = translationVariant.getParameterTranslator().translateParameter(sourceParameter, sourceSymbol, translationVariant.getRootSymbol());
			if (translationVariant.isTerminal()) {
				Terminal<U> symbol = (Terminal<U>) translationVariant.getRootSymbol();
				Q translatedParameter = translationVariant.getParameterTranslator().translateParameter(sourceVariant.getParameter(), sourceSymbol, symbol);
				Collection<U> tokens = translator.makeToken(symbol, parameter);
				if (tokens.isEmpty()) {
					out.print("<span class='error'>[no token]</span>");
				} else {
					Iterator<U> i = tokens.iterator();
					while (i.hasNext()) {
						printTargetSymbol(out, translationVariant.getRootSymbol(), translatedParameter);
						out.printf(" = <em>%s</em>", i.next());
						if (i.hasNext()) {
							out.print(" / ");
						}
					}
				}

				return Collections.emptyList();
			} else {
				List<SyntaxTree<T, P>> subCalls = new ArrayList<>();

				Q translatedParameter = translationVariant.getParameterTranslator().translateParameter(sourceVariant.getParameter(), sourceSymbol, translationVariant.getRootSymbol());
				printTargetSymbol(out, translationVariant.getRootSymbol(), translatedParameter);
				out.print(" { ");
				List<TranslationTree<T, P, U, Q>> children = translationVariant.getChildren();

				if (children.isEmpty()) {
					out.print("&epsilon; ");
				} else {
					for (TranslationTree<T, P, U, Q> child : children) {
						subCalls.addAll(printTranslationTree(out, sourceVariant, child));
						out.print(" ");
					}
				}
				out.print("}");

				return subCalls;
			}
		}
	}

	public List<SyntaxTree<T, P>> printTranslationTree(PrintStream out, SyntaxTreeVariant<T, P> sourceVariant) {
		List<SyntaxTree<T, P>> subCalls = new ArrayList<>();

		Set<TranslationTree<T, P, U, Q>> translations = translator.getTranslationTrees(sourceVariant);
		if (translations.isEmpty()) {
			out.printf("<span class='error'>[no translation found for %s]</span>", sourceVariant.getProduction());
		} else {
			for (TranslationTree<T, P, U, Q> translation : translations) {
				for (TranslationTreeVariant<T, P, U, Q> translationVariant : translation.getVariants()) {
					subCalls.addAll(printTranslationTreeVariant(out, sourceVariant, translationVariant));
				}
			}
		}
		return subCalls;
	}

	public void printSourceSymbol(PrintStream out, Symbol<T> symbol) {
		out.printf("<span class='source-symbol %s'>%s</span>", getSymbolClass(symbol), symbol);
	}

	public void printSourceSymbol(PrintStream out, Symbol<T> symbol, P parameter) {
		out.printf("<span class='source-symbol %s'>%s(%s)</span>", getSymbolClass(symbol), symbol, parameter);
	}

	public void printTargetSymbol(PrintStream out, Symbol<U> symbol) {
		out.printf("<span class='target-symbol %s'>%s</span>", getSymbolClass(symbol), symbol);
	}

	public void printTargetSymbol(PrintStream out, Symbol<U> symbol, Q parameter) {
		out.printf("<span class='target-symbol %s'>%s(%s)</span>", getSymbolClass(symbol), symbol, parameter);
	}

	private String getSymbolClass(Symbol<?> symbol) {
		return symbol instanceof Terminal<?> ? "terminal-symbol" : "non-terminal-symbol";
	}
}
