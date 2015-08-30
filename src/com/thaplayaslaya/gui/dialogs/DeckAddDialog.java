package com.thaplayaslaya.gui.dialogs;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.datastructures.Style;

public class DeckAddDialog extends DeckDialog {

	private static final long serialVersionUID = -6612859610769438388L;

	private DeckBinder db = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder();

	public DeckAddDialog() {
		super(DeckManager.getDeckManagerGUI(), "New Deck", true);
		Style.applyStyle(db.getDBP().getComboBox(), ((Deck) db.getDBP().getComboBox().getSelectedItem()).getStyle());
		db.getDBP().transferFocus();
		this.newDeck = new Deck();
		initComponents();
		setLocation(DeckManager.getDeckManagerGUI().getSmartExternalWindowLocation(this));
		setVisible(true);
	}

	protected boolean validateDone() {
		boolean b = false;
		if (1 > jTextField1.getText().length()) {
			JOptionPane
					.showMessageDialog(DeckAddDialog.this, "The name field is blank. Please enter a name.", "Try again", JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}
		if (null == db || (db.containsDeck(jTextField1.getText()))) {
			JOptionPane.showMessageDialog(this, "Sorry, \"" + jTextField1.getText() + "\" " + "already exists in this deck binder.\n"
					+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}

		b = true;
		return b;
	}

	protected void doneAction() {
		newDeck.setName(jTextField1.getText());
		newDeck.setImportCode(importCodeTextArea.getText());
		newDeck.setNotes(deckNotesTextArea.getText());

		db.addDeck(newDeck);

		JComboBox<Deck> cb = db.getDBP().getComboBox();
		Style.applyStyle(cb, newDeck.getStyle());

		cb.setSelectedItem(newDeck);

		dispose();
	}

	protected void cancelAction() {
		db.getDeckBinderPanel().getComboBox().setSelectedIndex(0);
		// For the case where only "add new deck" is left in the DBP
		if (db.getDecks().isEmpty()) {
			db.getDeckBinderPanel().getComboBox().transferFocusUpCycle();
			db.getDeckBinderPanel().revalidate();
		}
		dispose();
	}
}