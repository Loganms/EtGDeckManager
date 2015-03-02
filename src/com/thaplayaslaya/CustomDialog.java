package com.thaplayaslaya;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*Thank you to David Kroukamp from StackExchange
 <http://stackoverflow.com/questions/13055107/joptionpane-check-user-input-and-prevent-from-closing-until-conditions-are-met>
 for this CustomDialog example template.*/

public class CustomDialog extends JDialog implements ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = -2396181031393526824L;
	private String typedText = null;
	@SuppressWarnings("unused")
	private String typedImportCode = null;
	private JTextField nameTextField;
	private JTextArea importCodeTextArea;
	private JOptionPane optionPane;
	private String btnString1 = "Enter";
	private String btnString2 = "Cancel";
	private OperationType typeOfOperation;

	/**
	 * Returns null if the typed string was invalid; otherwise, returns the
	 * string as the user entered it.
	 */
	public String getValidatedText() {
		return typedText;
	}

	/**
	 * Creates the reusable dialog. Supplying an integer value of 0 tells
	 * CustomDialog we are adding a new deck. Supplying an integer value of 1
	 * tells CustomDialog we are adding a new deck binder.
	 */
	public CustomDialog(Frame aFrame, OperationType operationType) {
		super(aFrame, true);
		this.typeOfOperation = operationType;

		// Create an array of the text and components to be displayed.
		String msgString1 = null;
		String msgString2 = null;

		nameTextField = new JTextField(10);
		if (typeOfOperation == OperationType.ADD_NEW_DECK || typeOfOperation == OperationType.EDIT_DECK) {
			importCodeTextArea = new JTextArea(5, 10);
			importCodeTextArea.setLineWrap(true);
		}

		switch (typeOfOperation) {
		case ADD_NEW_DECK:
			setTitle("New Deck");
			msgString1 = "Enter a name for the deck.";
			msgString2 = "Enter an import code or leave blank.";
			break;
		case ADD_NEW_DECKBINDER:
			setTitle("New Deck Binder");
			msgString1 = "Enter a name for the deck binder.";
			break;
		case EDIT_DECK:
			setTitle("Edit Deck");
			msgString1 = "Edit this deck's name.";
			msgString2 = "Edit this deck's import code.";
			Deck d = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeck();
			importCodeTextArea.setText(d.getImportCode());
			nameTextField.setText(d.getName());
			nameTextField.selectAll();
			break;
		}

		Object[] array = { msgString1, nameTextField, msgString2, importCodeTextArea };

		// Create an array specifying the number of dialog buttons
		// and their text.
		Object[] options = { btnString1, btnString2 };

		// Create the JOptionPane.
		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);

		// Make this dialog display it.
		setContentPane(optionPane);

		// Handle window closing correctly.
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				escapeTheWindow();
			}
		};
		addWindowListener(exitListener);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		// Ensure the text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent ce) {
				nameTextField.requestFocusInWindow();
			}
		});

		// Register an event handler that puts the text into the option pane.
		nameTextField.addActionListener(this);

		// Register an event handler that reacts to option pane state changes.
		optionPane.addPropertyChangeListener(this);

		pack();
		setLocationRelativeTo(aFrame);
		setVisible(true);
	}

	/**
	 * This method handles events for the text field.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		optionPane.setValue(btnString1);
	}

	/**
	 * This method reacts to state changes in the option pane.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();

		if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				// ignore reset
				return;
			}

			// Reset the JOptionPane's value.
			// If you don't do this, then if the user
			// presses the same button next time, no
			// property change event will be fired.
			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			typedText = nameTextField.getText();

			if (btnString1.equals(value)) {
				if (typedText.length() > 0) {
					switch (getTypeOfOperation()) {
					case ADD_NEW_DECK:
						DeckBinder db = DeckManager.cfg.getCase().getDeckBinder(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder());

						if (db != null) {
							if (!db.containsDeck(typedText)) {
								Deck newDeck = new Deck();
								newDeck.setName(typedText);
								newDeck.setImportCode(importCodeTextArea.getText());
								db.addDeck(newDeck);
								db.getDeckBinderPanel().getComboBox().setSelectedItem(newDeck);
								exit();
							} else {
								JOptionPane.showMessageDialog(this, "Sorry, \"" + typedText + "\" " + "already exists in this deck binder.\n" + "Please enter a different name.", "Try again",
										JOptionPane.ERROR_MESSAGE);
								typedText = null;
								nameTextField.requestFocusInWindow();
							}
						} else {
							System.out.println("CustomDialog class: the DB, " + db + " is null.");
						}
						
						break;
						
					case ADD_NEW_DECKBINDER:
						Case briefcase = DeckManager.cfg.getCase();
						
						if (!briefcase.containsDeckBinder(typedText)) {
							new DeckBinder(typedText);
							// TODO: should have a function to do this for me.
							DeckManager.getDeckManagerGUI().casePanel.add(DeckManager.getDeckManagerGUI().getDeckBinderPanels().getLast());
							DeckManager.getDeckManagerGUI().casePanel.revalidate();
							//
							exit();
						} else {
							JOptionPane.showMessageDialog(this, "Sorry, \"" + typedText + "\" " + "already exists as a deck binder.\n" + "Please enter a different name.", "Try again",
									JOptionPane.ERROR_MESSAGE);
							typedText = null;
							nameTextField.requestFocusInWindow();
						}
						
						break;

					case EDIT_DECK:
						Deck oldDeck = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeck();
						boolean nameTaken = DeckManager.cfg.getCase().getDeckBinder(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder()).containsDeck(typedText);
						boolean nameIsSame = typedText.equals(oldDeck.getName());
						
						if (nameTaken && nameIsSame) {
							// Then I know the name of the deck has not been
							// changed.
							oldDeck.setImportCode(importCodeTextArea.getText());
							exit();
						} else if (!nameTaken && !nameIsSame) {
							// Then I know the name has been changed and is
							// valid.
							oldDeck.setName(typedText);
							oldDeck.setImportCode(importCodeTextArea.getText());
							exit();
						} else if (nameTaken && !nameIsSame) {
							// Then this name is already in use.
							JOptionPane.showMessageDialog(this, "Sorry, \"" + typedText + "\" " + "already exists in this deck binder.\n" + "Please enter a different name.", "Try again",
									JOptionPane.ERROR_MESSAGE);
							typedText = null;
							nameTextField.requestFocusInWindow();
						}
						
						break;
					}
				} else {
					noTypedTextPrompt();
				}
			} else { // user closed dialog or clicked cancel
				escapeTheWindow();
			}
		}
	}

	public void noTypedTextPrompt() {
		JOptionPane.showMessageDialog(this, "A name must be submitted.", "Try again", JOptionPane.ERROR_MESSAGE);
		typedText = null;
		nameTextField.requestFocusInWindow();
	}

	public OperationType getTypeOfOperation() {
		return typeOfOperation;
	}

	public void setTypeOfOperation(OperationType typeOfOperation) {
		this.typeOfOperation = typeOfOperation;
	}

	public void escapeTheWindow() {
		typedText = null;
		// need if statement to take focus off of "add new deck" option upon
		// window closing.
		if (getTypeOfOperation().equals(OperationType.ADD_NEW_DECK)) {
			DeckManager.cfg.getCase().getDeckBinder(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder()).getDeckBinderPanel().getComboBox().setSelectedIndex(0);
		}
		exit();
	}

	/**
	 * This method clears the dialog and hides it.
	 */
	public void exit() {
		dispose();
	}
}