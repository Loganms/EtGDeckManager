package com.thaplayaslaya;

public class FGCounterDeck extends Deck {

	private String falseGod;

	public FGCounterDeck() {
		falseGod = null;
	}

	// I don't think I like this constructor
	// Would rather use the FalseGod constructor
	// Seems prone to errors.
	public FGCounterDeck(String falseGod) {
		this.falseGod = falseGod;
	}

	public FGCounterDeck(FalseGod falseGod) {
		this.falseGod = falseGod.name();
	}

	public String getFalseGod() {
		return this.falseGod;
	}

}
