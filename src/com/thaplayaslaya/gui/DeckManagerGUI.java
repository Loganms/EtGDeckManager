package com.thaplayaslaya.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.gui.dialogs.DeckEditDialog;

public class DeckManagerGUI extends JFrame {

	private static final long serialVersionUID = 3686286211660935696L;
	private static final Dimension MINIMUM_CONTENT_SIZE = new Dimension(364, 214);
	private static final String NO_DECK_SELECTED = "[No Deck Selected]";
	private static final String windowName = "EtG Deck Manager", upArrow = "UpArrow", downArrow = "DownArrow";
	private static final JButton[] rightPanelButtons = {
			new JButton("Copy Code"),
			new JButton("View Deck"),
			new JButton("Edit Deck"),
			new JButton("Delete") };

	private DeckManagerMenuBar menuBar;

	public JTabbedPane tabbedPane = new JTabbedPane();

	public JPanel rightPanel;
	private JPanel casePanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel promptPanel = new JPanel();

	private OraclePanel oraclePanel = new OraclePanel();

	private Deck currentlySelectedDeck;
	private DeckBinder currentlySelectedDeckBinder;
	private JLabel rightPromptLabel;
	private JLabel currentlySelectedDeckLabel = new JLabel(NO_DECK_SELECTED, JLabel.CENTER);
	private LinkedList<DeckBinderPanel> deckBinderPanels = new LinkedList<DeckBinderPanel>();

	private String preferredDeckImageLocation = "";
	private String preferredDeckImageLocationMod = "";

	public DeckManagerGUI() {
		super(windowName);
		setFrameDefaults();
	}

	public void setComponents() {
		this.setLayout(new BorderLayout());
		setCenter();
		this.revalidate();
		this.pack();
		this.setVisible(true);
		// TODO: Worry about this later
		int minFrameX = MINIMUM_CONTENT_SIZE.width + getInsets().left + getInsets().right;
		int minFrameY = MINIMUM_CONTENT_SIZE.height + getInsets().top + getInsets().bottom + menuBar.getHeight();
		this.setMinimumSize(new Dimension(minFrameX, minFrameY));
		this.setFocusable(true);
		this.requestFocus();
	}

	private void setCenter() {
		centerPanel.setLayout(new BorderLayout());
		setCase();

		rightPanel = new JPanel();
		JPanel innerRightPanel = new JPanel();

		rightPromptLabel = new JLabel("Currently Selected Deck", SwingConstants.CENTER);

		String[] rightPanelButtonsToolTips = {
				"Copy this deck's import code to your clipboard",
				"Display an image of this deck in a separate window",
				"Edit this deck's name, import code, and style",
				"Delete this deck" };

		rightPanel.setLayout(new BorderLayout());
		innerRightPanel.setLayout(new BorderLayout());
		innerRightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		promptPanel.setLayout(new BorderLayout());
		promptPanel.add(rightPromptLabel, BorderLayout.NORTH);
		promptPanel.add(currentlySelectedDeckLabel, BorderLayout.SOUTH);
		innerRightPanel.add(promptPanel, BorderLayout.NORTH);

		JPanel buttonsPanel = new JPanel();

		buttonsPanel.setLayout(new GridLayout(rightPanelButtons.length, 1));

		for (int i = 0; i < rightPanelButtons.length; i++) {
			rightPanelButtons[i].setToolTipText(rightPanelButtonsToolTips[i]);
			addAButton(rightPanelButtons[i], buttonsPanel);
		}

		innerRightPanel.add(buttonsPanel, BorderLayout.CENTER);
		rightPanel.add(innerRightPanel);
		centerPanel.add(rightPanel, BorderLayout.EAST);
		tabbedPane.addTab("Deck Manager", null, centerPanel, "Save Organize Edit");
		tabbedPane.setMnemonicAt(tabbedPane.getTabCount() - 1, KeyEvent.VK_M);
		tabbedPane.addTab("Oracle", null, oraclePanel, "Prediction Help");
		tabbedPane.setMnemonicAt(tabbedPane.getTabCount() - 1, KeyEvent.VK_O);

		KeyStroke ctrlUp = KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_DOWN_MASK);
		KeyStroke ctrlDown = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_DOWN_MASK);
		InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(ctrlUp, "none");
		inputMap.put(ctrlDown, "none");

		this.add(tabbedPane, BorderLayout.CENTER);
	}

	private class RightButtonsPanelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentlySelectedDeck != null) {
				if (e.getActionCommand().equals("Copy Code")) {
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(currentlySelectedDeck.getImportCode()), null);
				} else if (e.getActionCommand().equals("View Deck")) {
					BufferedImage img = currentlySelectedDeck.getDeckImage();
					if (img != null) {
						JFrame frame = new JFrame(currentlySelectedDeck.getName());
						frame.getContentPane().add(new JLabel(new ImageIcon(img)));
						frame.pack();
						frame.setLocation(getSmartExternalWindowLocation(frame));
						frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						frame.setIconImage(getIconImage());
						frame.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(DeckManagerGUI.this,
								"A deck image could not be created from " + currentlySelectedDeck.getName() + "'s import code.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getActionCommand().equals("Edit Deck")) {
					new DeckEditDialog(currentlySelectedDeck);
				}

				else if (e.getActionCommand().equals("Delete")) {
					if (JOptionPane.showConfirmDialog(DeckManager.getDeckManagerGUI(),
							"Are you sure you want to delete " + currentlySelectedDeck.getName() + "?") == JOptionPane.YES_OPTION) {
						currentlySelectedDeckBinder.getDeckBinderPanel().disableListeners();
						currentlySelectedDeckBinder.removeDeck(currentlySelectedDeck);
						setCurrentlySelectedDeck(null);
						currentlySelectedDeckBinder.getDeckBinderPanel().enableListeners();
					}
				}
			} else {
				JOptionPane.showMessageDialog(DeckManagerGUI.this, "Please choose a deck first.", "Try again", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class HotkeyAction extends AbstractAction {

		private static final long serialVersionUID = 2644070230078784280L;

		public HotkeyAction(String text) {
			super(text);
			putValue(ACTION_COMMAND_KEY, text);
		}

		// Move DBPs up/down within the CasePanel
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (getCurrentlySelectedDeckBinder() != null) {
				DeckBinder db = getCurrentlySelectedDeckBinder();
				int index = DeckManager.getCase().getDeckBinders().indexOf(db);
				if (actionCommand.equals(upArrow) && index > 0) {
					casePanel.remove(index);
					deckBinderPanels.remove(index);
					DeckManager.getCase().getDeckBinders().remove(index);

					casePanel.add(db.getDeckBinderPanel(), index - 1);
					deckBinderPanels.add(index - 1, db.getDeckBinderPanel());
					DeckManager.getCase().getDeckBinders().add(index - 1, db);

					casePanel.revalidate();
				} else if (actionCommand.equals(downArrow) && index < deckBinderPanels.size() - 1) {
					casePanel.remove(index);
					deckBinderPanels.remove(index);
					DeckManager.getCase().getDeckBinders().remove(index);

					casePanel.add(db.getDeckBinderPanel(), index + 1);
					deckBinderPanels.add(index + 1, db.getDeckBinderPanel());
					DeckManager.getCase().getDeckBinders().add(index + 1, db);

					casePanel.revalidate();
				}
			} else {
				System.out.println("HotkeyAction fails.");
			}
		}
	}

	private void setFrameDefaults() {
		URL iconURL = getClass().getResource("/com/thaplayaslaya/gui/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());

		this.getContentPane().setMinimumSize(MINIMUM_CONTENT_SIZE);
		this.getContentPane().setPreferredSize(MINIMUM_CONTENT_SIZE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		menuBar = new DeckManagerMenuBar();
		this.setJMenuBar(menuBar);

		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DeckManager.saveAndExit();
			}
		});

		JRootPane p = this.getRootPane();
		p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_DOWN_MASK), upArrow);
		p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_DOWN_MASK), downArrow);

		p.getActionMap().put(upArrow, new HotkeyAction(upArrow));
		p.getActionMap().put(downArrow, new HotkeyAction(downArrow));
	}

	private void addAButton(JButton button, Container container) {
		JPanel panel = new JPanel();
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new RightButtonsPanelListener());
		panel.add(button);
		container.add(panel);
	}

	public void setCase() {
		casePanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		WrapLayout wrap = new WrapLayout();
		wrap.setHgap(0);
		wrap.setVgap(7);
		wrap.setAlignment(FlowLayout.LEFT);
		wrap.setMaximizeHorizontalDimension(true);
		casePanel.setLayout(wrap);

		for (DeckBinderPanel dbp : getDeckBinderPanels()) {
			casePanel.add(dbp);
		}

		JScrollPane scroll = new JScrollPane(casePanel);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		centerPanel.add(scroll, BorderLayout.CENTER);
	}

	public LinkedList<DeckBinderPanel> getDeckBinderPanels() {
		return deckBinderPanels;
	}

	public void setDeckBinderPanels(LinkedList<DeckBinderPanel> deckBinderPanels) {
		this.deckBinderPanels = deckBinderPanels;
	}

	public void removeDeckBinderPanel(DeckBinderPanel dbp) {
		casePanel.remove(dbp);
		this.deckBinderPanels.remove(dbp);
		if (currentlySelectedDeckBinder != null && currentlySelectedDeckBinder.equals(dbp.getName())) {
			currentlySelectedDeckBinder = null;
			setCurrentlySelectedDeck(null);
		}
		// Calling repaint after revalidate solved ghosting issue
		promptPanel.revalidate();
		promptPanel.repaint();
		casePanel.revalidate();
		casePanel.repaint();
	}

	public void setCurrentlySelectedDeckBinder(String name) {
		currentlySelectedDeckBinder = DeckManager.getCase().getDeckBinder(name);
	}

	public void setCurrentlySelectedDeckBinder(DeckBinder db) {
		currentlySelectedDeckBinder = db;
	}

	public DeckBinder getCurrentlySelectedDeckBinder() {
		return currentlySelectedDeckBinder;
	}

	public void setCurrentlySelectedDeck(Deck deck) {
		if (deck != null && !deck.equals(Deck.DEFAULT)) {
			currentlySelectedDeck = deck;
			currentlySelectedDeckLabel.setText(deck.getName());

			// TODO: I am setting the preferredSize. Oh no!
			currentlySelectedDeckLabel.setPreferredSize(rightPromptLabel.getSize());
			promptPanel.revalidate();
		} else {
			currentlySelectedDeck = null;
			currentlySelectedDeckLabel.setText(NO_DECK_SELECTED);
			promptPanel.revalidate();
		}
	}

	public Deck getCurrentlySelectedDeck() {
		return currentlySelectedDeck;
	}

	public JPanel getCasePanel() {
		return casePanel;
	}

	public OraclePanel getOraclePanel() {
		return oraclePanel;
	}

	public String getPreferredDeckImageLocation() {
		return preferredDeckImageLocation;
	}

	public void setPreferredDeckImageLocation(String preferredDeckImageLocation) {

		this.preferredDeckImageLocation = preferredDeckImageLocation;
	}

	public String getPreferredDeckImageLocationMod() {
		return preferredDeckImageLocationMod;
	}

	public void setPreferredDeckImageLocationMod(String preferredDeckImageLocationMod) {
		this.preferredDeckImageLocationMod = preferredDeckImageLocationMod;
	}

	public Point getSmartExternalWindowLocation(Window windowToBeDisplayed) {
		int ploc = DeckManagerMenuBar.preferenceToIntCode(getPreferredDeckImageLocation());
		int pmod = DeckManagerMenuBar.preferenceToIntCode(getPreferredDeckImageLocationMod());
		Point loc = getLocation();
		if (ploc == DeckManagerMenuBar.TOP) {
			loc.y -= windowToBeDisplayed.getHeight();
		} else if (ploc == DeckManagerMenuBar.BOTTOM) {
			loc.y += getHeight();
		} else if (ploc == DeckManagerMenuBar.LEFT) {
			loc.x -= windowToBeDisplayed.getWidth();
		} else if (ploc == DeckManagerMenuBar.RIGHT) {
			loc.x += getWidth();
		}

		if (pmod == DeckManagerMenuBar.CENTER) {
			if (ploc == DeckManagerMenuBar.TOP || ploc == DeckManagerMenuBar.BOTTOM) {
				loc.x = loc.x + (getWidth() / 2) - (windowToBeDisplayed.getWidth() / 2);
			} else if (ploc == DeckManagerMenuBar.LEFT || ploc == DeckManagerMenuBar.RIGHT) {
				loc.y = loc.y + getHeight() / 2 - windowToBeDisplayed.getHeight() / 2;
			}
		} else if (pmod == DeckManagerMenuBar.FLUSH_RIGHT) {
			loc.x = loc.x + getWidth() - windowToBeDisplayed.getWidth();
		} else if (pmod == DeckManagerMenuBar.FLUSH_BOTTOM) {
			loc.y = loc.y + getHeight() - windowToBeDisplayed.getHeight();
		}

		// TODO: window will always appear on main monitor. Fix for
		// multi-monitor setups.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		if (loc.y < 0) {
			loc.y = 0;
		} else if (loc.y + windowToBeDisplayed.getHeight() > screenSize.height) {
			loc.y = screenSize.height - windowToBeDisplayed.getHeight();
		}

		if (loc.x < 0) {
			loc.x = 0;
		} else if (loc.x + windowToBeDisplayed.getWidth() > screenSize.width) {
			loc.x = screenSize.width - windowToBeDisplayed.getWidth();
		}

		return loc;
	}
}
