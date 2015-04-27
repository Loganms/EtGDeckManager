package com.thaplayaslaya;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class DeckManagerMenuBar extends JMenuBar {

	public static final int TOP = 0, BOTTOM = 1, LEFT = 2, RIGHT = 3, CENTER = 4, FLUSH_TOP = 5, FLUSH_BOTTOM = 6, FLUSH_LEFT = 7, FLUSH_RIGHT = 8;

	private JMenu fileMenu;
	private JMenu newMenu;
	private JMenuItem newDeckBinderOption;
	private MenuActionListener menuActionListener = new MenuActionListener();

	private JMenu viewMenu;
	private JMenu deckImagesMenu;
	private RadioButtonLocationActionListener rblActionListener = new RadioButtonLocationActionListener();
	private RadioButtonModActionListener rbmActionListener = new RadioButtonModActionListener();
	private JRadioButtonMenuItem[] deckImagesOptions;
	private ButtonGroup locationGroup = new ButtonGroup(), modifierGroup = new ButtonGroup();

	private JMenu helpMenu;
	private String[] helpMenuNames = { "User Manual", "Source Code", "About" };
	private JMenuItem[] helpMenuItems;

	boolean isSetup = true;

	public DeckManagerMenuBar() {
		fileMenu = new JMenu("File");

		newMenu = new JMenu("New");
		newDeckBinderOption = new JMenuItem("Deck Binder");
		newDeckBinderOption.setActionCommand("Deck Binder");
		newDeckBinderOption.addActionListener(menuActionListener);
		newMenu.add(newDeckBinderOption);
		fileMenu.add(newMenu);

		viewMenu = new JMenu("View");
		deckImagesMenu = new JMenu("Deck Images");
		createDeckImagesOptions();

		for (int i = 0; i < deckImagesOptions.length; i++) {
			if (i == 4) {
				deckImagesMenu.addSeparator();
			}
			deckImagesMenu.add(deckImagesOptions[i]);
		}

		viewMenu.add(deckImagesMenu);

		helpMenu = new JMenu("Help");
		createHelpMenuItems();

		this.add(fileMenu);
		this.add(viewMenu);
		this.add(helpMenu);
		isSetup = false;
	}

	private void createHelpMenuItems() {
		helpMenuItems = new JMenuItem[3];
		JMenuItem mi;
		for (String s : helpMenuNames) {
			mi = new JMenuItem(s);
			mi.setActionCommand(s);
			mi.addActionListener(menuActionListener);
			helpMenu.add(mi);
		}
	}

	private void createDeckImagesOptions() {
		deckImagesOptions = new JRadioButtonMenuItem[9];
		JRadioButtonMenuItem rbmi;
		int index = 0;
		for (Orientation o : Orientation.values()) {
			rbmi = new JRadioButtonMenuItem(o.toString());
			rbmi.setActionCommand(o.name());

			if (index < 4) {
				rbmi.addActionListener(rblActionListener);
				locationGroup.add(rbmi);

			} else if (index >= 4) {
				rbmi.addActionListener(rbmActionListener);
				modifierGroup.add(rbmi);
			}
			deckImagesOptions[index] = rbmi;
			index++;
		}
	}

	public static int preferenceToIntCode(String preference) {
		switch (preference) {
		case "TOP":
			return TOP;
		case "BOTTOM":
			return BOTTOM;
		case "LEFT":
			return LEFT;
		case "RIGHT":
			return RIGHT;
		case "CENTER":
			return CENTER;
		case "FLUSH_TOP":
			return FLUSH_TOP;
		case "FLUSH_BOTTOM":
			return FLUSH_BOTTOM;
		case "FLUSH_LEFT":
			return FLUSH_LEFT;
		case "FLUSH_RIGHT":
			return FLUSH_RIGHT;
		default:
			System.out.println("Preference is not an option.");
			return -1;
		}
	}

	private class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String button = e.getActionCommand();
			if (button.equals("Deck Binder")) {
				DeckManager.cfg.getCase().addNewDeckBinder();
			} else if (button.equals(helpMenuNames[0])) {
				JLabel label1 = new JLabel("Deck Manager", SwingConstants.LEFT);
				JTextArea text1 = new JTextArea("     Deck management begins by creating a Deck Binder. To do this, access File > New > Deck Binder. You will be prompted for a name. Enter what ever you like, you can always rename it later by pressing the button with an \"R\" on it. Alternatively, you can delete the entire Deck Binder from the list by pressing the \"D\" button. Be careful, in addition to deleting the Deck Binder, any decks that were saved inside will also be erased.\n\n     A new Deck Binder will be empty except for the option \"add new deck\". Clicking on \"add new deck\" will prompt you for a name and import code to assign to the deck. Only a name is required to create a new deck; you can add the import code later or edit the name and import code by pressing the \"Edit Deck\" button on the right side of the Deck Manager tab. The button labeled \"Copy Code\" copies the selected deck's import code to your system clipboard so that you can paste it elsewhere.\n\n     If a deck has its import code set, clicking the \"View Deck\" button will display an image of the deck in a seperate window. Lastly, the \"Delete\" button deletes the selected deck from its respective Deck Binder.", 12, 30);
				dressTextArea(text1);
				
				JLabel label2 = new JLabel("Oracle", SwingConstants.LEFT);
				JTextArea text2 = new JTextArea("     Every day, the Oracle predicts the next False God you will encounter. To make the most of this information, navigate to the Oracle tab. On the Oracle tab, find the name of the False God that the oracle predicted and click the \"Go\" button. Alternatively, you can begin typing the name of the False God into the search box which will auto-complete the name for you.\n\n     Within a couple seconds, depending on your Internet connection, you will see various decks pop up. Hover over any of the decks to see a complete image. To the right of the search box is the deck that the False God will use against you. Realize that all False God decks contain twice as many cards as are shown in the image. In addition, False Gods fight with a 3x Mark and draw two cards per turn.\n\n     Any other decks listed in the lower portion of the Oracle tab are tried and true, community-recommended decks: the same ones you will find on the community forums. Each False God has at least one recommended deck but most have more. Once you find a suitable deck, click its thumbnail image to copy the import code to your system clipboard.", 12, 30);
				dressTextArea(text2);
				Component[] comps = new Component[]{label1, text1, label2, text2};
				
				new InformationWindow(helpMenuNames[0], comps);
			} else if (button.equals(helpMenuNames[1])) {

			} else if (button.equals(helpMenuNames[2])) {

			}
		}
		
		private void dressTextArea(JTextArea textArea){
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setOpaque(false);
		}
	}
	
	private class InformationWindow extends JFrame {
		JOptionPane optionPane;
		Object[] options = new Object[]{"OK"};
		JPanel mainPanel = new JPanel();
		JPanel panel;
		JScrollPane scroll;
		
		public InformationWindow (String title, Component[] components) {
			setTitle(title);
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			
			for (Component c: components){
				panel = new JPanel();
				panel.add(c);
				mainPanel.add(panel);
			}
			
			mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			
			scroll = new JScrollPane(mainPanel);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setBorder(BorderFactory.createEmptyBorder());
			
			optionPane = new JOptionPane(scroll, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION, null, options);
			
			setContentPane(optionPane);
			pack();
			setLocationRelativeTo(DeckManager.getDeckManagerGUI());
			setVisible(true);
		}
	}

	private class RadioButtonLocationActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String newLoc = e.getActionCommand();
			String loc = DeckManager.getDeckManagerGUI().getPreferredDeckImageLocation();
			String mod = DeckManager.getDeckManagerGUI().getPreferredDeckImageLocationMod();

			if (!newLoc.equals(loc) || isSetup) {
				DeckManager.getDeckManagerGUI().setPreferredDeckImageLocation(newLoc);
				for (int i = CENTER; i < deckImagesOptions.length; i++) {
					deckImagesOptions[i].setEnabled(true);
				}

				if (newLoc.equals(Orientation.TOP.name()) || newLoc.equals(Orientation.BOTTOM.name())) {
					if (mod.equals(Orientation.CENTER.name()) || mod.equals(Orientation.FLUSH_LEFT.name())
							|| mod.equals(Orientation.FLUSH_RIGHT.name())) {
					} else {
						deckImagesOptions[CENTER].doClick();
					}

					deckImagesOptions[FLUSH_TOP].setEnabled(false);
					deckImagesOptions[FLUSH_BOTTOM].setEnabled(false);

				} else if (newLoc.equals(Orientation.LEFT.name()) || newLoc.equals(Orientation.RIGHT.name())) {
					if (mod.equals(Orientation.CENTER.name()) || mod.equals(Orientation.FLUSH_TOP.name())
							|| mod.equals(Orientation.FLUSH_BOTTOM.name())) {
					} else {
						deckImagesOptions[CENTER].doClick();
					}
					deckImagesOptions[FLUSH_LEFT].setEnabled(false);
					deckImagesOptions[FLUSH_RIGHT].setEnabled(false);
				}
			}
		}
	}

	private class RadioButtonModActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String newMod = e.getActionCommand();
			String mod = DeckManager.getDeckManagerGUI().getPreferredDeckImageLocationMod();
			if (!newMod.equals(mod)) {
				DeckManager.getDeckManagerGUI().setPreferredDeckImageLocationMod(newMod);
			}
		}

	}

	public JRadioButtonMenuItem[] getDeckImagesOptions() {
		return deckImagesOptions;
	}

	public enum Orientation {
		TOP("Top", DeckManagerMenuBar.TOP), BOTTOM("Bottom", DeckManagerMenuBar.BOTTOM), LEFT("Left", DeckManagerMenuBar.LEFT), RIGHT("Right",
				DeckManagerMenuBar.RIGHT), CENTER("Center", DeckManagerMenuBar.CENTER), FLUSH_TOP("Flush Top", DeckManagerMenuBar.FLUSH_TOP), FLUSH_BOTTOM(
				"Flush Bottom", DeckManagerMenuBar.FLUSH_BOTTOM), FLUSH_LEFT("Flush Left", DeckManagerMenuBar.FLUSH_LEFT), FLUSH_RIGHT("Flush Right",
				DeckManagerMenuBar.FLUSH_RIGHT);
		private String text;
		private int code;

		Orientation(String text, int code) {
			this.text = text;
			this.code = code;
		}

		public int code() {
			return this.code;
		}

		public String toString() {
			return this.text;
		}
	}

	public static boolean validateLocation(String preferredDeckImageLocation) {
		if (preferenceToIntCode(preferredDeckImageLocation) < CENTER) {
			return true;
		}
		return false;
	}

	public static boolean validateLocationMod(String preferredDeckImageLocationMod) {
		if (preferenceToIntCode(preferredDeckImageLocationMod) >= CENTER) {
			return true;
		}
		return false;
	}

}
