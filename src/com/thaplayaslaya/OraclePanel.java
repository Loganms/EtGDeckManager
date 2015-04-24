package com.thaplayaslaya;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class OraclePanel extends JPanel {

	private static final long serialVersionUID = 6311196152723245093L;

	private JPanel mainPanel1 = new JPanel();
	private JPanel godsPanel = new JPanel(), godsCBPanel = new JPanel(), godsButtonPanel = new JPanel(), godImagePanel = new JPanel(),
			mainPanel1SouthPanel = new JPanel();
	private JComboBox<FalseGod> godsCB = new JComboBox<>();
	private JLabel godsLabel = new JLabel("Predicted False God", JLabel.CENTER), mainPanel2Label = new JLabel("Community-Recommended Deck(s):");
	private JButton godsButton = new JButton("Go");
	private LabelImage godImage = new LabelImage();

	private JPanel mainPanel2 = new JPanel();

	private JPanel progPanel = new JPanel();
	private JLabel progLabel = new JLabel("Searching...", JLabel.CENTER);
	private JProgressBar progBar = new JProgressBar();

	private FalseGod currentlySelectedFG = null, previouslySelectedFG = null;

	private List<LabelImage> images = null;

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
		godImagePanel.add(godImage);
		mainPanel1.add(godImagePanel, BorderLayout.CENTER);

		mainPanel1SouthPanel.setLayout(new BorderLayout());
		mainPanel2Label.setBorder(BorderFactory.createEmptyBorder(10, 2, 0, 0));
		mainPanel1SouthPanel.add(mainPanel2Label, BorderLayout.WEST);

		mainPanel1.add(mainPanel1SouthPanel, BorderLayout.SOUTH);

		mainPanel2.setBorder(BorderFactory.createEtchedBorder());

		progPanel.setLayout(new BorderLayout());
		progLabel.setLabelFor(progBar);
		progBar.setIndeterminate(true);
		progPanel.add(progLabel, BorderLayout.NORTH);
		progPanel.add(progBar, BorderLayout.CENTER);

		this.add(mainPanel1, BorderLayout.NORTH);
		this.add(mainPanel2, BorderLayout.CENTER);
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
			System.out.println("Previously Selected FG: " + previouslySelectedFG);
			System.out.println("Currently Selected FG: " + currentlySelectedFG);
			gatherAndDisplayIntel();
			// DEBUGtestDownloadFGDeck();
		}

		/*
		 * private void DEBUGtestDownloadFGDeck() { try {
		 * DownloadPage.getFalseGodDeck(getCurrentlySelectedFG()); } catch
		 * (IOException e) { e.printStackTrace(); }
		 * 
		 * }
		 */
	}

	public void setImages(List<LabelImage> images) {
		this.images = images;
	}

	public List<LabelImage> getImages() {
		return this.images;
	}

	private void gatherAndDisplayIntel() {
		// protects from redundant searches and button spam
		if (!currentlySelectedFG.equals(previouslySelectedFG)) {
			mainPanel2.removeAll();
			progBar.setVisible(true);
			mainPanel2.add(progPanel);
			mainPanel2.revalidate();
			mainPanel2.repaint();

			SwingWorker<List<LabelImage>, Void> worker = new SwingWorker<List<LabelImage>, Void>() {

				@Override
				protected List<LabelImage> doInBackground() throws Exception {
					List<LabelImage> images = new ArrayList<LabelImage>();
					images.add(new LabelImage(DownloadPage.getFalseGodDeck(currentlySelectedFG)));
					List<URL> urls = DownloadPage.getRecommendedDeckURLS(currentlySelectedFG);
					for (URL url : urls) {
						images.add(new LabelImage(url));
					}
					return images;
				}

				@Override
				protected void done() {
					try {
						mainPanel2.remove(progPanel);
						setImages(get());
						for (int i = 0; i < getImages().size(); i++) {
							if (i == 0) {
								godImagePanel.remove(godImage);
								godImage = images.get(0);
								godImage.setClickable(false);
								godImage.setGod(true);
								godImage.setToolTipText("Powers: 3x Mark, 2x Cards, 2x Draw");

								godImagePanel.add(godImage, BorderLayout.CENTER);
								godImagePanel.revalidate();
								godImagePanel.repaint();
							} else {
								mainPanel2.add(images.get(i));
							}
						}
						// remove godImage so it is unaffected by mouse click
						// events
						getImages().remove(0);
						mainPanel2.revalidate();
						mainPanel2.repaint();
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
