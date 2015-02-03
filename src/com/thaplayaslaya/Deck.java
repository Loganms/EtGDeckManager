package com.thaplayaslaya;

public class Deck {
	
	//public static final Deck DEFAULT;
	private String name;
	private String importCode;
	
	public Deck() {
		name = "New Deck";
	}

	public String getName() {
		return name;
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
	
	public String toString() {
		return this.name;
	}
}
