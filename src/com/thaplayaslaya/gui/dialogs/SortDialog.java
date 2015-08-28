package com.thaplayaslaya.gui.dialogs;

import static com.thaplayaslaya.datastructures.DeckSort.ALPHA_OPTIONS;
import static com.thaplayaslaya.datastructures.DeckSort.ELEMENT_OPTIONS;
import static com.thaplayaslaya.datastructures.DeckSort.ELEMENT_OPTIONS_ALT;
import static com.thaplayaslaya.datastructures.DeckSort.L1Alpha;
import static com.thaplayaslaya.datastructures.DeckSort.L1Empty;
import static com.thaplayaslaya.datastructures.DeckSort.L1Least;
import static com.thaplayaslaya.datastructures.DeckSort.L1Mark;
import static com.thaplayaslaya.datastructures.DeckSort.L1Most;
import static com.thaplayaslaya.datastructures.DeckSort.LEVEL_ONE_OPTIONS;
import static com.thaplayaslaya.datastructures.DeckSort.MLCopiesOf;
import static com.thaplayaslaya.datastructures.DeckSort.MLElements;
import static com.thaplayaslaya.datastructures.DeckSort.M_L_OPTIONS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.DeckSort;
import com.thaplayaslaya.gui.SortDialogComboBox;

public class SortDialog extends JDialog {

	private static final long serialVersionUID = -2013173436874522492L;
	private static final int LEVEL_ONE_SORT_WIDTH = 60;
	private static final int LEVEL_TWO_SORT_WIDTH = 85;
	private static final int LEVEL_THREE_SORT_WIDTH = 85;

	private static SortingCBListener sortListener;

	/**
	 * Creates new form SortDialog
	 */
	public SortDialog() {
		super(DeckManager.getDeckManagerGUI(), "Sort", true);
		initComponents();
		setLocationRelativeTo(DeckManager.getDeckManagerGUI());
		setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		doneButton = new JButton();
		cancelButton = new JButton();
		sortListener = new SortingCBListener();
		sortFields = new JComponent[3][3];

		panels = new JPanel[] { new JPanel(), new JPanel(), new JPanel(), new JPanel() };

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 0) {
					sortFields[i][j] = new SortDialogComboBox<String>(LEVEL_ONE_OPTIONS, i, j);
					((SortDialogComboBox<String>) sortFields[i][j]).addItemListener(sortListener);
				} else if (j == 1) {
					sortFields[i][j] = new SortDialogComboBox<String>(i, j);
					((SortDialogComboBox<String>) sortFields[i][j]).addItemListener(sortListener);
				} else {
					sortFields[i][j] = new SortDialogComboBox<String>(i, j);
				}
			}
		}

		((SortDialogComboBox<String>) sortFields[0][1]).setModel(new DefaultComboBoxModel<String>(ALPHA_OPTIONS));

		doneButton.setText("Done");
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO:

				Queue<String> sortList = new LinkedList<String>();

				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						JComponent comp = sortFields[i][j];
						if (comp.isEnabled()) {
							if (comp instanceof SortDialogComboBox) {
								String sortOption = (String) ((SortDialogComboBox<String>) comp).getSelectedItem();
								if (sortOption.equals(LEVEL_ONE_OPTIONS[L1Empty])) {
									break;
								} else {
									sortList.add(sortOption);
								}
							} else {
								String sortOption = (String) ((JTextField) comp).getText().trim();
								if (sortOption.equals(LEVEL_ONE_OPTIONS[L1Empty])) {
									// no card code entered
									break;
								} else if (sortOption.length() == 3) {
									// card code entered
									sortList.add(sortOption);
								} else {
									// card code entered but invalid
									break;
								}
							}
						}
					}
				}
				DeckSort.sort(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder(), sortList);
			}
		});

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);

		panels[0].setBorder(javax.swing.BorderFactory.createTitledBorder("Sort \""
				+ DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().getName() + "\" by"));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panels[0]);
		panels[0].setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(sortFields[0][0], javax.swing.GroupLayout.PREFERRED_SIZE, LEVEL_ONE_SORT_WIDTH,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(sortFields[0][1], 0, LEVEL_TWO_SORT_WIDTH, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addComponent(sortFields[0][2], javax.swing.GroupLayout.PREFERRED_SIZE, LEVEL_THREE_SORT_WIDTH,
								javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(sortFields[0][0], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(sortFields[0][1], javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(sortFields[0][2], javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		panels[1].setBorder(javax.swing.BorderFactory.createTitledBorder("Then by"));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(panels[1]);
		panels[1].setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(sortFields[1][0], javax.swing.GroupLayout.PREFERRED_SIZE, LEVEL_ONE_SORT_WIDTH,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(sortFields[1][1], 0, LEVEL_TWO_SORT_WIDTH, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addComponent(sortFields[1][2], javax.swing.GroupLayout.PREFERRED_SIZE, LEVEL_THREE_SORT_WIDTH,
								javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								jPanel2Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(sortFields[1][0], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(sortFields[1][1], javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(sortFields[1][2], javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		panels[2].setBorder(javax.swing.BorderFactory.createTitledBorder("Then by"));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(panels[2]);
		panels[2].setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel3Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(sortFields[2][0], javax.swing.GroupLayout.PREFERRED_SIZE, LEVEL_ONE_SORT_WIDTH,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(sortFields[2][1], 0, LEVEL_TWO_SORT_WIDTH, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addComponent(sortFields[2][2], javax.swing.GroupLayout.PREFERRED_SIZE, LEVEL_THREE_SORT_WIDTH,
								javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel3Layout
						.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								jPanel3Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(sortFields[2][0], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(sortFields[2][1], javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(sortFields[2][2], javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(panels[3]);
		panels[3].setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanel4Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(doneButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cancelButton).addContainerGap()));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel4Layout
						.createSequentialGroup()
						.addGap(0, 0, 0)
						.addGroup(
								jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(doneButton)
										.addComponent(cancelButton)).addGap(0, 0, 0)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(panels[0], javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(panels[1], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(panels[2], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												layout.createSequentialGroup()
														.addGap(0, 0, Short.MAX_VALUE)
														.addComponent(panels[3], javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(panels[0], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panels[1], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panels[2], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panels[3], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

		pack();

		for (int i = 0; i < 3; i++) {
			for (int j = 2; j > 1 - i; j--) {
				sortFields[i][j].setEnabled(false);
			}
		}

		((SortDialogComboBox<String>) sortFields[1][0]).setSelectedIndex(L1Empty);

		((SortDialogComboBox<String>) sortFields[2][0]).setSelectedIndex(L1Empty);

	} // Variables declaration - do not modify

	private javax.swing.JButton cancelButton;
	private javax.swing.JButton doneButton;
	// End of variables declaration
	private JPanel[] panels;
	private JComponent[][] sortFields;

	private class SortingCBListener implements ItemListener {
		@SuppressWarnings("unchecked")
		public void itemStateChanged(ItemEvent e) {

			Object item = e.getItem();
			int i = ((SortDialogComboBox<String>) e.getSource()).getI();
			int j = ((SortDialogComboBox<String>) e.getSource()).getJ();

			if (j == 0) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println(item + " " + ((SortDialogComboBox<String>) e.getSource()).getPosition());

					if (item.equals(LEVEL_ONE_OPTIONS[L1Empty])) {
						System.out.println("EMPTY");

						System.out.println("******");
						for (int i2 = i; i2 < 3; i2++) {
							for (int j2 = j; j2 < 3; j2++) {
								if (!(i == i2 && j == j2)) {
									sortFields[i2][j2].setEnabled(false);
								}
							}
						}
					} else if (item.equals(LEVEL_ONE_OPTIONS[L1Alpha])) {
						setNextLevelOptions(i, j, ALPHA_OPTIONS);
					} else if (item.equals(LEVEL_ONE_OPTIONS[L1Most]) || item.equals(LEVEL_ONE_OPTIONS[L1Least])) {
						String prevSelect = ((SortDialogComboBox<String>) e.getSource()).getPreviouslySelected();
						if (!(prevSelect.equals(LEVEL_ONE_OPTIONS[L1Most]) || prevSelect.equals(LEVEL_ONE_OPTIONS[L1Least]))) {
							setNextLevelOptions(i, j, M_L_OPTIONS);
						}
					} else if (item.equals(LEVEL_ONE_OPTIONS[L1Mark])) {
						setNextLevelOptions(i, j, ELEMENT_OPTIONS);
					}
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					((SortDialogComboBox<String>) e.getSource()).setPreviouslySelected((String) item);
					if (item.equals(LEVEL_ONE_OPTIONS[L1Empty])) {
						if (i != 2) {
							sortFields[i][j + 1].setEnabled(true);
							sortFields[i][j + 2].setEnabled(true);
							sortFields[i + 1][j].setEnabled(true);
							if (!((SortDialogComboBox<String>) sortFields[i + 1][j]).getSelectedItem().equals(LEVEL_ONE_OPTIONS[L1Empty])) {
								SortDialogComboBox<String> temp = (SortDialogComboBox<String>) sortFields[i + 1][j + 1];
								temp.setEnabled(true);
								if (temp.getSelectedItem().equals(M_L_OPTIONS[MLCopiesOf]) || temp.getSelectedItem().equals(M_L_OPTIONS[MLElements])) {
									sortFields[i + 1][j + 2].setEnabled(true);
								}
							}
						}
					} else {
						sortFields[i][j + 1].setEnabled(true);
						sortFields[i][j + 2].setEnabled(true);
					}
				}
			} else if (j == 1) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (item.equals(M_L_OPTIONS[MLCopiesOf])) {
						sortFields[i][j + 1].setEnabled(true);
						JTextField tempComponent = new JTextField(3);
						((GroupLayout) panels[i].getLayout()).replace(sortFields[i][j + 1], tempComponent);
						sortFields[i][j + 1] = tempComponent;
					} else if (item.equals(M_L_OPTIONS[MLElements])) {
						sortFields[i][j + 1].setEnabled(true);
						SortDialogComboBox<String> tempComponent = new SortDialogComboBox<String>(ELEMENT_OPTIONS, i, j);
						tempComponent.insertItemAt(ELEMENT_OPTIONS_ALT, 0);
						tempComponent.setSelectedIndex(0);
						((GroupLayout) panels[i].getLayout()).replace(sortFields[i][j + 1], tempComponent);
						sortFields[i][j + 1] = tempComponent;
					}
				} else if (e.getStateChange() == ItemEvent.DESELECTED
						&& (item.equals(M_L_OPTIONS[MLCopiesOf]) || item.equals(M_L_OPTIONS[MLElements]))) {
					sortFields[i][j + 1].setEnabled(false);
				}
			}
		}

		@SuppressWarnings("unchecked")
		private void setNextLevelOptions(int i, int j, String[] options) {
			((SortDialogComboBox<String>) sortFields[i][j + 1]).setModel(new DefaultComboBoxModel<String>(options));
			sortFields[i][j + 2].setEnabled(false);
		}

	}
}
