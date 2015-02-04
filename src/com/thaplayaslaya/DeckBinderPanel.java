package com.thaplayaslaya;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class DeckBinderPanel extends JPanel {
	
	private static final long serialVersionUID = -1215607079828446786L;
	private static final Dimension MAXIMUM_SIZE = new Dimension(120, 40+15);
	private static JLabel dBName;
	private JComboBox<Deck> comboBox = new JComboBox<Deck>();
	private Deck defaultDeck = new Deck();
	private JButton renameButton = new JButton("R");
	private JPanel northPanel = new JPanel();
	
	public DeckBinderPanel(DeckBinder deckBinder) {
		dBName = new JLabel(deckBinder.toString(), JLabel.LEFT);
		dBName.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
		dBName.setName("dBName");
		this.setLayout(new BorderLayout());
		northPanel.setLayout(new BorderLayout());	
		northPanel.setName("northPanel");
		
		northPanel.add(dBName, BorderLayout.WEST);
		renameButton.setFont(new Font(Font.DIALOG, Font.PLAIN,10));
		renameButton.setMargin(new java.awt.Insets(0, 2, 0, 2));
		renameButton.addActionListener(new DeckBinderOptionsButtonsListener());
		
		JPanel renameButtonPanel = new JPanel();
		renameButtonPanel.setName("renameButtonPanel");
		renameButtonPanel.add(renameButton, JPanel.RIGHT_ALIGNMENT);
		
		northPanel.add(renameButtonPanel, BorderLayout.EAST);
		this.add(northPanel, BorderLayout.NORTH);
		
		for(Deck d : deckBinder.getDecks()) {
			comboBox.addItem(d);
		}
		
		defaultDeck.setName("add new deck");
		comboBox.setName(deckBinder.toString());
		comboBox.addItem(defaultDeck);
		comboBox.addItemListener(new ItemChangeListener());
		comboBox.addFocusListener(new cBFocusListener());
		this.add(comboBox, BorderLayout.CENTER);
		this.setMaximumSize(MAXIMUM_SIZE);
	}
	
	public static JLabel getdBName() {
		return dBName;
	}

	public static void setdBName(JLabel dBName) {
		DeckBinderPanel.dBName = dBName;
	}

	private class ItemChangeListener implements ItemListener{
	    @Override
	    public void itemStateChanged(ItemEvent e) {
	       if (e.getStateChange() == ItemEvent.SELECTED) {
	          Deck deck = ((Deck)e.getItem());
	          if ((deck.getName() != null) && (deck.getName().length() > 0)) {
	         	DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(deck);
	          } 
	       }
	    }       
	}
	
	private class cBFocusListener implements FocusListener {
		
		@Override
		public void focusGained(FocusEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>)e.getComponent();
			System.out.println(comboBox.getSelectedItem().toString() + "focus gained");
			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck((Deck)comboBox.getSelectedItem());
			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(comboBox.getName());
		}
		
		@Override
		public void focusLost(FocusEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>)e.getComponent();
			System.out.println(comboBox.getSelectedItem().toString() + "focus lost");
			
		}
	}
	
	private class DeckBinderOptionsButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("R")) {
				JLabel dBLabel = (JLabel)(((JButton)e.getSource()).getParent().getParent().getComponents())[0];
				String newName = JOptionPane.showInputDialog("Choose a new name for this Deck Binder.", dBLabel.getText());
				if(newName!= null && newName.length() > 0){
					Case briefcase = DeckManager.cfg.getCase();
					if(!briefcase.containsDeckBinder(newName)) {
						briefcase.getDeckBinder(dBLabel.getText()).setName(newName);
						DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(newName);
						dBLabel.setText(newName);
						
						
					} else {
						//TODO: supply user with pop ups explaining why their name change did not go through.
						System.out.println("name already exists for another deck binder.");
					}
					
				} else {
					//TODO: supply user with pop ups explaining why their name change did not go through.
					System.out.println("name is either null or 0 characters long.");
				}
			}
		}
	}
}
