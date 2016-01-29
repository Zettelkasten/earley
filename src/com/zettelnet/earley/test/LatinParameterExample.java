package com.zettelnet.earley.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zettelnet.earley.ChartSetPrinter;
import com.zettelnet.earley.EarleyParser;
import com.zettelnet.earley.Grammar;
import com.zettelnet.earley.GrammarParser;
import com.zettelnet.earley.NonTerminal;
import com.zettelnet.earley.ParameterizedSymbol;
import com.zettelnet.earley.ParseResult;
import com.zettelnet.earley.SimpleNonTerminal;
import com.zettelnet.earley.SimpleTerminal;
import com.zettelnet.earley.Terminal;
import com.zettelnet.earley.input.DynamicInputPositionInitializer;
import com.zettelnet.earley.param.AnyParameterExpression;
import com.zettelnet.earley.param.CopyParameterExpression;
import com.zettelnet.earley.param.ParameterExpression;
import com.zettelnet.earley.param.ParameterManager;
import com.zettelnet.earley.param.SpecificParameterExpression;
import com.zettelnet.earley.test.latin.Determination;
import com.zettelnet.earley.test.latin.FormParameter;
import com.zettelnet.earley.test.latin.FormParameterManager;
import com.zettelnet.earley.test.latin.Token;
import com.zettelnet.latin.Form;
import com.zettelnet.latin.form.Casus;
import com.zettelnet.latin.form.Genus;
import com.zettelnet.latin.form.Mood;
import com.zettelnet.latin.form.Numerus;
import com.zettelnet.latin.form.Person;
import com.zettelnet.latin.form.Tense;
import com.zettelnet.latin.form.Voice;
import com.zettelnet.latin.form.provider.FormProvider;
import com.zettelnet.latin.lemma.Lemma;

public class LatinParameterExample {

	public static class LexemeTerminal extends SimpleTerminal<Token> {

		private final Lemma.Type type;

		public LexemeTerminal(final Lemma.Type type) {
			super(type.toString());
			this.type = type;
		}

		@Override
		public boolean isCompatibleWith(Token token) {
			for (Determination determination : token.getDeterminations()) {
				if (isCompatibleWith(determination)) {
					return true;
				}
			}
			return false;
		}

		public boolean isCompatibleWith(Determination determination) {
			return determination.getLemmaType().equals(type);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		NonTerminal<Token> sentence = new SimpleNonTerminal<>("S");

		NonTerminal<Token> nounPhrase = new SimpleNonTerminal<>("NP");
		NonTerminal<Token> nounForm = new SimpleNonTerminal<>("NF");
		NonTerminal<Token> attribute = new SimpleNonTerminal<>("Attr");

		NonTerminal<Token> verbPhrase = new SimpleNonTerminal<>("VP");
		NonTerminal<Token> verbForm = new SimpleNonTerminal<>("VF");
		NonTerminal<Token> arguments = new SimpleNonTerminal<>("Args");
		NonTerminal<Token> adverbalPhrase = new SimpleNonTerminal<>("AP");

		Terminal<Token> verb = new LexemeTerminal(Lemma.Type.Verb);
		Terminal<Token> noun = new LexemeTerminal(Lemma.Type.Noun);

		ParameterManager<FormParameter> parameterManager = new FormParameterManager();
		Grammar<Token, FormParameter> grammar = new Grammar<>(sentence, parameterManager);

		ParameterExpression<Token, FormParameter> copy = new CopyParameterExpression<>(grammar, (Token token, Terminal<Token> terminal) -> {
			Collection<FormParameter> parameters = new ArrayList<>();

			for (Determination determination : token.getDeterminations()) {
				if (terminal instanceof LexemeTerminal) {
					LexemeTerminal lexemeTerminal = (LexemeTerminal) terminal;
					if (!lexemeTerminal.isCompatibleWith(determination)) {
						continue;
					}
				}

				Form form = determination.getForm();
				FormParameter parameter = new FormParameter(Form.withValues(form.getCasus(), form.getNumerus(), form.getGenus(), form.getPerson(), form.getMood(), form.getTense(), form.getVoice(), form.getComparison()));
				parameters.add(parameter);
			}

			return parameters;
		});
		ParameterExpression<Token, FormParameter> any = new AnyParameterExpression<>(parameterManager);

		// S(pi : !NullVal !Imp) -> NP(pi) VP(pi) -> TODO
		grammar.addProduction(sentence,
				new ParameterizedSymbol<>(nounPhrase, copy),
				new ParameterizedSymbol<>(verbPhrase, copy));
		// S(pi) -> VP(pi)
		grammar.addProduction(sentence,
				new ParameterizedSymbol<>(verbPhrase, copy));

		// // VP(pi) -> VF(pi) Args(pi) AP
		grammar.addProduction(
				verbPhrase,
				new ParameterizedSymbol<>(verbForm, copy),
				new ParameterizedSymbol<>(arguments, copy),
				new ParameterizedSymbol<>(adverbalPhrase, any));
		// // VF(pi) -> v(pi) -> TODO
		grammar.addProduction(
				verbForm,
				new ParameterizedSymbol<>(verb, copy));
		// Args -> epsilon -> TODO
		grammar.addProduction(
				arguments);
		// Args(pi : Kopula) -> NP(pi) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, copy));
		// Args(pi : GenVal) -> NP(Gen) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, new FormParameter(Form.nounForm(Casus.Genitive, null, null)))));
		// Args(pi : DatVal) -> NP(Dat) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, new FormParameter(Form.nounForm(Casus.Dative, null, null)))));
		// Args(pi : AkkVal) -> NP(Akk) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, new FormParameter(Form.nounForm(Casus.Accusative, null, null)))));
		// Args(pi : AkkDatVal) -> NP(Akk) NP(Dat) -> TODO
		grammar.addProduction(
				arguments,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, new FormParameter(Form.nounForm(Casus.Accusative, null, null)))),
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, new FormParameter(Form.nounForm(Casus.Dative, null, null)))));
		// AP -> epsilon
		grammar.addProduction(
				adverbalPhrase);
		// AP -> AP AP
		grammar.addProduction(
				adverbalPhrase,
				new ParameterizedSymbol<>(adverbalPhrase, any),
				new ParameterizedSymbol<>(adverbalPhrase, any));

		// NP(pi) -> NF(pi) Attr(pi)
		grammar.addProduction(
				nounPhrase,
				new ParameterizedSymbol<>(nounForm, copy),
				new ParameterizedSymbol<>(attribute, copy));
		// NF(pi) -> n(pi)
		grammar.addProduction(
				nounForm,
				new ParameterizedSymbol<>(noun, copy));
		// Attr(pi) -> epsilon
		grammar.addProduction(
				attribute);
		// Attr(pi) -> Attr(pi) Attr(pi)
		grammar.addProduction(
				attribute,
				new ParameterizedSymbol<>(attribute, copy),
				new ParameterizedSymbol<>(attribute, copy));
		// Attr(pi) -> NF(pi)
		grammar.addProduction(
				attribute,
				new ParameterizedSymbol<>(nounForm, copy));
		// Attr(pi) -> NP(Gen)
		grammar.addProduction(
				attribute,
				new ParameterizedSymbol<>(nounPhrase, new SpecificParameterExpression<>(parameterManager, new FormParameter(Form.nounForm(Casus.Genitive, null, null)))));

		GrammarParser<Token, FormParameter> parser = new EarleyParser<>(grammar, new DynamicInputPositionInitializer<>());

		// lemmas

		Lemma serva = new DummyLemma(Lemma.Type.Noun);
		Lemma servus = new DummyLemma(Lemma.Type.Noun);
		Lemma canto = new DummyLemma(Lemma.Type.Verb);

		// tokens

		List<Token> tokens = new ArrayList<>();
		tokens.add(new Token("serva", new Determination(serva, Form.nounForm(Casus.Nominative, Numerus.Singular, Genus.Feminine))));
		tokens.add(new Token("servi", new Determination(servus, Form.nounForm(Casus.Genitive, Numerus.Singular, Genus.Masculine))));
		tokens.add(new Token("cantat", new Determination(canto, Form.verbForm(Person.Third, Numerus.Singular, Tense.Present, Mood.Indicative, Voice.Active))));

		ParseResult<Token, FormParameter> result = parser.parse(tokens);

		new ChartSetPrinter<Token, FormParameter>(result.getCharts()).print(new PrintStream("E:\\temp.html"));
		System.out.println(result.getTreeForest());
	}

	public static class DummyLemma implements Lemma {

		private final Lemma.Type type;

		public DummyLemma(Lemma.Type type) {
			this.type = type;
		}

		@Override
		public String getFirstForm() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getForm(Form form) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasForm(Form form) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Map<Form, String> getForms() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Form> getAvailableForms() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FormProvider<? extends Lemma> getFormProvider() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Lemma> getDerivatives() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Lemma getDerivedFrom() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Type getType() {
			return type;
		}

	}
}
