package com.thaplayaslaya.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.datastructures.OperationType;
import com.thaplayaslaya.gui.dialogs.CaseExportDialog;
import com.thaplayaslaya.gui.dialogs.CustomDialog;
import com.thaplayaslaya.gui.dialogs.DeckBinderExportDialog;
import com.thaplayaslaya.gui.dialogs.DeckEditDialog;
import com.thaplayaslaya.gui.dialogs.SortDialog;

@SuppressWarnings("serial")
public class DeckManagerMenuBar extends JMenuBar {

	public static final int TOP = 0, BOTTOM = 1, LEFT = 2, RIGHT = 3, CENTER = 4, FLUSH_TOP = 5, FLUSH_BOTTOM = 6, FLUSH_LEFT = 7, FLUSH_RIGHT = 8;

	private JMenu fileMenu;
	private JMenu newMenu, editMenu;
	private MenuActionListener menuActionListener;
	private String newDeckBinderOptionLabel = "Deck Binder", newDeckOptionLabel = "Deck";

	private JMenu viewMenu;
	private JMenu deckImagesMenu;
	private RadioButtonLocationActionListener rblActionListener = new RadioButtonLocationActionListener();
	private RadioButtonModActionListener rbmActionListener = new RadioButtonModActionListener();
	private JRadioButtonMenuItem[] deckImagesOptions;
	private ButtonGroup locationGroup = new ButtonGroup(), modifierGroup = new ButtonGroup();

	private JMenu helpMenu;
	private String[] helpMenuNames = { "User Manual", "Shortcuts", "Source Code", "About" };
	@SuppressWarnings("unused")
	private JMenuItem[] helpMenuItems;

	boolean isSetup = true;

	public DeckManagerMenuBar() {
		menuActionListener = new MenuActionListener();

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		newMenu = new JMenu("New");
		newMenu.setMnemonic(KeyEvent.VK_N);

		createQuickMenuItem(newDeckBinderOptionLabel, KeyEvent.VK_B, KeyEvent.VK_B, newMenu);
		createQuickMenuItem(newDeckOptionLabel, KeyEvent.VK_D, KeyEvent.VK_D, newMenu);
		/*
		 * newDeckBinderOption = new JMenuItem(newDeckBinderOptionLabel);
		 * newDeckBinderOption.setMnemonic(KeyEvent.VK_B);
		 * newDeckBinderOption.setAccelerator
		 * (KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
		 * newDeckBinderOption.setActionCommand(newDeckBinderOptionLabel);
		 * 
		 * newDeckBinderOption.addActionListener(menuActionListener);
		 * newMenu.add(newDeckBinderOption);
		 */
		fileMenu.add(newMenu);

		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);

		createQuickMenuItem("Selected Deck", KeyEvent.VK_S, KeyEvent.VK_E, editMenu);
		fileMenu.add(editMenu);

		createQuickMenuItem("Export", KeyEvent.VK_X, KeyEvent.VK_X, fileMenu);
		createQuickMenuItem("Sort", KeyEvent.VK_S, KeyEvent.VK_S, fileMenu);

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

	private void createQuickMenuItem(String menuItemName, int mnemonicKeyEventCode, int acceleratorKeyEventCode, JMenu menu) {
		JMenuItem mi = new JMenuItem(menuItemName);
		mi.setMnemonic(mnemonicKeyEventCode);
		mi.setAccelerator(KeyStroke.getKeyStroke(acceleratorKeyEventCode, InputEvent.CTRL_DOWN_MASK));
		mi.setActionCommand(menuItemName);
		mi.addActionListener(menuActionListener);
		menu.add(mi);
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
			System.out.println(button);
			if (button.equals(newDeckBinderOptionLabel)) {
				DeckManager.cfg.getCase().addNewDeckBinder();
			} else if (button.equals(newDeckOptionLabel)) {
				DeckBinder db = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder();
				if(db != null){
					db.addNewDeck();
				} else {
					JOptionPane.showMessageDialog(DeckManager.getDeckManagerGUI(), 
							"Please choose a Deck Binder first.", "Try again", JOptionPane.ERROR_MESSAGE);
				}
			} else if (button.equals("Selected Deck")) {
				DeckManagerGUI dmgui = DeckManager.getDeckManagerGUI();
				if (dmgui.getOraclePanel().isShowing()) {
					if (dmgui.getOraclePanel().getCurrentlySelectedDeck() instanceof CounterDeckLabelImage) {
						ImageMagnifier.getInstance(new ImageIcon(((CounterDeckLabelImage) dmgui.getOraclePanel().getCurrentlySelectedDeck())
								.getDeck().getDeckImage()));
						new CustomDialog(DeckManager.getDeckManagerGUI(), OperationType.EDIT_FG_COUNTER_DECK, null);
					}
				} else {
					if (null != dmgui.getCurrentlySelectedDeck()) {
						new DeckEditDialog(dmgui.getCurrentlySelectedDeck());
					} else {
						JOptionPane.showMessageDialog(dmgui, "Please choose a deck first.", "Try again", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else if (button.equals("Export")) {
				// { 0, 1, 2}
				Object[] options = { "Entire Case", "Single Deck Binder", "Cancel" };
				// switch on the outcome
				switch (JOptionPane.showOptionDialog(DeckManager.getDeckManagerGUI(), "What do you want to export?", "Export",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0])) {
				case 0:
					if (DeckManager.getCase().getDeckBinders().isEmpty()) {
						JOptionPane.showMessageDialog(DeckManager.getDeckManagerGUI(),
								"The Case contains no Deck Binders.\nCreate a new Deck Binder and try again.", "Error", JOptionPane.OK_OPTION);
					} else {
						new CaseExportDialog();
					}
					break;
				case 1:
					if (DeckManager.getCase().getDeckBinders().isEmpty()) {
						JOptionPane.showMessageDialog(DeckManager.getDeckManagerGUI(),
								"No Deck Binders available to export.\nCreate a new Deck Binder and try again.", "Error", JOptionPane.OK_OPTION);
					} else {
						new DeckBinderExportDialog();
					}

					break;
				case 2:
					break;
				default:
					break;
				}
			} else if (button.equals("Sort")) {
				if (null != DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder())
					new SortDialog();
				else
					JOptionPane
							.showMessageDialog(DeckManager.getDeckManagerGUI(),
									"A Deck Binder must be selected in order to sort.\nClick on a Deck Binder and try again.", "Error",
									JOptionPane.OK_OPTION);
			} else if (button.equals(helpMenuNames[0])) {
				JLabel label1 = new JLabel("Deck Manager", SwingConstants.LEFT);
				JTextArea text1 = new JTextArea(
						"     Deck management begins by creating a Deck Binder. To do this, access File > New > Deck Binder. You will be prompted for a name. Enter what ever you like, you can always edit it's name later by pressing the button with an \"E\" on it. Alternatively, you can delete the entire Deck Binder from the list by pressing the \"D\" button. Be careful, in addition to deleting the Deck Binder, any decks that were saved inside will also be erased.\n\n     A new Deck Binder will be empty except for the option labeled \"add new deck\". Clicking on \"add new deck\" will create a deck and prompt you for a name and import code. Only a name is required to create a new deck; you can add the import code later or edit the name and import code by pressing the \"Edit Deck\" button, on the right side of the Deck Manager tab. The button labeled \"Copy Code\" copies the selected deck's import code to your system clipboard so that you can paste it elsewhere.\n\n     If a deck has its import code set, clicking the \"View Deck\" button will display an image of the deck in a seperate window. This feature requires an Internet connection. Lastly, the \"Delete\" button deletes the selected deck from its respective Deck Binder.",
						12, 30);
				dressTextArea(text1);

				JLabel label2 = new JLabel("Oracle", SwingConstants.LEFT);
				JTextArea text2 = new JTextArea(
						"     Every day, the Oracle predicts the next False God you will encounter. To make the most of this information, navigate to the Oracle tab. On the Oracle tab, find the name of the False God that the oracle predicted and click the \"Go\" button. Alternatively, you can begin typing the name of the False God into the search box which will auto-complete the name for you.\n\n     Within a couple seconds, depending on your Internet connection, you will see various thumbnail images of decks pop up. Hover over any of the decks to see a complete image. To the right of the search box is the deck that the False God will use against you. Realize that all False God decks contain twice as many cards as are shown in the image. In addition, False Gods fight with a 3x Mark and draw two cards per turn.\n\n     In the lower portion of the Oracle area you will see two tabs, one labeled \"Community Deck(s)\" and another labeled \"Custom Deck(s)\". All decks in the Community section come straight from the community forums. Each False God has at least one community-recommended deck. Once you find a suitable deck, click its thumbnail image to copy the import code to your system clipboard.\n\n     If there is a deck that you enjoy using against a specific False God, you can save it into the \"Custom Deck(s)\" tab by clicking on the plus(+) thumbnail. In order to delete a custom deck you have saved, select the deck by clicking on it and press the delete key on your keyboard. You can also change to order of your custom saved decks by selecting one of them and using the left and right arrow keys.",
						12, 30);
				dressTextArea(text2);
				Component[] comps = new Component[] { label1, text1, label2, text2 };

				new InformationWindow(helpMenuNames[0], comps, true);
			} else if (button.equals(helpMenuNames[1])) {
				JLabel deckManager = new JLabel(" Deck Manager Shortcuts ", SwingConstants.CENTER);
				deckManager.setBorder(BorderFactory.createRaisedBevelBorder());
				ShortcutDescription editDeck = new ShortcutDescription("Edit Deck", "A deck must be selected", "Ctrl + E");
				ShortcutDescription moveDeckBinder = new ShortcutDescription("Move Deck Binder UP/DOWN", "A deck must be selected",
						"Ctrl + UP/DOWN Arrow");
				ShortcutDescription moveDeck = new ShortcutDescription("Move Deck UP/DOWN", "A deck must be selected", "Shft + UP/DOWN Arrow");
				ShortcutDescription createNewDeckBinder = new ShortcutDescription("Create New Deck Binder", null, "Ctrl + B");
				ShortcutDescription createNewDeck = new ShortcutDescription("Create New Deck Binder", "A deck must be selected", "Ctrl + D");
				JLabel oracle = new JLabel(" Oracle Shortcuts ", SwingConstants.CENTER);
				oracle.setBorder(BorderFactory.createRaisedBevelBorder());
				ShortcutDescription editCounterDeck = new ShortcutDescription("Edit Custom Deck", "A custom deck must be selected", "Ctrl + E");
				ShortcutDescription moveCounterDeck = new ShortcutDescription("Move Custom Deck LEFT/RIGHT", "A custom deck must be selected",
						"Ctrl + LEFT/RIGHT Arrow");
				ShortcutDescription deleteCounterDeck = new ShortcutDescription("Delete Custom Deck", "A custom deck must be selected", "DELETE Key");

				Component[] comps = new Component[] {
						deckManager,
						editDeck,
						moveDeckBinder,
						moveDeck,
						createNewDeckBinder,
						createNewDeck,
						oracle,
						editCounterDeck,
						moveCounterDeck,
						deleteCounterDeck };
				new InformationWindow(helpMenuNames[1], comps, true);
			} else if (button.equals(helpMenuNames[2])) {
				JLabel label1 = new JLabel("EtG Deck Manager is an Open Source Project");
				JLabel label2 = new JLabel("All Source Code is Available at:");

				JTextPane f = new JTextPane();
				dressTextPane(f);
				f.setText("https://github.com/ThaPlayaSlaya/EtGDeckManager");

				Component[] comps = new Component[] { label1, label2, f };
				new InformationWindow(helpMenuNames[2], comps, false);
			} else if (button.equals(helpMenuNames[3])) {
				JLabel label1 = new JLabel(helpMenuNames[3] + " EtG Deck Manager " + DeckManager.VERSION_ID);

				JTextArea text1 = new JTextArea("     EtG Deck Manager " + DeckManager.VERSION_ID + " was made by Logan Scheiner. EtG Deck Manager "
						+ DeckManager.VERSION_ID + " is free, open source software. If you paid for EtG Deck Manager " + DeckManager.VERSION_ID
						+ ", contact Logan on the Official Elements Community forums, username: Tha_Playa_Slaya.", 6, 31);
				dressTextArea(text1);

				JLabel label2 = new JLabel("EtG Deck Manager " + DeckManager.VERSION_ID + " Depends on the Following Libraries");
				JTextPane textpane1 = new JTextPane();
				dressTextPane(textpane1);
				textpane1.setText("Gson v2.2.4");

				JTextPane textpane2 = new JTextPane();
				dressTextPane(textpane2);
				textpane2.setText("SwingX v1.6.4");

				JLabel label3 = new JLabel("Acknowledgments");
				label3.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
				JTextArea text2 = new JTextArea(
						"     Special thanks to Elements forum member and administrator, Antiaverage, for developing the extremely helpful http://dek.im which this program uses for some features.",
						3, 31);
				dressTextArea(text2);

				JTextArea text3 = new JTextArea(
						"     Legendary Elements forum member, Xenocidious, who created the first deck managing tool for the community available at http://elementscommunity.org/forum/elements-tools/the-deck-manager-2-0/",
						3, 31);
				dressTextArea(text3);

				Component[] comps = new Component[] { label1, text1, label2, textpane1, textpane2, label3, text2, text3 };
				new InformationWindow(helpMenuNames[3], comps, true);
			}
		}
	}

	private void dressTextPane(JTextPane textPane) {
		textPane.setEditable(false);
		textPane.setBackground(null);
		textPane.setBorder(null);
		textPane.setFont(UIManager.getFont("TextArea.font"));
	}

	private void dressTextArea(JTextArea textArea) {
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
	}

	private class ShortcutDescription extends JPanel {
		JLabel action = new JLabel();
		JTextPane condition = new JTextPane();
		JTextPane hotkey = new JTextPane();

		public ShortcutDescription(String action, String condition, String hotkey) {
			super();
			this.setLayout(new BorderLayout());
			StyledDocument doc = this.condition.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);

			if (action != null) {
				this.action.setText(action);
				this.action.setHorizontalAlignment(SwingConstants.CENTER);
				this.add(this.action, BorderLayout.NORTH);
			}

			dressTextPane(this.condition);
			if (condition != null) {
				this.condition.setText("Condition: " + condition);
			} else {
				this.condition.setText("Condition: None");
			}
			this.add(this.condition, BorderLayout.CENTER);

			if (hotkey != null) {
				doc = this.hotkey.getStyledDocument();
				doc.setParagraphAttributes(0, doc.getLength(), center, false);
				dressTextPane(this.hotkey);
				this.hotkey.setText(hotkey);
				this.add(this.hotkey, BorderLayout.SOUTH);
			}
		}
	}

	private class InformationWindow extends JFrame implements PropertyChangeListener {
		JOptionPane optionPane;
		Object[] options = new Object[] { "OK" };
		JPanel mainPanel = new JPanel();
		JPanel panel;
		JScrollPane scroll;

		public InformationWindow(String title, Component[] components, boolean needsScrollPanel) {
			setTitle(title);
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

			for (Component c : components) {
				panel = new JPanel();
				panel.add(c);
				mainPanel.add(panel);
			}
			if (needsScrollPanel) {

				mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

				scroll = new JScrollPane(mainPanel);
				scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scroll.setBorder(BorderFactory.createEmptyBorder());
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scroll.getVerticalScrollBar().setValue(0);
					}
				});

				optionPane = new JOptionPane(scroll, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION, null, options, options[0]);
			} else {
				optionPane = new JOptionPane(mainPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION, null, options, options[0]);
			}

			optionPane.addPropertyChangeListener(this);

			setContentPane(optionPane);
			pack();
			if (needsScrollPanel) {
				setSize(new Dimension(getWidth(), 440));
			}
			setLocationRelativeTo(DeckManager.getDeckManagerGUI());
			setVisible(true);
		}

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			String prop = e.getPropertyName();

			if (isVisible() && (e.getSource() == optionPane)
					&& (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
				Object value = optionPane.getValue();

				if (value.equals(options[0])) {
					this.dispose();
				}
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

		@Override
		public String toString() {
			return this.text;
		}
	}

	public static boolean validateLocation(String preferredDeckImageLocation) {
		if (preferenceToIntCode(preferredDeckImageLocation) > -1 && preferenceToIntCode(preferredDeckImageLocation) < CENTER) {
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
