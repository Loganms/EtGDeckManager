package com.thaplayaslaya;

public class Deck {
	
	//private transient final Deck DEFAULT;
	private transient static String defaultDeckName = "add new deck";
	private String name;
	private String importCode;
	
	public Deck() {
		name = "[No Name Set]";
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImportCode() {
		return importCode;
	}

	public void setImportCode(String importCode) {
		this.importCode = importCode;
	}
	
	public static Deck getDefaultDeck() {
		Deck deck = new Deck();
		deck.setName(defaultDeckName);
		return deck;
	}
	
	public String toString() {
		return this.name;
	}
}
