package com.thaplayaslaya;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class OraclePanel extends JPanel {

	private static final long serialVersionUID = 6311196152723245093L;

	String[] godsArray = { "Akebono", "Chaos Lord", "Dark Matter", "Decay", "Destiny", "Divine Glory", "Dream Catcher", "Elidnis", "Eternal Phoenix", "Ferox", "Fire Queen", "Gemini", "Graviton",
			"Hecate", "Hermes", "Incarnate", "Jezebel", "Lionheart", "Miracle", "Morte", "Neptune", "Obliterator", "Octane", "Osiris", "Paradox", "Rainbow", "Scorpio", "Seism", "Serket" };
	JComboBox<FalseGod> godsCB = new JComboBox<>();
	JPanel godsPanel = new JPanel(), godsCBPanel = new JPanel();
	JLabel godsLabel = new JLabel("Predicted God", JLabel.CENTER);

	public OraclePanel() {
		this.setLayout(new BorderLayout());

		godsCB.setModel(new DefaultComboBoxModel<>(FalseGod.values()));
		godsCB.addItemListener(new ItemChangeListener());
		AutoCompleteDecorator.decorate(godsCB);

		godsLabel.setLabelFor(godsCB);
		godsCBPanel.add(godsCB);
		godsPanel.setLayout(new BorderLayout());
		godsPanel.add(godsLabel, BorderLayout.NORTH);
		godsPanel.add(godsCBPanel, BorderLayout.CENTER);

		this.add(godsPanel, BorderLayout.WEST);
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
}
