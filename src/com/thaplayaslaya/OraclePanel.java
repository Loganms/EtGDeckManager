package com.thaplayaslaya;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class OraclePanel extends JPanel {

	private static final long serialVersionUID = 6311196152723245093L;

	private JPanel mainPanel1 = new JPanel();
	private JPanel godsPanel = new JPanel(), godsCBPanel = new JPanel(), godsButtonPanel = new JPanel(), godImagePanel = new JPanel();
	private JComboBox<FalseGod> godsCB = new JComboBox<>();
	private JLabel godsLabel = new JLabel("Predicted False God", JLabel.CENTER);
	private JButton godsButton = new JButton("Go");
	private LabelImage godLabelImage = new FalseGodLabelImage();

	private JTabbedPane deckDisplayTabPane = new JTabbedPane();

	private JPanel recommendedDecksPanel = new JPanel();
	private JPanel customSavedDecksPanel = new JPanel();

	private JPanel progPanel = new JPanel();
	private JLabel progLabel = new JLabel("Searching...", JLabel.CENTER);
	private JProgressBar progBar = new JProgressBar();

	private FalseGod currentlySelectedFG = null, previouslySelectedFG = null;

	private List<RecommendedDeckLabelImage> recommendedDeckImages = null;

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

		// mainPanel1SouthPanel.setLayout(new BorderLayout());
		// mainPanel2Label.setBorder(BorderFactory.createEmptyBorder(10, 2, 0,
		// 0));
		// mainPanel1SouthPanel.add(mainPanel2Label, BorderLayout.WEST);

		// mainPanel1.add(mainPanel1SouthPanel, BorderLayout.SOUTH);

		// mainPanel2.setBorder(BorderFactory.createEtchedBorder());

		deckDisplayTabPane.addTab("C-R Deck(s)", recommendedDecksPanel);
		deckDisplayTabPane.addTab("Custom Deck(s)", customSavedDecksPanel);

		progPanel.setLayout(new BorderLayout());
		progLabel.setLabelFor(progBar);
		progBar.setIndeterminate(true);
		progPanel.add(progLabel, BorderLayout.NORTH);
		progPanel.add(progBar, BorderLayout.CENTER);

		this.add(mainPanel1, BorderLayout.NORTH);
		this.add(deckDisplayTabPane, BorderLayout.CENTER);
	}

	public FalseGod getCurrentlySelectedFG() {
		return currentlySelectedFG;
	}

	public void setCurrentlySelectedFG(FalseGod god) {
		this.currentlySelectedFG = god;
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			previouslySelectedFG = currentlySelectedFG;

			setCurrentlySelectedFG((FalseGod) godsCB.getSelectedItem());
			gatherAndDisplayIntel();
		}
	}

	public void setRecommendedDeckImages(List<RecommendedDeckLabelImage> images) {
		this.recommendedDeckImages = images;
	}

	public List<RecommendedDeckLabelImage> getRecommendedDeckImages() {
		return this.recommendedDeckImages;
	}

	private void gatherAndDisplayIntel() {
		// protects from redundant searches and button spam
		if (!currentlySelectedFG.equals(previouslySelectedFG)) {
			recommendedDecksPanel.removeAll();
			progBar.setVisible(true);
			recommendedDecksPanel.add(progPanel);
			recommendedDecksPanel.revalidate();
			recommendedDecksPanel.repaint();

			SwingWorker<List<RecommendedDeckLabelImage>, FalseGodLabelImage> worker = new SwingWorker<List<RecommendedDeckLabelImage>, FalseGodLabelImage>() {

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
						recommendedDecksPanel.remove(progPanel);
						setRecommendedDeckImages(get());
						for (LabelImage li : get()) {
							recommendedDecksPanel.add(li);
						}
						recommendedDecksPanel.revalidate();
						recommendedDecksPanel.repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			};
			worker.execute();
		}
	}
}
