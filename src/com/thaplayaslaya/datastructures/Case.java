package com.thaplayaslaya.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.gui.DeckBinderAddDialog;
import com.thaplayaslaya.gui.DeckManagerGUI;
import com.thaplayaslaya.gui.DeckManagerMenuBar;
import com.thaplayaslaya.gui.DeckManagerMenuBar.Orientation;

public class Case {

	public String VERSION_ID = DeckManager.VERSION_ID;
	private String preferredDeckImageLocation;
	private String preferredDeckImageLocationMod;
	private ArrayList<DeckBinder> deckBinders = new ArrayList<DeckBinder>();
	private Map<String, ArrayList<Deck>> customFalseGodCounters = new HashMap<String, ArrayList<Deck>>();

	public Case() {
		preferredDeckImageLocation = Orientation.TOP.name();
		preferredDeckImageLocationMod = Orientation.CENTER.name();
	}

	public Map<String, ArrayList<Deck>> getFGCounterMap() {
		if (customFalseGodCounters.isEmpty()) {
			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Your save data resulted in an empty field.\nNo custom-saved False God counters could be acquired.\nThis is likely due to updating from a version below v1.0.2\nThe program will now update the necessary fields.",
							"Load Error", JOptionPane.ERROR_MESSAGE);
			initializeNewFGCounterMap();
		}
		return customFalseGodCounters;
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
		// new CustomDialog(DeckManager.getDeckManagerGUI(),
		// OperationType.ADD_NEW_DECKBINDER, null);
		new DeckBinderAddDialog();
	}

	public void initializeNewFGCounterMap() {
		for (FalseGod fg : FalseGod.values()) {
			customFalseGodCounters.put(fg.name(), new ArrayList<Deck>());
		}
	}

	public ArrayList<Deck> getFGCounterDeckList(String name) {
		return getFGCounterMap().get(name);
	}

	public void addFGCounterDeck(String nameofFG, Deck counter) {
		getFGCounterMap().get(nameofFG).add(counter);
	}

	public String getPreferredDeckImageLocation() {
		if (DeckManagerMenuBar.validateLocation(preferredDeckImageLocation)) {
			return preferredDeckImageLocation;
		} else {
			preferredDeckImageLocation = Orientation.TOP.name();
			return preferredDeckImageLocation;
		}
	}

	public String getPreferredDeckImageLocationMod() {
		if (DeckManagerMenuBar.validateLocationMod(preferredDeckImageLocationMod)) {
			return preferredDeckImageLocationMod;
		} else {
			preferredDeckImageLocationMod = Orientation.CENTER.name();
			return preferredDeckImageLocationMod;
		}
	}

	public void prepareForSave() {
		DeckManagerGUI dmgui = DeckManager.getDeckManagerGUI();
		if (DeckManagerMenuBar.validateLocation(dmgui.getPreferredDeckImageLocation())) {
			preferredDeckImageLocation = DeckManager.getDeckManagerGUI().getPreferredDeckImageLocation();

		} else {
			preferredDeckImageLocation = Orientation.TOP.name();
		}
		if (DeckManagerMenuBar.validateLocationMod(dmgui.getPreferredDeckImageLocationMod())) {
			preferredDeckImageLocationMod = DeckManager.getDeckManagerGUI().getPreferredDeckImageLocationMod();

		} else {
			preferredDeckImageLocationMod = Orientation.CENTER.name();
		}
	}

}
