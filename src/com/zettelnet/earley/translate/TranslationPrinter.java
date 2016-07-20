package com.zettelnet.earley.translate;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.zettelnet.earley.param.Parameter;
import com.zettelnet.earley.symbol.Symbol;
import com.zettelnet.earley.symbol.Terminal;
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
		out.print("<link href='src/translate.css' rel='stylesheet'>");
		// out.print("<script
		// src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		// out.print("<script src='src/charts.js'></script>");
		out.print("</head>");
		out.print("<body>");
		out.print("<div class='container'>");

		printTree(out, source);

		out.print("<div class='container footer'>");
		out.printf("<span class='generated'>Automatically generated on %s</span>", new Date());
		out.print("</div>");

		out.print("</div>");

		out.print("</body>");
		out.print("</html>");
	}

	public void printTree(PrintStream out, SyntaxTree<T, P> source) {
		out.print("<div class='tree-translation'>");

		for (SyntaxTreeVariant<T, P> sourceVariant : source.getVariants()) {
			out.printf("%s &#8658; ", sourceVariant.getRootSymbol());
			Collection<SyntaxTree<T, P>> subCalls = printTranslationTree(out, sourceVariant);
			for (SyntaxTree<T, P> subCall : subCalls) {
				printTree(out, subCall);
			}
		}

		out.print("</div>");
	}

	public Collection<SyntaxTree<T, P>> printTranslationTree(PrintStream out, SyntaxTreeVariant<T, P> source, TranslationTree<T, P, U, Q> translation) {
		Set<SyntaxTree<T, P>> subCalls = new HashSet<>();

		Iterator<TranslationTreeVariant<T, P, U, Q>> i = translation.getVariants().iterator();
		while (i.hasNext()) {
			subCalls.addAll(printTranslationTreeVariant(out, source, i.next()));
			if (i.hasNext()) {
				out.print(" / ");
			}
		}

		return subCalls;
	}

	public Collection<SyntaxTree<T, P>> printTranslationTreeVariant(PrintStream out, SyntaxTreeVariant<T, P> sourceVariant, TranslationTreeVariant<T, P, U, Q> translationVariant) {
		Symbol<T> sourceSymbol = sourceVariant.getRootSymbol();
		P sourceParameter = sourceVariant.getParameter();

		if (translationVariant.isAbstract()) {
			SyntaxTree<T, P> referenced = translationVariant.getAbstractReference().getSourceTree(sourceVariant);

			Iterator<SyntaxTreeVariant<T, P>> referencedVariants = referenced.getVariants().iterator();
			out.printf("~%s", referencedVariants.next().getRootSymbol());
			if (referencedVariants.hasNext()) {
				out.print("...");
			}

			return Arrays.asList(referenced);
		} else {
			Q parameter = translationVariant.getParameterTranslator().translateParameter(sourceParameter, sourceSymbol);
			if (translationVariant.isTerminal()) {
				Terminal<U> symbol = (Terminal<U>) translationVariant.getRootSymbol();
				Collection<U> tokens = translator.makeToken(symbol, parameter);
				if (tokens.isEmpty()) {
					out.print("[no token]");
				} else {
					Iterator<U> i = tokens.iterator();
					while (i.hasNext()) {
						out.printf("%s = <em>%s</em>", translationVariant.getRootSymbol(), i.next());
						if (i.hasNext()) {
							out.print(" / ");
						}
					}
				}

				return Collections.emptySet();
			} else {
				Set<SyntaxTree<T, P>> subCalls = new HashSet<>();

				out.printf("%s { ", sourceVariant.getRootSymbol(), translationVariant.getRootSymbol());
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

	public Collection<SyntaxTree<T, P>> printTranslationTree(PrintStream out, SyntaxTreeVariant<T, P> sourceVariant) {
		Set<SyntaxTree<T, P>> subCalls = new HashSet<>();
		
		for (TranslationTree<T, P, U, Q> translation : translator.getTranslationTrees(sourceVariant)) {
			for (TranslationTreeVariant<T, P, U, Q> translationVariant : translation.getVariants()) {
				subCalls.addAll(printTranslationTreeVariant(out, sourceVariant, translationVariant));
			}
		}
		return subCalls;
	}
}
