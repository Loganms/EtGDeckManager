package com.thaplayaslaya;
import java.util.ArrayList;

import javax.swing.JPanel;


public class DeckBinder {

	private String dBName;
	private ArrayList<Deck> decks = new ArrayList<Deck>();
	
	public DeckBinder(){
		dBName = "Default Name";
	}

	public void addDeck(Deck deck) {
		decks.add(deck);
	}
	
	public void setName(String name) {
		this.dBName = name;
	}
		
	public String toString() {
		return dBName;
	}

	public Deck getDeck(String name) {
		for (Deck d : this.decks) {
			if (d.toString().equals(name)){
				return d;
			}
		}
		return null;
		
	}
	
	public ArrayList<Deck> getDecks() {
		return decks;
	}

	public void setDecks(ArrayList<Deck> decks) {
		this.decks = decks;
	}

	public void addAsPanelTo(JPanel panel) {
		DeckBinderPanel DBP = new DeckBinderPanel(this);
		panel.add(DBP);
	}
	
	public boolean containsDeck(String name){
		for( Deck d : this.getDecks()) {
			if(d.toString().equals(name)){
				return true;
			}
		}
		return false;
	}
	
}
