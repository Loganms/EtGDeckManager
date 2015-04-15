package com.thaplayaslaya;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
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
import javax.swing.SwingWorker;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class OraclePanel extends JPanel {

	private static final long serialVersionUID = 6311196152723245093L;

	// private static final Dimension DEFAULT_DECK_IMAGE_SIZE = new
	// Dimension(646, 252);

	private JPanel mainPanel1 = new JPanel(), godsPanel = new JPanel(), godsCBPanel = new JPanel(), godsButtonPanel = new JPanel();
	private JComboBox<FalseGod> godsCB = new JComboBox<>();
	private JLabel godsLabel = new JLabel("Predicted God", JLabel.CENTER), mainPanel2Label = new JLabel("Community-Recommended Decks:");
	private JButton godsButton = new JButton("Go");

	private JPanel mainPanel2 = new JPanel();

	private JPanel progPanel = new JPanel();
	private JLabel progLabel = new JLabel("Searching...", JLabel.CENTER);
	private JProgressBar progBar = new JProgressBar();

	private FalseGod currentlySelectedFG = null, previouslySelectedFG = null;
	
	private List<LabelImage> images = null;
	double goTime;

	public OraclePanel() {
		this.setLayout(new BorderLayout());
		mainPanel1.setLayout(new BorderLayout());

		godsCB.setModel(new DefaultComboBoxModel<>(FalseGod.values()));
		godsCB.addItemListener(new ItemChangeListener());
		AutoCompleteDecorator.decorate(godsCB);

		godsButton.addActionListener(new ButtonListener());

		godsLabel.setLabelFor(godsCB);
		godsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		godsCBPanel.add(godsCB);
		godsPanel.setLayout(new BorderLayout());
		godsPanel.add(godsLabel, BorderLayout.NORTH);
		godsPanel.add(godsCBPanel, BorderLayout.CENTER);
		godsButtonPanel.add(godsButton);
		godsPanel.add(godsButtonPanel, BorderLayout.EAST);

		mainPanel1.add(godsPanel, BorderLayout.WEST);
		mainPanel2Label.setBorder(BorderFactory.createEmptyBorder(10, 2, 0, 0));
		mainPanel1.add(mainPanel2Label, BorderLayout.SOUTH);

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
			goTime = System.currentTimeMillis();
			previouslySelectedFG = currentlySelectedFG;
			setCurrentlySelectedFG((FalseGod) godsCB.getSelectedItem());
			System.out.println("Previously Selected FG: " + previouslySelectedFG);
			System.out.println("Currently Selected FG: " + currentlySelectedFG);
			gatherAndDisplayIntel();
		}
	}

	private class ItemChangeListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				FalseGod god = ((FalseGod) e.getItem());
				DeckManager.getDeckManagerGUI().setCurrentlySelectedFG(god);
			}
		}
	}
	
	public void setImages(List<LabelImage> images) {
		this.images = images;
	}
	
	public List<LabelImage> getImages() {
		return this.images;
	}

	private void gatherAndDisplayIntel() {
		// protects from redundant searches.
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
					double goTime2 = System.currentTimeMillis();
					List<URL> urls = DownloadPage.getRecommendedDeckURLS(currentlySelectedFG);
					System.out.println(urls.size() + " URLs downloaded in " + (System.currentTimeMillis() - goTime2) / 1000 + "sec");
					int count = urls.size();
					// goTime3 is longest in any scenario.
					double goTime3 = System.currentTimeMillis();
					for (URL url : urls) {
						images.add(new LabelImage(url));
					}
					System.out.println(count + " images took " + (System.currentTimeMillis() - goTime3) / 1000 + "sec");
					return images;
				}

				@Override
				protected void done() {
					try {
						double goTime4 = System.currentTimeMillis();
						mainPanel2.remove(progPanel);
						setImages(get());
						for (LabelImage li : getImages()) {
							mainPanel2.add(li);
						}
						mainPanel2.revalidate();
						mainPanel2.repaint();
						System.out.println("Those images took " + (System.currentTimeMillis() - goTime4) / 1000 + "sec to be added to mainPanel2");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			worker.execute();
		}
		System.out.println("Operation took: " + ((System.currentTimeMillis() - goTime) / 1000) + "sec");
	}
}
