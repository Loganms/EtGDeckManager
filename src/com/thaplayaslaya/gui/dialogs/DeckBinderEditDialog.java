package com.thaplayaslaya.gui.dialogs;

import javax.swing.JOptionPane;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Case;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.datastructures.Style;

public class DeckBinderEditDialog extends DeckBinderDialog {

	private static final long serialVersionUID = 8841193530064265567L;

	private DeckBinder originalDeckBinder;

	public DeckBinderEditDialog(DeckBinder deckBinder) {
		super(DeckManager.getDeckManagerGUI(), "Edit Deck Binder", true);
		this.originalDeckBinder = deckBinder;
		this.newDeckBinder = originalDeckBinder.copy();
		this.deckBinderPanel1 = originalDeckBinder.getDBP().copy();
		this.newComboBox = deckBinderPanel1.getComboBox();
		initComponents();
		setLocation(DeckManager.getDeckManagerGUI().getSmartExternalWindowLocation(this));
		setVisible(true);
	}

	protected boolean validateDone() {
		boolean b = false;

		if (1 > jTextField1.getText().length()) {
			JOptionPane.showMessageDialog(DeckBinderEditDialog.this, "The name field is blank. Please enter a name.", "Try again",
					JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}
		Case c = DeckManager.getCase();
		if (c.containsDeckBinder(jTextField1.getText()) && !jTextField1.getText().equals(originalDeckBinder.getName())) {
			JOptionPane.showMessageDialog(this, "Sorry, \"" + jTextField1.getText() + "\" " + "already exists as a deck binder.\n"
					+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}

		b = true;
		return b;
	}

	protected void doneAction() {
		originalDeckBinder.setName(jTextField1.getText());
		originalDeckBinder.setDecks(listmodel1);
		originalDeckBinder.setStyle(newDeckBinder.getStyle());
		Style.applyStyle(originalDeckBinder.getDBP().getComboBox(), originalDeckBinder.getDecks().get(0).getStyle());
		dispose();
	}
}
