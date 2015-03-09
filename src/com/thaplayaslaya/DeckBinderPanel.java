package com.thaplayaslaya;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class DeckBinderPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -1215607079828446786L;
	private static final Dimension MAXIMUM_SIZE = new Dimension(120, 40 + 20);
	private String name = "[Default Name]", upArrow = "UpArrow", downArrow = "DownArrow";
	private JLabel dBName = new JLabel(this.name, JLabel.LEFT);
	private JComboBox<Deck> comboBox = new JComboBox<Deck>();
	private JButton renameButton = new JButton(OperationType.RENAME_DECKBINDER.getButtonText()), deleteButton = new JButton("D");	
	private JPanel northPanel = new JPanel();
	private ItemChangeListener itemChangeListener = new ItemChangeListener();
	private cBFocusListener focusListener = new cBFocusListener();
	private boolean hasListenersEnabled = false;

	public DeckBinderPanel() {
		init();
	}

	public DeckBinderPanel(String name) {
		init();
		setName(name);
	}

	private void init() {
		this.setLayout(new BorderLayout());

		dBName.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
		dBName.setPreferredSize(new Dimension(100, 10));

		renameButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
		renameButton.setMargin(new java.awt.Insets(0, 2, 0, 2));
		renameButton.setToolTipText("Rename Deck Binder");
		renameButton.addActionListener(this);

		deleteButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
		deleteButton.setMargin(new java.awt.Insets(0, 2, 0, 2));
		deleteButton.setToolTipText("Delete Deck Binder");
		deleteButton.addActionListener(this);

		JPanel renameButtonPanel = new JPanel();
		renameButtonPanel.add(renameButton, JPanel.RIGHT_ALIGNMENT);
		renameButtonPanel.add(deleteButton, JPanel.RIGHT_ALIGNMENT);

		northPanel.setLayout(new BorderLayout());
		northPanel.add(dBName, BorderLayout.WEST);
		northPanel.add(renameButtonPanel, BorderLayout.EAST);
		this.add(northPanel, BorderLayout.NORTH);

		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		comboBox.setToolTipText("Move Deck (Shft+UP/DOWN)");
		this.add(comboBox, BorderLayout.CENTER);
		this.setMaximumSize(MAXIMUM_SIZE);
		DeckManager.getDeckManagerGUI().getDeckBinderPanels().add(this);
	}

	public void setListeners() {
		enableListeners();
		// Add keybinds to manipulate item order of the comboBox.
		comboBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_DOWN_MASK), upArrow);
		comboBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK), downArrow);

		comboBox.getActionMap().put(upArrow, new VertArrowAction(upArrow));
		comboBox.getActionMap().put(downArrow, new VertArrowAction(downArrow));
	}
	
	public void disableListeners() {
		if(hasListenersEnabled) {
			comboBox.removeItemListener(itemChangeListener);
			comboBox.removeFocusListener(focusListener);
			this.hasListenersEnabled = false;
		}
	}

	public void enableListeners() {
		if(!hasListenersEnabled){
			comboBox.addItemListener(itemChangeListener);
			comboBox.addFocusListener(focusListener);
			this.hasListenersEnabled = true;
		}
	}
	
	public void setName(String name) {
		this.name = name;
		dBName.setName(name);
		dBName.setText(name);
		comboBox.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public JComboBox<Deck> getComboBox() {
		return comboBox;
	}

	private class VertArrowAction extends AbstractAction {

		private static final long serialVersionUID = 2644070230078784280L;

		public VertArrowAction(String text) {
			super(text);
			putValue(ACTION_COMMAND_KEY, text);
		}

		// Move Items up/down within the JComboBox
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>) e.getSource();
			Deck selectedDeck = (Deck) comboBox.getSelectedItem();
			int selectedIndex = comboBox.getSelectedIndex();
			if (actionCommand.equals(upArrow) && selectedIndex > 0) {
				comboBox.removeItem(selectedDeck);
				comboBox.insertItemAt(selectedDeck, selectedIndex - 1);
				comboBox.setSelectedIndex(selectedIndex - 1);
			} else if (actionCommand.equals(downArrow) && selectedIndex < comboBox.getItemCount() - 2) {
				comboBox.removeItem(selectedDeck);
				comboBox.insertItemAt(selectedDeck, selectedIndex + 1);
				comboBox.setSelectedIndex(selectedIndex + 1);
			}

		}

	}

	private class ItemChangeListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Deck deck = ((Deck) e.getItem());
				// this might be an unnecessary check.
				if ((deck.getName() != null) && (deck.getName().length() > 0)) {
					if (!deck.equals(Deck.DEFAULT)) {
						DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(deck);
					} else {
						DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().addNewDeck();
					}
				}
			}
		}
	}

	private class cBFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>) e.getComponent();
			System.out.println(comboBox.getSelectedItem().toString() + " focus gained");
			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(comboBox.getName());

			if (comboBox.getSelectedItem().equals(Deck.DEFAULT)) {
				DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().addNewDeck();
			}

			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck((Deck) comboBox.getSelectedItem());
			System.out.println(comboBox.getName());
		}

		@Override
		public void focusLost(FocusEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>) e.getComponent();
			System.out.println( ((Deck) comboBox.getSelectedItem()).getName() + " focus lost");

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(OperationType.RENAME_DECKBINDER.getButtonText())) {
			new CustomDialog(DeckManager.getDeckManagerGUI(), OperationType.RENAME_DECKBINDER, this.name);
		} else if (e.getActionCommand().equals("D")) {
			if (JOptionPane.showConfirmDialog(DeckManager.getDeckManagerGUI(), "Are you sure you want to delete this deck binder?") == JOptionPane.YES_OPTION) {
				DeckManagerGUI dmg = DeckManager.getDeckManagerGUI();
				if (DeckManager.getCase().getDeckBinder(this.name).getDecks().contains(dmg.getCurrentlySelectedDeck())){
					dmg.setCurrentlySelectedDeck(null);
				}
				DeckManager.getCase().removeDeckBinder(DeckManager.getCase().getDeckBinder(this.name));
			}
		}
	}
}
