package com.thaplayaslaya;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class DeckManagerGUI extends JFrame{
	
	private static final long serialVersionUID = 3686286211660935696L;
	
	Dimension MINIMUM_SIZE = new Dimension(300, 100);
	static String windowName = "Deck Manager";
	JPanel leftPanel = new JPanel();
	JPanel casePanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel promptPanel = new JPanel();
	
	JLabel currentlySelectedDeckLabel = new JLabel("[No deck currently selected]", JLabel.CENTER);
	Deck currentlySelectedDeck;
	
	DeckBinder currentlySelectedDeckBinder;
	
	Case briefcase = DeckManager.cfg.getCase();
	
	public DeckManagerGUI() {
		super(windowName);
		
		setFrameDefaults();
		setComponents();
		
		this.pack();
	}

	private void setComponents() {
		this.setLayout(new BorderLayout());
		setOptionsBar();
		setCenter();
		
	}

	private void setCenter() {
		centerPanel.setLayout(new GridLayout(1,2));
		setCase(DeckManager.cfg.getCase());
		
		JPanel rightPanel = new JPanel();	
		JPanel innerRightPanel = new JPanel();
		
		JLabel rightPrompt = new JLabel("What do you want to do with: ", JLabel.CENTER);
		
		JButton[] rightPanelButtons = {new JButton("Copy"),
										new JButton("Duplicate"),
										new JButton("Rename"),
										new JButton("Update"),
										new JButton("Delete")
				};
		/*JButton copyButton = new JButton("Copy");
		JButton duplicateButton = new JButton("Duplicate");
		JButton renameButton = new JButton("Rename");
		JButton updateButton = new JButton("Update");
		JButton deleteButton = new JButton("Delete");*/
		
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		innerRightPanel.setLayout(new BorderLayout());
		innerRightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		promptPanel.setLayout(new BorderLayout());
		promptPanel.add(rightPrompt, BorderLayout.NORTH);
		promptPanel.add(currentlySelectedDeckLabel, BorderLayout.SOUTH);
		innerRightPanel.add(promptPanel, BorderLayout.NORTH);
		
		JPanel buttonsPanel = new JPanel();
		
		buttonsPanel.setLayout(new GridLayout(5,1));
		
		for(JButton jb : rightPanelButtons){
			addAButton(jb, buttonsPanel);
		}
		
		/*addAButton(copyButton, buttonsPanel);
		addAButton(duplicateButton, buttonsPanel);
		addAButton(renameButton, buttonsPanel);
		addAButton(updateButton, buttonsPanel);
		addAButton(deleteButton, buttonsPanel);*/
		
		innerRightPanel.add(buttonsPanel, BorderLayout.CENTER);
		rightPanel.add(innerRightPanel);
		centerPanel.add(rightPanel);
		this.add(centerPanel, BorderLayout.CENTER);
	}

	private void setOptionsBar() {
		JMenuBar bar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu newMenu = new JMenu("New");
		JMenuItem newDeckBinderOption = new JMenuItem("Deck Binder");
		newDeckBinderOption.addActionListener(new FileMenuActionListener());
		newMenu.add(newDeckBinderOption);

	
		fileMenu.add(newMenu);
		
		this.add(bar, BorderLayout.NORTH);
		bar.add(fileMenu);
		
	}
	
	private class FileMenuActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JMenuItem)(e.getSource())).getText().equals("Deck Binder")) {
				Case briefcase = DeckManager.cfg.getCase();
				String string = JOptionPane.showInputDialog("What do you want to name this Deck Binder?");
				
				if((string != null) && (string.length() > 0)) {
					if(!briefcase.containsDeckBinder(string)){
						DeckBinder db = new DeckBinder();
						db.setName(string);
						briefcase.addDeckBinder(db);
						//might replace wonky "get by index" with Case's "getDeckBinder()" func.
						briefcase.getDeckBinders().get(briefcase.getDeckBinders().size() - 1).addAsPanelTo(casePanel);
						leftPanel.revalidate();
					} else {
						//TODO: tell user that name already exists.
					}
					
				} else {
					//TODO: tell user that name is not valid.
				}
			}
		}
	}
	
	private class RightButtonsPanelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Rename")) {
				String newName = JOptionPane.showInputDialog("Submit a new name for this deck.", currentlySelectedDeck.getName());
				if(newName != null && newName.length() > 0) {
					if(!currentlySelectedDeckBinder.containsDeck(newName)){
						currentlySelectedDeck.setName(newName);
						setCurrentlySelectedDeck(currentlySelectedDeck);
						currentlySelectedDeckLabel.revalidate();
						leftPanel.requestFocusInWindow();
					} else {
						//TODO: tell user that name already exists in this Deck Binder.
					}
				} else {
					//TODO: tell user that name is invalid.
				}

			}
			
		}
		
	}
	
	private void setFrameDefaults() {
		this.setMinimumSize(MINIMUM_SIZE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocus();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void addAButton(JButton button, Container container) {
		JPanel panel = new JPanel();
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new RightButtonsPanelListener());
		panel.add(button);
        container.add(panel);
    }
	
	public void setCase(Case briefcase) {
		casePanel.setLayout(new BoxLayout(casePanel, BoxLayout.Y_AXIS));
		briefcase.addToPanel(casePanel);
		
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		leftPanel.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(casePanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		leftPanel.add(scroll, BorderLayout.CENTER);
		centerPanel.add(leftPanel);
	
	}
	
	public void setCurrentlySelectedDeckBinder(DeckBinder deckBinder) {
		currentlySelectedDeckBinder = deckBinder;
	}
	
	public void setCurrentlySelectedDeck(Deck deck) {
		currentlySelectedDeck = deck;
		currentlySelectedDeckLabel.setText(deck.getName());
		promptPanel.revalidate();
	}
}
