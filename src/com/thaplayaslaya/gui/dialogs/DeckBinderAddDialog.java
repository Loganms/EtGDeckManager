package com.thaplayaslaya.gui.dialogs;

import javax.swing.JOptionPane;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Case;
import com.thaplayaslaya.datastructures.DeckBinder;

public class DeckBinderAddDialog extends DeckBinderDialog {

	private static final long serialVersionUID = -5337417527419850619L;

	private DeckBinder originalDeckBinder;

	public DeckBinderAddDialog() {
		super(DeckManager.getDeckManagerGUI(), "Edit Deck Binder", true);
		this.originalDeckBinder = new DeckBinder("");
		this.newDeckBinder = originalDeckBinder.copy();
		this.deckBinderPanel1 = newDeckBinder.getDBP();
		this.newComboBox = deckBinderPanel1.getComboBox();
		initComponents();
		setLocation(DeckManager.getDeckManagerGUI().getSmartExternalWindowLocation(this));
		setVisible(true);
	}

	@Override
	protected boolean validateDone() {
		boolean b = false;

		if (1 > jTextField1.getText().length()) {
			JOptionPane.showMessageDialog(DeckBinderAddDialog.this, "The name field is blank. Please enter a name.", "Try again",
					JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}
		Case c = DeckManager.getCase();
		if (c.containsDeckBinder(jTextField1.getText())) {
			JOptionPane.showMessageDialog(this, "Sorry, \"" + jTextField1.getText() + "\" " + "already exists as a deck binder.\n"
					+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}

		b = true;
		return b;
	}

	@Override
	protected void doneAction() {
		originalDeckBinder.setName(jTextField1.getText());
		originalDeckBinder.setDecks(listmodel1);
		originalDeckBinder.setStyle(newDeckBinder.getStyle());
		DeckManager.getDeckManagerGUI().getCasePanel().add(DeckManager.getDeckManagerGUI().getDeckBinderPanels().getLast());
		DeckManager.getDeckManagerGUI().getCasePanel().revalidate();
		dispose();
	}

}
