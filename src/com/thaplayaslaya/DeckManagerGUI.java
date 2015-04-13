package com.thaplayaslaya;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

public class DeckManagerGUI extends JFrame {

	private static final long serialVersionUID = 3686286211660935696L;
	private static final Dimension MINIMUM_SIZE = new Dimension(320, 270);
	private static final String windowName = "Deck Manager", upArrow = "UpArrow", downArrow = "DownArrow";
	private static final JButton[] rightPanelButtons = { new JButton("Copy Code"), new JButton("View Deck"), new JButton(OperationType.EDIT_DECK.getButtonText()), new JButton("Delete") };
	
	//TODO: tabbed pane makes Ctrl+UP/DOWN features not work for organization.
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private JPanel leftPanel = new JPanel();
	private JPanel casePanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel promptPanel = new JPanel();
	
	private JPanel oraclePanel = new OraclePanel();
	private FalseGod currentlySelectedFG = null;

	private Deck currentlySelectedDeck;
	private DeckBinder currentlySelectedDeckBinder;
	private JLabel currentlySelectedDeckLabel = new JLabel("[No deck currently selected]", JLabel.CENTER);
	private LinkedList<DeckBinderPanel> deckBinderPanels = new LinkedList<DeckBinderPanel>();

	public DeckManagerGUI() {
		super(windowName);
		setFrameDefaults();
	}

	void setComponents() {
		this.setLayout(new BorderLayout());
		setOptionsBar();
		setCenter();
		this.revalidate();
		this.pack();
	}

	private void setCenter() {
		centerPanel.setLayout(new GridLayout(1, 2));
		setCase();

		JPanel rightPanel = new JPanel();
		JPanel innerRightPanel = new JPanel();

		JLabel rightPrompt = new JLabel("What do you want to do with: ", JLabel.CENTER);

		String[] rightPanelButtonsToolTips = { "Copy this deck's import code to your clipboard", "Display an image of this deck in a separate window", "Edit this deck's name and import code",
				"Delete this deck" };

		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		innerRightPanel.setLayout(new BorderLayout());
		innerRightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		promptPanel.setLayout(new BorderLayout());
		promptPanel.add(rightPrompt, BorderLayout.NORTH);
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
		centerPanel.add(rightPanel);
		tabbedPane.addTab("Deck Manager", null, centerPanel, "Default View");
		tabbedPane.addTab("Oracle", null, oraclePanel, "Recommended Decks");
		this.add(tabbedPane, BorderLayout.CENTER);
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

	private class FileMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JMenuItem) (e.getSource())).getText().equals("Deck Binder")) {
				DeckManager.cfg.getCase().addNewDeckBinder();
			}
		}
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
						frame.setVisible(true);
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					} else {
						JOptionPane.showMessageDialog(DeckManagerGUI.this, "A deck image could not be created from " + currentlySelectedDeck.getName() + "'s import code.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getActionCommand().equals(OperationType.EDIT_DECK.getButtonText())) {
					new CustomDialog(DeckManagerGUI.this, OperationType.EDIT_DECK, null);
				}

				else if (e.getActionCommand().equals("Delete")) {
					if (JOptionPane.showConfirmDialog(DeckManager.getDeckManagerGUI(), "Are you sure you want to delete " + currentlySelectedDeck.getName() + "?") == JOptionPane.YES_OPTION) {
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

	private class VertArrowAction extends AbstractAction {

		private static final long serialVersionUID = 2644070230078784280L;

		public VertArrowAction(String text) {
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
					System.out.println("CTRL+UP pressed and activated");
				} else if (actionCommand.equals(downArrow) && index < deckBinderPanels.size() - 1) {
					casePanel.remove(index);
					deckBinderPanels.remove(index);
					DeckManager.getCase().getDeckBinders().remove(index);

					casePanel.add(db.getDeckBinderPanel(), index + 1);
					deckBinderPanels.add(index + 1, db.getDeckBinderPanel());
					DeckManager.getCase().getDeckBinders().add(index + 1, db);

					casePanel.revalidate();
					System.out.println("CTRL+DOWN pressed and activated");
				}
			} else {
				System.out.println("No deck binder selected. Can't move!");
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
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DeckManager.saveAndExit();
			}
		});

		JRootPane p = this.getRootPane();
		p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_DOWN_MASK), upArrow);
		p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_DOWN_MASK), downArrow);

		p.getActionMap().put(upArrow, new VertArrowAction(upArrow));
		p.getActionMap().put(downArrow, new VertArrowAction(downArrow));
	}

	private void addAButton(JButton button, Container container) {
		JPanel panel = new JPanel();
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new RightButtonsPanelListener());
		panel.add(button);
		container.add(panel);
	}

	public void setCase() {
		casePanel.setLayout(new BoxLayout(casePanel, BoxLayout.Y_AXIS));
		for (DeckBinderPanel dbp : getDeckBinderPanels()) {
			casePanel.add(dbp);
		}

		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		leftPanel.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(casePanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		leftPanel.add(scroll, BorderLayout.CENTER);
		centerPanel.add(leftPanel);
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
			promptPanel.revalidate();
		} else {
			currentlySelectedDeck = null;
			currentlySelectedDeckLabel.setText("[No deck currently selected]");
			promptPanel.revalidate();
		}
	}

	public Deck getCurrentlySelectedDeck() {
		return currentlySelectedDeck;
	}

	public FalseGod getCurrentlySelectedFG() {
		return currentlySelectedFG;
	}

	public void setCurrentlySelectedFG(FalseGod FalseGod) {
		this.currentlySelectedFG = FalseGod;
		System.out.println("Currently Selected FG: " + FalseGod);
	}

	public JPanel getCasePanel() {
		return casePanel;
	}
}
