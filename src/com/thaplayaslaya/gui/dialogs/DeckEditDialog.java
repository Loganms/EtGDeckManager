package com.thaplayaslaya.gui.dialogs;

import javax.swing.JOptionPane;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.datastructures.Style;

public class DeckEditDialog extends DeckDialog {

	private static final long serialVersionUID = -2241892616536917822L;

	private Deck originalDeck;

	public DeckEditDialog(Deck deck) {
		super(DeckManager.getDeckManagerGUI(), "Edit Deck", true);
		this.originalDeck = deck;
		this.newDeck = deck.clone();
		initComponents();
		setLocation(DeckManager.getDeckManagerGUI().getSmartExternalWindowLocation(this));
		jTextField1.selectAll();
		setVisible(true);
	}

	protected boolean validateDone() {
		boolean b = false;
		if (1 > jTextField1.getText().length()) {
			JOptionPane.showMessageDialog(DeckEditDialog.this, "The name field is blank. Please enter a name.", "Try again",
					JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}
		DeckBinder db = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder();
		if (null == db || (db.containsDeck(jTextField1.getText()) && !jTextField1.getText().equals(originalDeck.getName()))) {
			JOptionPane.showMessageDialog(this, "Sorry, \"" + jTextField1.getText() + "\" " + "already exists in this deck binder.\n"
					+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}

		b = true;
		return b;
	}

	protected void doneAction() {
		originalDeck.setName(jTextField1.getText());
		originalDeck.setStyle(newDeck.getStyle());
		originalDeck.setImportCode(newDeck.getImportCode());
		originalDeck.setNotes(newDeck.getNotes());
		Style.applyStyle(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().getDBP().getComboBox(), newDeck.getStyle());
		dispose();
	}
}
