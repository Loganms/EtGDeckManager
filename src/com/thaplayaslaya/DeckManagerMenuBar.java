package com.thaplayaslaya;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

@SuppressWarnings("serial")
public class DeckManagerMenuBar extends JMenuBar {

	public static final int TOP = 0, BOTTOM = 1, LEFT = 2, RIGHT = 3, CENTER = 4, FLUSH_TOP = 5, FLUSH_BOTTOM = 6, FLUSH_LEFT = 7, FLUSH_RIGHT = 8;
	private JMenu fileMenu;
	private JMenu newMenu;
	private JMenuItem newDeckBinderOption;
	private JMenu viewMenu;
	private JMenu deckImagesMenu;
	private RadioButtonLocationActionListener rblActionListener = new RadioButtonLocationActionListener();
	private RadioButtonModActionListener rbmActionListener = new RadioButtonModActionListener();
	private JRadioButtonMenuItem[] deckImagesOptions;
	private ButtonGroup locationGroup = new ButtonGroup(), modifierGroup = new ButtonGroup();
	boolean isSetup = true;

	public DeckManagerMenuBar() {
		fileMenu = new JMenu("File");

		newMenu = new JMenu("New");
		newDeckBinderOption = new JMenuItem("Deck Binder");
		newDeckBinderOption.addActionListener(new FileMenuActionListener());
		newMenu.add(newDeckBinderOption);
		fileMenu.add(newMenu);

		viewMenu = new JMenu("View");
		deckImagesMenu = new JMenu("Deck Images");
		createDeckImagesOptions();

		// deckImagesOptions[preferenceToIntCode(DeckManager.getDeckManagerGUI().getPreferredDeckImageLocation())].doClick();
		// deckImagesOptions[preferenceToIntCode(DeckManager.getDeckManagerGUI().getPreferredDeckImageLocationMod())].doClick();

		for (int i = 0; i < deckImagesOptions.length; i++) {
			if (i == 4) {
				deckImagesMenu.addSeparator();
			}
			deckImagesMenu.add(deckImagesOptions[i]);
		}

		viewMenu.add(deckImagesMenu);

		this.add(fileMenu);
		this.add(viewMenu);
		isSetup = false;
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
			System.out.println("preference is not an option.");
			return -1;
		}
	}

	private class FileMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JMenuItem) (e.getSource())).getText().equals("Deck Binder")) {
				DeckManager.cfg.getCase().addNewDeckBinder();
			}
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
