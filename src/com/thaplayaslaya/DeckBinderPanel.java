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

public class DeckBinderPanel extends JPanel {

	private static final long serialVersionUID = -1215607079828446786L;
	private static final Dimension MAXIMUM_SIZE = new Dimension(120, 40 + 20);
	private String name = "[Default Name]", upArrow = "UpArrow", downArrow = "DownArrow";
	private JLabel dBName = new JLabel(this.name, JLabel.LEFT);
	private JComboBox<Deck> comboBox = new JComboBox<Deck>();
	private JButton renameButton = new JButton("R");
	private JPanel northPanel = new JPanel();

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
		renameButton.addActionListener(new DeckBinderOptionsButtonsListener());

		JPanel renameButtonPanel = new JPanel();
		renameButtonPanel.add(renameButton, JPanel.RIGHT_ALIGNMENT);

		northPanel.setLayout(new BorderLayout());
		northPanel.add(dBName, BorderLayout.WEST);
		northPanel.add(renameButtonPanel, BorderLayout.EAST);
		this.add(northPanel, BorderLayout.NORTH);

		this.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		this.add(comboBox, BorderLayout.CENTER);
		this.setMaximumSize(MAXIMUM_SIZE);
		DeckManager.getDeckManagerGUI().getDeckBinderPanels().add(this);
	}

	public void setListeners() {
		comboBox.addItemListener(new ItemChangeListener());
		comboBox.addFocusListener(new cBFocusListener());
		//Add keybinds to manipulate item order of the comboBox.
		comboBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_DOWN_MASK), upArrow);
		comboBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK), downArrow);
		
		comboBox.getActionMap().put(upArrow, new VertArrowAction(upArrow));
		comboBox.getActionMap().put(downArrow, new VertArrowAction(downArrow));
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
		public VertArrowAction (String text) {
			super(text);
			putValue(ACTION_COMMAND_KEY, text);
		}
		
		//Move Items up/down within the JComboBox
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>) e.getSource();
			Deck selectedDeck = (Deck) comboBox.getSelectedItem();
			int selectedIndex = comboBox.getSelectedIndex();
			if(actionCommand.equals(upArrow) && selectedIndex > 0) {
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
					if (!deck.getName().equals(Deck.getDefaultDeck().getName())) {
						DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(deck);
					} else {
						DeckManager.cfg.getCase().getDeckBinder(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder()).addNewDeck();
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
			
			if (comboBox.getSelectedItem().toString().equals(Deck.getDefaultDeck().getName())) {
				DeckManager.cfg.getCase().getDeckBinder(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder()).addNewDeck();
			}
			
			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck((Deck) comboBox.getSelectedItem());
			System.out.println(comboBox.getName());
		}

		@Override
		public void focusLost(FocusEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>) e.getComponent();
			System.out.println(((Deck) comboBox.getSelectedItem()).getName() + " focus lost");

		}
	}

	private class DeckBinderOptionsButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("R")) {
				JLabel dBLabel = (JLabel) (((JButton) e.getSource()).getParent().getParent().getComponents())[0];
				String newName = JOptionPane.showInputDialog("What do you want to name this Deck Binder.", dBLabel.getText());
				if (newName != null && newName.length() > 0) {
					Case briefcase = DeckManager.cfg.getCase();
					if (!briefcase.containsDeckBinder(newName)) {
						briefcase.getDeckBinder(dBLabel.getText()).setName(newName);
						DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(newName);
						dBLabel.setText(newName);
						// test: remembering to change name of comboBox
						// since that is the way I set
						// currentlySelectedDeckBinder for DMGUI.
						((JComboBox<?>) (((JButton) e.getSource()).getParent().getParent().getParent().getComponents())[1]).setName(newName);

					} else {
						// TODO: supply user with pop ups explaining why their
						// name change did not go through.
						System.out.println("name already exists for another deck binder.");
					}

				} else {
					// TODO: supply user with pop ups explaining why their name
					// change did not go through.
					System.out.println("name is either null or 0 characters long.");
				}
			}
		}
	}
}
