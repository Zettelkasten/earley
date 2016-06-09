package com.zettelnet.latin.lemma.property;

import com.zettelnet.latin.form.Mood;

public enum SubjunctionType implements LemmaProperty {

	Question(Mood.Subjunctive, "Question"),
	Indicative(Mood.Indicative, "SubInd"),
	Subjunctive(Mood.Subjunctive, "SubSub");

	public static final Class<? extends SubjunctionType> TYPE = SubjunctionType.class;

	private final Mood mood;

	private final String shortName;

	private SubjunctionType(final Mood mood, final String shortName) {
		this.mood = mood;
		this.shortName = shortName;
	}

	public Mood getMood() {
		return mood;
	}

	@Override
	public String shortName() {
		return shortName;
	}

	@Override
	public String toString() {
		return shortName();
	}

	@Override
	public Class<? extends SubjunctionType> getType() {
		return TYPE;
	}
}
