package com.thaplayaslaya;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


public class OraclePanel extends JPanel {

	private static final long serialVersionUID = 6311196152723245093L;

	
	private static final Dimension DEFAULT_DECK_IMAGE_SIZE = new Dimension(646, 252);
	private String[] godsArray = { "Akebono", "Chaos Lord", "Dark Matter", "Decay", "Destiny", "Divine Glory", "Dream Catcher", "Elidnis", "Eternal Phoenix", "Ferox", "Fire Queen", "Gemini", "Graviton",
			"Hecate", "Hermes", "Incarnate", "Jezebel", "Lionheart", "Miracle", "Morte", "Neptune", "Obliterator", "Octane", "Osiris", "Paradox", "Rainbow", "Scorpio", "Seism", "Serket" };
	private JComboBox<FalseGod> godsCB = new JComboBox<>();
	private JPanel mainPanel1 = new JPanel(), godsPanel = new JPanel(), godsCBPanel = new JPanel(), godsButtonPanel = new JPanel();
	private JLabel godsLabel = new JLabel("Predicted God", JLabel.CENTER);
	private JButton godsButton = new JButton("Go");
	
	private FalseGod currentlySelectedFG = null, previouslySelectedFG = null;
	
	//private JComboBox<Deck> recommendedCB = new JComboBox<Deck>();
	private JPanel mainPanel2 = new JPanel(), imagesPanel = new JPanel();
	
	double goTime;

	public OraclePanel() {
		this.setLayout(new GridLayout(2, 1));
		mainPanel1.setLayout(new BorderLayout());

		godsCB.setModel(new DefaultComboBoxModel<>(FalseGod.values()));
		godsCB.addItemListener(new ItemChangeListener());
		AutoCompleteDecorator.decorate(godsCB);
		
		godsButton.addActionListener(new ButtonListener());

		godsLabel.setLabelFor(godsCB);
		godsCBPanel.add(godsCB);
		godsPanel.setLayout(new BorderLayout());
		godsPanel.add(godsLabel, BorderLayout.NORTH);
		godsPanel.add(godsCBPanel, BorderLayout.CENTER);
		godsButtonPanel.add(godsButton);
		godsPanel.add(godsButtonPanel, BorderLayout.EAST);

		mainPanel1.add(godsPanel, BorderLayout.WEST);
		
		mainPanel2.setBorder(BorderFactory.createEtchedBorder());
		
		this.add(mainPanel1);
		this.add(mainPanel2);
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
	
	private void gatherAndDisplayIntel() {
		//protects from redundant searches.
			if(!currentlySelectedFG.equals(previouslySelectedFG)) {
				mainPanel2.removeAll();
				double goTime2 = System.currentTimeMillis();
				List<URL> urls = DownloadPage.getRecommendedDeckURLS(currentlySelectedFG);
				System.out.println(urls.size() + " URLs downloaded in " + (System.currentTimeMillis()-goTime2)/1000 + "sec");
				int count = urls.size();
				double goTime3 = System.currentTimeMillis();
				if (count == 1) {
					LabelImage pi = new LabelImage(urls.get(0));
					pi.setSize(mainPanel2.getSize());
					mainPanel2.add(pi);
				} else {
					for(URL url: urls) {
					//System.out.println("from G&Di " + url);
					//System.out.println("from G&Di " + Deck.convertURLToCode(url));
					LabelImage pi = new LabelImage(url);
					mainPanel2.add(pi);
					}
				
				}
				System.out.println(count + " images took " + (System.currentTimeMillis()-goTime3)/1000 + "sec");
				mainPanel2.revalidate();
				mainPanel2.repaint();
			}
			System.out.println("Operation took: " + ((System.currentTimeMillis() - goTime)/1000) + "sec");
		} 
}
