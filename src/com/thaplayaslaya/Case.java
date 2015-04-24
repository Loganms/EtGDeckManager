package com.thaplayaslaya;

import java.util.ArrayList;

public class Case {
	
	private String preferredDeckImageLocation;
	private String preferredDeckImageLocationMod;
	private ArrayList<DeckBinder> deckBinders = new ArrayList<DeckBinder>();
	
	public Case() {

	}

	public void addDeckBinder(DeckBinder deckBinder) {
		this.deckBinders.add(deckBinder);
	}

	public void removeDeckBinder(DeckBinder deckBinder) {
		deckBinders.remove(deckBinder);
		deckBinder.delete(deckBinder);
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
		return null;
	}

	public boolean containsDeckBinder(String name) {
		for (DeckBinder db : this.getDeckBinders()) {
			if (db.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	// This is only called when user wants to make a new deck binder.
	// Walks user through the process with prompts and validation.
	public void addNewDeckBinder() {
		new CustomDialog(DeckManager.getDeckManagerGUI(), OperationType.ADD_NEW_DECKBINDER, null);
	}

	public String getPreferredDeckImageLocation() {
		if(DeckManagerMenuBar.validateLocation(preferredDeckImageLocation)){
			return preferredDeckImageLocation;
		}
		return "TOP";
	}

	public String getPreferredDeckImageLocationMod() {
		if(DeckManagerMenuBar.validateLocationMod(preferredDeckImageLocationMod)){
			return preferredDeckImageLocationMod;
		}
		return "CENTER";
	}
	
	public void prepareForSave() {
		preferredDeckImageLocation = DeckManager.getDeckManagerGUI().getPreferredDeckImageLocation();
		preferredDeckImageLocationMod = DeckManager.getDeckManagerGUI().getPreferredDeckImageLocationMod();
	}

}
