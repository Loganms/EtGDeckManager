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
	private String name;
	private JLabel dBName;
	private JComboBox<Deck> comboBox = new JComboBox<Deck>();
	private Deck defaultDeck = new Deck();
	private JButton renameButton = new JButton("R");
	private JPanel northPanel = new JPanel();
	
	public DeckBinderPanel(String name) {
		dBName = new JLabel(name, JLabel.LEFT);
		
		setName(name);
		this.setLayout(new BorderLayout());
				
		dBName.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
		dBName.setPreferredSize(new Dimension(100, 10));
		
		renameButton.setFont(new Font(Font.DIALOG, Font.PLAIN,10));
		renameButton.setMargin(new java.awt.Insets(0, 2, 0, 2));
		renameButton.addActionListener(new DeckBinderOptionsButtonsListener());
		
		JPanel renameButtonPanel = new JPanel();
		renameButtonPanel.add(renameButton, JPanel.RIGHT_ALIGNMENT);
		
		northPanel.setLayout(new BorderLayout());	
		northPanel.add(dBName, BorderLayout.WEST);
		northPanel.add(renameButtonPanel, BorderLayout.EAST);
		this.add(northPanel, BorderLayout.NORTH);
		
		comboBox.setName(name);
		//defaultDeck.setName("add new deck");
		//comboBox.addItem(defaultDeck);
		comboBox.addItemListener(new ItemChangeListener());
		comboBox.addFocusListener(new cBFocusListener());
		this.add(comboBox, BorderLayout.CENTER);
		this.setMaximumSize(MAXIMUM_SIZE);
		DeckManager.getDeckManagerGUI().getDeckBinderPanels().add(this);
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

	/*public JLabel getdBName() {
		return dBName;
	}*/

	/*public void setdBName(String name) {
		this.dBName.setText(name);
	}*/

	private class ItemChangeListener implements ItemListener{
	    @Override
	    public void itemStateChanged(ItemEvent e) {
	       if (e.getStateChange() == ItemEvent.SELECTED) {
	          Deck deck = ((Deck)e.getItem());
	          //this might be an unnecessary check.
	          if ((deck.getName() != null) && (deck.getName().length() > 0)) {
	        	  if(!deck.getName().equals(Deck.getDefaultDeck().getName())) {
	        		  DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(deck);
	        	  } else {
	        		  DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(null);
	        		  String newName = JOptionPane.showInputDialog("Enter a name for the new deck.");
	        		  if(!DeckManager.cfg.getCase().getDeckBinder(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder()).containsDeck(newName)) {
	        			  Deck newDeck = new Deck();
		        		  newDeck.setName(newName);
		        		  DeckManager.cfg.getCase().getDeckBinder(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder()).addDeck(newDeck);
		        		  DeckManager.cfg.getCase().getDeckBinder(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder()).dBP.getComboBox().setSelectedItem(newDeck);
	        		  } else {
	        			  System.out.println("This deck binder already contains a deck with that name.");
	        		  }
	        		  
	        	  }
	         	
	          } 
	       }
	    }       
	}
	
	private class cBFocusListener implements FocusListener {
		
		@Override
		public void focusGained(FocusEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>)e.getComponent();
			System.out.println(comboBox.getSelectedItem().toString() + " focus gained");
			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck((Deck)comboBox.getSelectedItem());
			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(comboBox.getName());
			System.out.println(comboBox.getName());
		}
		
		@Override
		public void focusLost(FocusEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>)e.getComponent();
			System.out.println(((Deck) comboBox.getSelectedItem()).getName() + " focus lost");
			
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
						//test: remembering to change name of comboBox 
						// since that is the way I set currentlySelectedDeckBinder for DMGUI.
						((JComboBox<?>)(((JButton)e.getSource()).getParent().getParent().getParent().getComponents())[1]).setName(newName);
						
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
