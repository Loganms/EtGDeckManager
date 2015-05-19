package com.thaplayaslaya.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.DownloadPage;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.FalseGod;

public class OraclePanel extends JPanel {

	private static final long serialVersionUID = 6311196152723245093L;
	private static final String DELETE = "DELETE", LEFT_ARROW = "LEFT_ARROW", RIGHT_ARROW = "RIGHT_ARROW";
	private JPanel mainPanel1 = new JPanel();
	private JPanel godsPanel = new JPanel(), godsCBPanel = new JPanel(), godsButtonPanel = new JPanel(), godImagePanel = new JPanel();
	private JComboBox<FalseGod> godsCB = new JComboBox<>();
	private JLabel godsLabel = new JLabel("Predicted False God", JLabel.CENTER);
	private JButton godsButton = new JButton("Go");
	private LabelImage godLabelImage = new FalseGodLabelImage();

	private JTabbedPane deckDisplayTabPane = new JTabbedPane();

	private JPanel recommendedDecksPanel = new JPanel();
	private JPanel counterDecksPanel = new JPanel();

	private JPanel recommendedProgPanel = new JPanel(), counterProgPanel = new JPanel();
	private JLabel recommendedProgLabel = new JLabel("Searching...", JLabel.CENTER), counterProgLabel = new JLabel("Working...", JLabel.CENTER);
	private JProgressBar recommendedProgBar = new JProgressBar(), counterProgBar = new JProgressBar();

	private FalseGod currentlySelectedFG = null, previouslySelectedFG = null;
	private LabelImage currentlySelectedDeckLabelImage = null;

	private List<RecommendedDeckLabelImage> recommendedDeckImages = null;
	private List<CounterDeckLabelImage> counterDeckLabelImages = null;

	public OraclePanel() {
		this.setLayout(new BorderLayout());
		mainPanel1.setLayout(new BorderLayout());

		godsCB.setModel(new DefaultComboBoxModel<>(FalseGod.values()));
		AutoCompleteDecorator.decorate(godsCB);

		godsButton.addActionListener(new ButtonListener());
		godsButton.setToolTipText("Gather information on the selected False God");

		godsLabel.setLabelFor(godsCB);
		godsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		godsCBPanel.add(godsCB);
		godsPanel.setLayout(new BorderLayout());
		godsPanel.add(godsLabel, BorderLayout.NORTH);
		godsPanel.add(godsCBPanel, BorderLayout.CENTER);
		godsButtonPanel.add(godsButton);
		godsPanel.add(godsButtonPanel, BorderLayout.EAST);

		mainPanel1.add(godsPanel, BorderLayout.WEST);
		godImagePanel.add(godLabelImage);
		mainPanel1.add(godImagePanel, BorderLayout.CENTER);

		deckDisplayTabPane.addTab("Community Deck(s)", null, recommendedDecksPanel, "Community-recommended decks");
		deckDisplayTabPane.setMnemonicAt(deckDisplayTabPane.getTabCount() - 1, KeyEvent.VK_C);
		deckDisplayTabPane.addTab("Custom Deck(s)", null, counterDecksPanel, "Personal False God counters");
		deckDisplayTabPane.setMnemonicAt(deckDisplayTabPane.getTabCount() - 1, KeyEvent.VK_U);
		
		initProg(recommendedProgPanel, recommendedProgLabel, recommendedProgBar);
		initProg(counterProgPanel, counterProgLabel, counterProgBar);

		this.add(mainPanel1, BorderLayout.NORTH);
		this.add(deckDisplayTabPane, BorderLayout.CENTER);

		InputMap inMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), DELETE);
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), LEFT_ARROW);
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), RIGHT_ARROW);

		getActionMap().put(DELETE, new KeyboardAction(DELETE));
		getActionMap().put(LEFT_ARROW, new KeyboardAction(LEFT_ARROW));
		getActionMap().put(RIGHT_ARROW, new KeyboardAction(RIGHT_ARROW));
	}

	public void initProg(JPanel progPanel, JLabel progLabel, JProgressBar progBar) {
		progPanel.setLayout(new BorderLayout());
		progLabel.setLabelFor(progBar);
		progBar.setIndeterminate(true);
		progPanel.add(progLabel, BorderLayout.NORTH);
		progPanel.add(progBar, BorderLayout.CENTER);
	}

	public FalseGod getCurrentlySelectedFG() {
		return currentlySelectedFG;
	}

	public void setCurrentlySelectedFG(FalseGod god) {
		this.currentlySelectedFG = god;
	}

	public LabelImage getCurrentlySelectedDeck() {
		return currentlySelectedDeckLabelImage;
	}

	public void resetCurrentlySelectedDeck() {
		this.currentlySelectedDeckLabelImage = null;
	}

	public void setCurrentlySelectedDeck(RecommendedDeckLabelImage currentlySelectedDeck) {
		this.currentlySelectedDeckLabelImage = currentlySelectedDeck;
	}

	public void setCurrentlySelectedDeck(CounterDeckLabelImage currentlySelectedDeck) {
		this.currentlySelectedDeckLabelImage = currentlySelectedDeck;
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			previouslySelectedFG = currentlySelectedFG;

			setCurrentlySelectedFG((FalseGod) godsCB.getSelectedItem());
			resetCurrentlySelectedDeck();
			gatherAndDisplayIntel();
		}
	}

	public void setRecommendedDeckImages(List<RecommendedDeckLabelImage> images) {
		this.recommendedDeckImages = images;
	}

	public List<RecommendedDeckLabelImage> getRecommendedDeckImages() {
		return this.recommendedDeckImages;
	}

	public void setCounterDeckImages(List<CounterDeckLabelImage> images) {
		this.counterDeckLabelImages = images;
	}

	public List<CounterDeckLabelImage> getCounterDeckImages() {
		return this.counterDeckLabelImages;
	}

	public void refreshRecommendedDeckPanel() {
		recommendedDecksPanel.removeAll();
		recommendedProgBar.setVisible(true);
		recommendedDecksPanel.add(recommendedProgPanel);
		recommendedDecksPanel.revalidate();
		recommendedDecksPanel.repaint();
	}

	public void refreshCounterDeckPanel() {
		counterDecksPanel.removeAll();
		counterProgBar.setVisible(true);
		counterDecksPanel.add(counterProgPanel);
		counterDecksPanel.revalidate();
		counterDecksPanel.repaint();
	}

	private void gatherAndDisplayIntel() {
		// protects from redundant searches and button spam
		if (!currentlySelectedFG.equals(previouslySelectedFG)) {
			refreshRecommendedDeckPanel();
			refreshCounterDeckPanel();

			SwingWorker<List<RecommendedDeckLabelImage>, FalseGodLabelImage> worker1 = new RecommendedAndFGDeckGatherer();
			worker1.execute();
			SwingWorker<List<CounterDeckLabelImage>, Void> worker2 = new CounterDeckGatherer();
			worker2.execute();
		}
	}

	class RecommendedAndFGDeckGatherer extends SwingWorker<List<RecommendedDeckLabelImage>, FalseGodLabelImage> {
		@Override
		protected List<RecommendedDeckLabelImage> doInBackground() throws Exception {
			List<RecommendedDeckLabelImage> images = new ArrayList<>();
			publish(new FalseGodLabelImage(DownloadPage.getFalseGodDeckURL(currentlySelectedFG)));
			List<URL> urls = DownloadPage.getRecommendedDeckURLS(currentlySelectedFG);
			for (URL url : urls) {
				images.add(new RecommendedDeckLabelImage(url));
			}
			return images;
		}

		@Override
		protected void process(List<FalseGodLabelImage> chunks) {
			godImagePanel.remove(godLabelImage);
			godLabelImage = chunks.get(chunks.size() - 1);
			godImagePanel.add(godLabelImage, BorderLayout.CENTER);
			godImagePanel.revalidate();
			godImagePanel.repaint();
		}

		@Override
		protected void done() {
			try {
				recommendedDecksPanel.remove(recommendedProgPanel);
				setRecommendedDeckImages(get());
				for (LabelImage li : get()) {
					recommendedDecksPanel.add(li);
				}
				recommendedDecksPanel.revalidate();
				recommendedDecksPanel.repaint();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	class CounterDeckGatherer extends SwingWorker<List<CounterDeckLabelImage>, Void> {

		@Override
		protected List<CounterDeckLabelImage> doInBackground() throws Exception {
			List<CounterDeckLabelImage> images = new ArrayList<>();
			for (Deck d : DeckManager.getCase().getFGCounterDeckList(currentlySelectedFG.name())) {
				images.add(new CounterDeckLabelImage(d));
			}
			images.add(CounterDeckLabelImage.DEFAULT);
			return images;
		}

		@Override
		protected void done() {
			try {
				counterDecksPanel.remove(counterProgPanel);
				setCounterDeckImages(get());
				for (CounterDeckLabelImage li : get()) {
					if (null != currentlySelectedDeckLabelImage && li.isSameAs((CounterDeckLabelImage) getCurrentlySelectedDeck())) {
						li.setBorder(CounterDeckLabelImage.DEFAULT_SELECTED_BORDER);
						setCurrentlySelectedDeck(li);
					}
					counterDecksPanel.add(li);
				}
				counterDecksPanel.revalidate();
				counterDecksPanel.repaint();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public void refreshCounterDecks() {
		refreshCounterDeckPanel();
		CounterDeckGatherer worker = new CounterDeckGatherer();
		worker.execute();

	}

	private class KeyboardAction extends AbstractAction {

		private static final long serialVersionUID = -8104519757224817624L;

		public KeyboardAction(String text) {
			super(text);
			putValue(ACTION_COMMAND_KEY, text);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (null != currentlySelectedDeckLabelImage && currentlySelectedDeckLabelImage instanceof CounterDeckLabelImage) {

				if (deckDisplayTabPane.getSelectedIndex() != 1)
					deckDisplayTabPane.setSelectedIndex(1);

				if (actionCommand.equals(DELETE)) {

					if (JOptionPane.showConfirmDialog(DeckManager.getDeckManagerGUI(), "Are you sure you want to delete the selected deck?") == JOptionPane.YES_OPTION) {
						if(null != currentlySelectedDeckLabelImage.im){
							currentlySelectedDeckLabelImage.im.dispose();
						}
						DeckManager.getCase().getFGCounterMap().get(currentlySelectedFG.name())
								.remove(((CounterDeckLabelImage) currentlySelectedDeckLabelImage).getDeck());
						resetCurrentlySelectedDeck();
						refreshCounterDecks();
					}
				} else if (actionCommand.equals(LEFT_ARROW)) {
					List<Deck> list = DeckManager.getCase().getFGCounterMap().get((getCurrentlySelectedFG().name()));
					if (list.size() > 1) {
						Deck deck = ((CounterDeckLabelImage) currentlySelectedDeckLabelImage).getDeck();
						int indexOfDeck = list.indexOf(deck);
						if (indexOfDeck > 0) {
							list.remove(deck);
							list.add(indexOfDeck - 1, deck);
							refreshCounterDecks();
						}
					}
				} else if (actionCommand.equals(RIGHT_ARROW)) {
					List<Deck> list = DeckManager.getCase().getFGCounterDeckList(getCurrentlySelectedFG().name());
					if (list.size() > 1) {
						Deck deck = ((CounterDeckLabelImage) currentlySelectedDeckLabelImage).getDeck();
						int indexOfDeck = list.indexOf(deck);
						if (indexOfDeck < list.size() - 1) {
							list.remove(deck);
							list.add(indexOfDeck + 1, deck);
							refreshCounterDecks();
						}
					}
				}
			}
		}
	}
}
