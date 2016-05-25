package com.zettelnet.latin.form;

public enum Voice implements FormProperty {

	Active("Act"), Passive("Pas");

	public static final FormPropertyType<Voice> TYPE = new FormPropertyType<>("Voice", values());
	
	private final String shortName;

	private Voice(String shortName) {
		this.shortName = shortName;
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
	public FormPropertyType<Voice> getType() {
		return TYPE;
	}
}
