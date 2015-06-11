package com.thaplayaslaya.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;

public class DeckBinderEditDialog extends JDialog {

	private static final long serialVersionUID = 1173643001427931665L;
	private DeckBinder originalDeckBinder;
	private DeckBinder newDeckBinder;
	private JPanel appearancePanel;
	private JButton backgroundColorButton;
	private JToggleButton boldToggle;
	private JButton cancelButton;
	private DeckBinderPanel deckBinderPanel1;
	private JPanel deckBinderSettingsPanel;
	private JButton defaultButton;
	private JButton doneButton;
	private JButton foregroundColorButton;
	private JToggleButton italicToggle;
	private DefaultComboBoxModel<Deck> listmodel1;
	private JList<Deck> jList1;
	private JLabel jListLabel;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JScrollPane jScrollPane1;
	private JTextField jTextField1;
	private JButton moveDownButton;
	private JButton moveUpButton;
	private JLabel nameLabel;
	private JPanel previewPanel;
	private JToggleButton strikethroughToggle;
	private JToggleButton underlineToggle;
	private FontEffectActionListener fontEffectActionListener;
	private ListOrderActionListener listOrderActionListener;
	private AppearanceActionListener appearanceActionListener;

	public DeckBinderEditDialog(DeckBinder deckBinder) {
		super(DeckManager.getDeckManagerGUI(), "Edit Deck Binder", true);
		this.originalDeckBinder = deckBinder;
		this.newDeckBinder = originalDeckBinder.copy();
		this.deckBinderPanel1 = newDeckBinder.getDBP();
		initComponents();
		setLocation(DeckManager.getDeckManagerGUI().getSmartExternalWindowLocation(this));
		setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {

		previewPanel = new JPanel();
		appearancePanel = new JPanel();
		foregroundColorButton = new JButton();
		backgroundColorButton = new JButton();
		jPanel2 = new JPanel();
		defaultButton = new JButton();
		jPanel3 = new JPanel();
		deckBinderSettingsPanel = new JPanel();
		nameLabel = new JLabel();
		jTextField1 = new JTextField();
		boldToggle = new JToggleButton();
		italicToggle = new JToggleButton();
		underlineToggle = new JToggleButton();
		jScrollPane1 = new JScrollPane();
		jListLabel = new JLabel();
		jPanel4 = new JPanel();
		moveUpButton = new JButton();
		moveDownButton = new JButton();
		strikethroughToggle = new JToggleButton();
		doneButton = new JButton();
		cancelButton = new JButton();

		fontEffectActionListener = new FontEffectActionListener(deckBinderPanel1.getdBName(), newDeckBinder);
		listOrderActionListener = new ListOrderActionListener();
		appearanceActionListener = new AppearanceActionListener(this, deckBinderPanel1.getdBName(), newDeckBinder);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		previewPanel.setBorder(BorderFactory.createTitledBorder("Preview"));

		GroupLayout previewPanelLayout = new GroupLayout(previewPanel);
		previewPanel.setLayout(previewPanelLayout);
		previewPanelLayout.setHorizontalGroup(previewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				previewPanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(deckBinderPanel1, deckBinderPanel1.getPreferredSize().width, deckBinderPanel1.getMaximumSize().width,
								Short.MAX_VALUE).addContainerGap()));
		previewPanelLayout.setVerticalGroup(previewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				previewPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(deckBinderPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		appearancePanel.setBorder(BorderFactory.createTitledBorder("Appearance"));

		foregroundColorButton.setText("Foreground Color");
		foregroundColorButton.addActionListener(appearanceActionListener);

		backgroundColorButton.setText("Background Color");
		backgroundColorButton.addActionListener(appearanceActionListener);

		defaultButton.setText("Default");
		defaultButton.addActionListener(appearanceActionListener);

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
				jPanel2Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(defaultButton)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(defaultButton,
				GroupLayout.Alignment.TRAILING));

		GroupLayout appearancePanelLayout = new GroupLayout(appearancePanel);
		appearancePanel.setLayout(appearancePanelLayout);
		appearancePanelLayout.setHorizontalGroup(appearancePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				appearancePanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								appearancePanelLayout
										.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(backgroundColorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(foregroundColorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jPanel2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addContainerGap()));
		appearancePanelLayout.setVerticalGroup(appearancePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				appearancePanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(foregroundColorButton).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(backgroundColorButton).addGap(11, 11, 11)
						.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, Short.MAX_VALUE));

		deckBinderSettingsPanel.setBorder(BorderFactory.createTitledBorder("Deck Binder Settings"));

		nameLabel.setText("Name:");

		jTextField1.setText(newDeckBinder.getName());
		jTextField1.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				updatePreview();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updatePreview();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updatePreview();
			}

			private void updatePreview() {
				deckBinderPanel1.setName(jTextField1.getText());
			}
		});

		if (newDeckBinder.getStyle().isBold()) {
			boldToggle.setSelected(true);
		}
		boldToggle.setFont(UIManager.getFont("Button.font").deriveFont(Font.BOLD));
		boldToggle.setText("B");
		boldToggle.addActionListener(fontEffectActionListener);

		if (newDeckBinder.getStyle().isItalic()) {
			italicToggle.setSelected(true);
		}
		italicToggle.setFont(UIManager.getFont("Button.font").deriveFont(Font.PLAIN).deriveFont(Font.ITALIC));
		italicToggle.setText("I");
		italicToggle.addActionListener(fontEffectActionListener);

		if (newDeckBinder.getStyle().isUnderline()) {
			underlineToggle.setSelected(true);
		}
		underlineToggle.setText("U");
		Font font = UIManager.getFont("Button.font").deriveFont(Font.PLAIN);
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		underlineToggle.setFont(new Font(attributes));
		underlineToggle.addActionListener(fontEffectActionListener);

		if (newDeckBinder.getStyle().isStrikethrough()) {
			strikethroughToggle.setSelected(true);
		}
		strikethroughToggle.setText("abc");
		Font font1 = UIManager.getFont("Button.font").deriveFont(Font.PLAIN);
		Map attributes1 = font1.getAttributes();
		attributes1.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		strikethroughToggle.setFont(new Font(attributes1));
		strikethroughToggle.addActionListener(fontEffectActionListener);

		// DefaultComboBoxModel so it can be used as a ListModel and sync the
		// two up.
		listmodel1 = new DefaultComboBoxModel();

		jList1 = new JList(listmodel1);
		for (Deck d : newDeckBinder.getDecks()) {
			listmodel1.addElement(d);
		}

		jList1.setVisibleRowCount(5);
		jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1.setViewportView(jList1);

		jListLabel.setText("Deck Order:");

		moveUpButton.setText("Move Up");
		moveUpButton.addActionListener(listOrderActionListener);

		moveDownButton.setText("Move Down");
		moveDownButton.addActionListener(listOrderActionListener);

		doneButton.setText("Done");
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				originalDeckBinder.setName(jTextField1.getText());
				originalDeckBinder.setStyle(newDeckBinder.getStyle());
				originalDeckBinder.setDecks(listmodel1);
				dispose();
			}
		});

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel4Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(moveUpButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(moveDownButton)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(moveUpButton).addComponent(moveDownButton)));

		GroupLayout deckBinderSettingsPanelLayout = new GroupLayout(deckBinderSettingsPanel);
		deckBinderSettingsPanel.setLayout(deckBinderSettingsPanelLayout);
		deckBinderSettingsPanelLayout.setHorizontalGroup(deckBinderSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						deckBinderSettingsPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										deckBinderSettingsPanelLayout
												.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(
														deckBinderSettingsPanelLayout.createSequentialGroup().addComponent(jListLabel)
																.addGap(0, 0, Short.MAX_VALUE))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														deckBinderSettingsPanelLayout
																.createSequentialGroup()
																.addGroup(
																		deckBinderSettingsPanelLayout
																				.createParallelGroup(GroupLayout.Alignment.TRAILING)
																				.addComponent(jPanel4, GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addGroup(
																						GroupLayout.Alignment.LEADING,
																						deckBinderSettingsPanelLayout
																								.createSequentialGroup()
																								.addComponent(nameLabel)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.UNRELATED)
																								.addComponent(jTextField1))
																				.addGroup(
																						GroupLayout.Alignment.LEADING,
																						deckBinderSettingsPanelLayout
																								.createSequentialGroup()
																								.addComponent(boldToggle)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(italicToggle)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(underlineToggle)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(strikethroughToggle)
																								.addGap(0, 0, Short.MAX_VALUE))
																				.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING))
																.addContainerGap()))));
		deckBinderSettingsPanelLayout.setVerticalGroup(deckBinderSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				deckBinderSettingsPanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								deckBinderSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLabel)
										.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								deckBinderSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(boldToggle)
										.addComponent(italicToggle).addComponent(underlineToggle).addComponent(strikethroughToggle))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jListLabel)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane1)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(doneButton)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(cancelButton))
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(deckBinderSettingsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
														.addGroup(
																layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																		.addComponent(appearancePanel, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(previewPanel, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(appearancePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(previewPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addComponent(deckBinderSettingsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(
												layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(doneButton)
														.addComponent(cancelButton))).addContainerGap()));

		pack();
		previewPanel.revalidate();
	}

	/*
	 * private class FontEffectActionListener implements ActionListener {
	 * 
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { JToggleButton btn
	 * = (JToggleButton) e.getSource(); boolean activate =
	 * btn.getModel().isSelected(); Font f =
	 * deckBinderPanel1.getdBName().getFont(); JLabel label =
	 * deckBinderPanel1.getdBName(); switch (btn.getText()) { case "B":
	 * label.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
	 * newDeckBinder.getStyle().setBold(label.getFont().isBold()); break; case
	 * "I": label.setFont(f.deriveFont(f.getStyle() ^ Font.ITALIC));
	 * newDeckBinder.getStyle().setItalic(label.getFont().isItalic()); break;
	 * case "U": Map attributes = f.getAttributes(); if (activate) {
	 * attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	 * newDeckBinder.getStyle().setUnderline(true); } else {
	 * attributes.put(TextAttribute.UNDERLINE, -1);
	 * newDeckBinder.getStyle().setUnderline(false); }
	 * label.setFont(f.deriveFont(attributes)); break; case "abc": Map
	 * attributes1 = f.getAttributes(); if (activate) {
	 * attributes1.put(TextAttribute.STRIKETHROUGH,
	 * TextAttribute.STRIKETHROUGH_ON);
	 * newDeckBinder.getStyle().setStrikethrough(true); } else {
	 * attributes1.put(TextAttribute.STRIKETHROUGH, -1);
	 * newDeckBinder.getStyle().setStrikethrough(false); }
	 * label.setFont(f.deriveFont(attributes1)); break; default: break; } } }
	 */

	private class ListOrderActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = jList1.getSelectedIndex();
			DefaultComboBoxModel<Deck> listModel = (DefaultComboBoxModel<Deck>) jList1.getModel();
			Deck d = listModel.getElementAt(index);

			if (index == -1) {
				System.out.println("Select a Deck First!");
			} else if (e.getActionCommand().equals("Move Up") && index > 0) {
				listModel.removeElementAt(index);
				listModel.insertElementAt(d, index - 1);
				jList1.setSelectedIndex(index - 1);
				deckBinderPanel1.getComboBox().setModel(listModel);
			} else if (e.getActionCommand().equals("Move Down") && index < jList1.getModel().getSize() - 1) {
				listModel.removeElementAt(index);
				listModel.insertElementAt(d, index + 1);
				jList1.setSelectedIndex(index + 1);
				deckBinderPanel1.getComboBox().setModel(listModel);
			} else {
				System.out.println("Movement action failed. Action Command is: " + e.getActionCommand());
			}
		}
	}

	/*
	 * private class AppearanceActionListener implements ActionListener {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { String cmd =
	 * e.getActionCommand(); JLabel label = deckBinderPanel1.getdBName();
	 * 
	 * if (cmd.equals("Foreground Color")) { Color color =
	 * JColorChooser.showDialog(DeckBinderEditDialog.this,
	 * "Choose Foreground Color", label.getForeground()); if (null != color) {
	 * label.setForeground(color);
	 * newDeckBinder.getStyle().setForegroundColor(color); }
	 * 
	 * } else if (cmd.equals("Background Color")) { Color color =
	 * JColorChooser.showDialog(DeckBinderEditDialog.this,
	 * "Choose Background Color", label.getBackground()); if (null != color) {
	 * label.setBackground(color);
	 * newDeckBinder.getStyle().setBackgroundColor(color); } } else if
	 * (cmd.equals("Default")) { label.setForeground(null);
	 * label.setBackground(null);
	 * newDeckBinder.getStyle().setForegroundColor(null);
	 * newDeckBinder.getStyle().setBackgroundColor(null); } } }
	 */
}
