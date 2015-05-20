package com.thaplayaslaya.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Case;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.datastructures.OperationType;

/*Thank you to David Kroukamp from StackExchange
 <http://stackoverflow.com/questions/13055107/joptionpane-check-user-input-and-prevent-from-closing-until-conditions-are-met>
 for this CustomDialog example template.*/

public class CustomDialog extends JDialog implements ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = -2396181031393526824L;
	private String typedText = null, btnString1 = "Enter", btnString2 = "Cancel", extraInfo;
	@SuppressWarnings("unused")
	private String typedImportCode = null;
	private JTextField nameTextField;
	private JTabbedPane tabPane;
	private JTextArea importCodeTextArea;
	private JTextArea deckNotesTextArea;
	private JOptionPane optionPane;
	private OperationType typeOfOperation;
	private UndoManager undoManager;

	/**
	 * Returns null if the typed string was invalid; otherwise, returns the
	 * string as the user entered it.
	 */
	public String getValidatedText() {
		return typedText;
	}

	/**
	 * Creates the reusable dialog.
	 */
	public CustomDialog(Frame aFrame, OperationType operationType, String extraInfo) {
		super(aFrame, true);
		this.typeOfOperation = operationType;
		this.extraInfo = extraInfo;

		// Create an array of the text and components to be displayed.
		String msgString1 = null;

		nameTextField = new JTextField(10);
		if (typeOfOperation == OperationType.ADD_NEW_DECK || typeOfOperation == OperationType.EDIT_DECK) {
			undoManager = new UndoManager();
			importCodeTextArea = new JTextArea(7, 20);
			tabPane = new JTabbedPane();

			initTextArea(importCodeTextArea);
			importCodeTextArea.setMinimumSize(new Dimension(300, 100));
			tabPane.addTab("Code", importCodeTextArea);
			deckNotesTextArea = new JTextArea();
			initTextArea(deckNotesTextArea);
			JScrollPane scroll = new JScrollPane(deckNotesTextArea);
			scroll.setBorder(BorderFactory.createEmptyBorder());
			tabPane.addTab("Notes", scroll);
		}

		switch (typeOfOperation) {
		case ADD_NEW_DECK:
			setTitle("New Deck");
			msgString1 = "Enter a name for the deck.";
			// msgString2 = "Enter an import code or leave blank.";
			break;
		case ADD_NEW_DECKBINDER:
			setTitle("New Deck Binder");
			msgString1 = "Enter a name for the deck binder.";
			break;
		case RENAME_DECKBINDER:
			setTitle("Rename Deck Binder");
			msgString1 = "Enter a new name for this deck binder.";
			nameTextField.setText(getExtraInfo());
			nameTextField.selectAll();
			break;
		case EDIT_DECK:
			setTitle("Edit Deck");
			msgString1 = "Edit this deck's name.";
			// msgString2 = "Edit this deck's import code.";
			Deck d = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeck();
			importCodeTextArea.setText(d.getImportCode());
			deckNotesTextArea.setText(d.getNotes());
			nameTextField.setText(d.getName());
			nameTextField.selectAll();
			break;
		case ADD_NEW_FG_COUNTER_DECK:
			setTitle("New Custom Counter");
			msgString1 = "Enter a properly formatted deck code.";
			undoManager = new UndoManager();
			importCodeTextArea = new JTextArea(7, 20);
			initTextArea(importCodeTextArea);
			importCodeTextArea.setMinimumSize(new Dimension(300, 100));
			importCodeTextArea.setBorder(BorderFactory.createEtchedBorder());
			break;
		case EDIT_FG_COUNTER_DECK:
			setTitle("Edit Custom Counter");
			msgString1 = "Edit the Counter Deck's import code.";
			undoManager = new UndoManager();
			importCodeTextArea = new JTextArea(7, 20);
			initTextArea(importCodeTextArea);
			importCodeTextArea.setMinimumSize(new Dimension(300, 100));
			importCodeTextArea.setBorder(BorderFactory.createEtchedBorder());
			importCodeTextArea.setText(((CounterDeckLabelImage)DeckManager.getDeckManagerGUI().getOraclePanel().getCurrentlySelectedDeck()).getDeckCode());
			importCodeTextArea.selectAll();
			break;
		default:
			break;
		}

		Object[] array;
		if (typeOfOperation.equals(OperationType.ADD_NEW_FG_COUNTER_DECK) || typeOfOperation.equals(OperationType.EDIT_FG_COUNTER_DECK)) {
			array = new Object[] { msgString1, importCodeTextArea };
		} else {
			array = new Object[] { msgString1, nameTextField, tabPane };
		}

		// Create an array specifying the number of dialog buttons
		// and their text.
		Object[] options = { btnString1, btnString2 };

		// Create the JOptionPane.
		optionPane = new JOptionPane(array, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);

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

		if (isVisible() && (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
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

			boolean nameTaken;
			boolean nameIsSame;

			if (btnString1.equals(value)) {
				if (typedText.length() > 0 || getTypeOfOperation().equals(OperationType.ADD_NEW_FG_COUNTER_DECK) || getTypeOfOperation().equals(OperationType.EDIT_FG_COUNTER_DECK)) {
					switch (getTypeOfOperation()) {
					case ADD_NEW_DECK:
						DeckBinder db = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder();

						if (db != null) {
							if (!db.containsDeck(typedText)) {
								Deck newDeck = new Deck();
								newDeck.setName(typedText);
								newDeck.setImportCode(importCodeTextArea.getText());
								newDeck.setNotes(deckNotesTextArea.getText());
								db.addDeck(newDeck);
								db.getDeckBinderPanel().getComboBox().setSelectedItem(newDeck);
								exit();
							} else {
								JOptionPane.showMessageDialog(this, "Sorry, \"" + typedText + "\" " + "already exists in this deck binder.\n"
										+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
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
							DeckManager.getDeckManagerGUI().getCasePanel().add(DeckManager.getDeckManagerGUI().getDeckBinderPanels().getLast());
							DeckManager.getDeckManagerGUI().getCasePanel().revalidate();
							exit();
						} else {
							JOptionPane.showMessageDialog(this, "Sorry, \"" + typedText + "\" " + "already exists as a deck binder.\n"
									+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
							typedText = null;
							nameTextField.requestFocusInWindow();
						}
						break;
					case RENAME_DECKBINDER:
						DeckBinder oldDB = DeckManager.getCase().getDeckBinder(getExtraInfo());
						nameTaken = DeckManager.getCase().containsDeckBinder(typedText);
						nameIsSame = typedText.equals(oldDB.getName());

						if (!nameIsSame && !nameTaken) {
							oldDB.setName(typedText);
							exit();
						} else if (!nameIsSame && nameTaken) {
							JOptionPane.showMessageDialog(this, "Sorry, \"" + typedText + "\" " + "already exists as a deck binder.\n"
									+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
							typedText = null;
							nameTextField.requestFocusInWindow();
						}
						break;
					case EDIT_DECK:
						Deck oldDeck = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeck();
						nameTaken = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().containsDeck(typedText);
						nameIsSame = typedText.equals(oldDeck.getName());

						if (nameTaken && nameIsSame) {
							// Then I know the name of the deck has not been
							// changed.
							oldDeck.setImportCode(importCodeTextArea.getText());
							oldDeck.setNotes(deckNotesTextArea.getText());
							exit();
						} else if (!nameTaken && !nameIsSame) {
							// Then I know the name has been changed and is
							// valid.
							oldDeck.setName(typedText);
							oldDeck.setImportCode(importCodeTextArea.getText());
							oldDeck.setNotes(deckNotesTextArea.getText());
							DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(oldDeck);
							exit();
						} else if (nameTaken && !nameIsSame) {
							// Then this name is already in use.
							JOptionPane.showMessageDialog(this, "Sorry, \"" + typedText + "\" " + "already exists in this deck binder.\n"
									+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
							typedText = null;
							nameTextField.requestFocusInWindow();
						}
						break;
					case ADD_NEW_FG_COUNTER_DECK:
						if (importCodeTextArea.getText().length() > 1) {
							Deck newDeck = new Deck();
							newDeck.setImportCode(importCodeTextArea.getText());
							DeckManager.getCase().addFGCounterDeck(getExtraInfo(), newDeck);
							DeckManager.getDeckManagerGUI().getOraclePanel().resetCurrentlySelectedDeck();
							DeckManager.getDeckManagerGUI().getOraclePanel().refreshCounterDecks();
							exit();
						} else {
							JOptionPane.showMessageDialog(this, "Sorry, \"" + importCodeTextArea.getText() + "\" is not an import code.\n"
									+ "Please make sure you input the code correctly.", "Try again", JOptionPane.ERROR_MESSAGE);
							importCodeTextArea.requestFocusInWindow();
						}
						break;
					case EDIT_FG_COUNTER_DECK:
						if (importCodeTextArea.getText().length() > 1) {
						Deck d = ((CounterDeckLabelImage) DeckManager.getDeckManagerGUI().getOraclePanel().getCurrentlySelectedDeck()).getDeck();
						d.setImportCode(importCodeTextArea.getText());
						DeckManager.getDeckManagerGUI().getOraclePanel().refreshCounterDecks();
						exit();
						} else {
							JOptionPane.showMessageDialog(this, "Sorry, \"" + importCodeTextArea.getText() + "\" is not an import code.\n"
									+ "Please make sure you input the code correctly.", "Try again", JOptionPane.ERROR_MESSAGE);
							importCodeTextArea.requestFocusInWindow();
						}
						break;
					default:
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

	@SuppressWarnings("serial")
	public void initTextArea(JTextArea textArea) {
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		Document doc = textArea.getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
			}
		});

		InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = textArea.getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

		am.put("Undo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (undoManager.canUndo()) {
						undoManager.undo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});
		am.put("Redo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (undoManager.canRedo()) {
						undoManager.redo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public void escapeTheWindow() {
		typedText = null;
		// need if statement to take focus off of "add new deck" option upon
		// window closing.
		if (getTypeOfOperation().equals(OperationType.ADD_NEW_DECK)) {
			DeckBinder db = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder();
			db.getDeckBinderPanel().getComboBox().setSelectedIndex(0);
			// For the case where only "add new deck" is left in the DBP
			if (db.getDecks().isEmpty()) {
				db.getDeckBinderPanel().getComboBox().setFocusable(false);
				db.getDeckBinderPanel().getComboBox().setFocusable(true);
				db.getDeckBinderPanel().revalidate();
			}
		}
		ImageMagnifier.cleanInstance();
		exit();
	}

	/**
	 * This method clears the dialog and hides it.
	 */
	public void exit() {
		dispose();
	}
}