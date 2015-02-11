package com.thaplayaslaya;

import java.util.ArrayList;

public class Case {

	private ArrayList<DeckBinder> deckBinders;

	public Case() {

	}

	public void addDeckBinder(DeckBinder deckBinder) {
		this.deckBinders.add(deckBinder);
	}

	public void addDeck(Deck deck, DeckBinder deckBinder) {
		loop: for (DeckBinder db : this.deckBinders) {
			if (db.equals(deckBinder)) {
				db.addDeck(deck);
				break loop;
			}
		}
	}

	public ArrayList<DeckBinder> getDeckBinders() {
		return deckBinders;
	}

	public DeckBinder getDeckBinder(String name) {
		for (DeckBinder db : this.deckBinders) {
			if (db.getName().equals(name)) {
				return db;
			}
		}
		System.out.println("returning null from getDeckBinder(String name)");
		return null;
	}

	public void setDeckBinders(ArrayList<DeckBinder> deckBinders) {
		this.deckBinders = deckBinders;
	}

	public boolean containsDeckBinder(String name) {
		for (DeckBinder db : this.getDeckBinders()) {
			if (db.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	//This is only called when user wants to make a new deck binder.
	//Walks user through the process with prompts and validation.
	public void addNewDeckBinder() {
		CustomDialog cd = new CustomDialog(null, 1);
		cd.setVisible(true);
	}	
}

