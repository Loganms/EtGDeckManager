package com.thaplayaslaya.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.ComboPopup;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;

public class DeckBinderComboBox<E> extends JComboBox<E> {

	private static final long serialVersionUID = -3685064067119310644L;
	private boolean isMouseInside = false;
	private ListSelectionListener listener;
	private MouseListener mouseListener;
	private PopupMenuListener popupListener;
	private NoteWindow nw;
	private Timer enterTimer;

	public DeckBinderComboBox() {
		setRenderer(new DeckBinderListRenderer());

		enterTimer = new Timer(750, new insideTimerAction());
		enterTimer.setRepeats(false);
		uninstall();
		install();

		mouseListener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				enterTimer.start();
				isMouseInside = true;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				enterTimer.stop();

				NoteWindow.instance.setVisible(false);
				isMouseInside = false;
			}
		};
		getPopupList().addMouseListener(mouseListener);

		popupListener = new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				enterTimer.stop();

				NoteWindow.instance.setVisible(false);
				isMouseInside = false;
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		};
		addPopupMenuListener(popupListener);
	}

	@Override
	public void updateUI() {
		uninstall();
		super.updateUI();
		install();
	}

	private void uninstall() {
		if (listener == null) {

		} else {
			getPopupList().removeListSelectionListener(listener);
			listener = null;
		}
		if (mouseListener == null) {
			return;
		} else {
			getPopupList().removeMouseListener(mouseListener);
			mouseListener = null;
			return;
		}
	}

	protected void install() {
		listener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				if (null != nw) {
					nw.dispose();
				}
				enterTimer.restart();
			}
		};
		getPopupList().addListSelectionListener(listener);
	}

	private JList<?> getPopupList() {
		ComboPopup popup = (ComboPopup) getUI().getAccessibleChild(this, 0);
		return popup.getList();

	}

	public void disableNoteWindows() {
		uninstall();
	}

	static final class NoteWindow extends JWindow {

		private static final long serialVersionUID = -9056010143768180368L;
		private static JTextArea notes;
		private static final int BORDERGAP_TOP_AND_BOTTOM = 5;
		private static final int BORDERGAP_LEFT = 17;
		private static final int BORDERGAP_RIGHT = 10;
		private static final Color BACKGROUND_COLOR_LIGHT = new Color(226, 233, 239);
		private static final Color BACKGROUND_COLOR_DARK = new Color(199, 213, 224);
		private static final Color TEXT_COLOR = new Color(48, 69, 90);
		private static Point pointOnScreen = null;
		private static Point pointer = null;
		private static final JPanel contentPane = new JPanel() {

			private static final long serialVersionUID = 7637008183415331565L;

			@Override
			public Dimension getPreferredSize() {
				System.out.println("Pref size is: " + super.getPreferredSize());
				Dimension d = super.getPreferredSize();
				// The "+2" is just error correction. Feel free to adjust.
				d.width -= (BORDERGAP_LEFT + BORDERGAP_RIGHT) / 2 + 2;
				d.height = (d.height > getMaximumSize().height) ? getMaximumSize().height : d.height;
				return d;

			}

			@Override
			public Dimension getMaximumSize() {
				DeckManagerGUI dmgui = DeckManager.getDeckManagerGUI();
				int p = pointOnScreen.x;
				p = dmgui.getLocationOnScreen().x + dmgui.getWidth() - dmgui.getInsets().right - p;
				return (new Dimension(p, dmgui.rightPanel.getSize().height));
			}

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_COLOR_LIGHT, 0, getHeight(), BACKGROUND_COLOR_DARK);
				g2d.setPaint(gp);

				int[] x = new int[] { 7, getWidth() - 1, getWidth() - 1, 7, 7, 7, 7 };
				int[] y = new int[] { 0, 0, getHeight() - 1, getHeight() - 1, 0, 0, 0 };

				if (null != pointer && pointer.y < getSize().height - 14) {
					x[4] = 7;
					y[4] = pointer.y + 7 * 2;
					x[5] = 0;
					y[5] = pointer.y + 7;
					x[6] = 7;
					y[6] = pointer.y;
				}

				g2d.drawPolygon(x, y, x.length);
				g2d.fillPolygon(x, y, x.length);

				super.paintComponent(g);
			}

			@Override
			protected void paintBorder(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setPaint(TEXT_COLOR);

				int[] x = new int[] { 7, getWidth() - 1, getWidth() - 1, 7, 7, 7, 7 };
				int[] y = new int[] { 0, 0, getHeight() - 1, getHeight() - 1, 0, 0, 0 };

				if (null != pointer && pointer.y < getSize().height - 14) {
					x[4] = 7;
					y[4] = pointer.y + 7 * 2;
					x[5] = 0;
					y[5] = pointer.y + 7;
					x[6] = 7;
					y[6] = pointer.y;
				}

				g2d.drawPolygon(x, y, x.length);
				super.paintBorder(g);
			}
		};

		private static final NoteWindow instance = new NoteWindow();

		private NoteWindow() {
			setBackground(new Color(0, 255, 0, 0));
			contentPane.setOpaque(false);
			contentPane.setBorder(BorderFactory
					.createEmptyBorder(BORDERGAP_TOP_AND_BOTTOM, BORDERGAP_LEFT, BORDERGAP_TOP_AND_BOTTOM, BORDERGAP_RIGHT));
			contentPane.setLayout(new BorderLayout());
			setContentPane(contentPane);
			setFocusableWindowState(false);

			notes = new JTextArea();
			notes.setOpaque(false);
			notes.setForeground(TEXT_COLOR);
			notes.setLineWrap(true);
			notes.setWrapStyleWord(true);
		}

		public static NoteWindow getInstance(String newNotes, Point point) {
			System.out.println("Getting NoteWindow Instance");
			if (null == newNotes || newNotes.equals("")) {
				System.out.println("No notes to show.");
				instance.setVisible(false);
				return null;
			}
			System.out.println("Readying note instance.");
			readyInstance(newNotes, point);
			return instance;
		}

		private static void readyInstance(String newNotes, Point point) {
			pointOnScreen = (Point) point.clone();
			notes.setText(newNotes);
			notes.setSize(getExtremeX() - point.x, 1);
			contentPane.add(notes, BorderLayout.CENTER);
			instance.pack();

			DeckManagerGUI dmgui = DeckManager.getDeckManagerGUI();

			if (getExtremeY() - point.y < contentPane.getPreferredSize().height) {
				System.out.println("Notes taller than available space.");
				if (contentPane.getPreferredSize().height < dmgui.rightPanel.getHeight()) {
					System.out.println("Notes will fit if moved up, moving up.");
					instance.setLocation(new Point(point.x, point.y - ((point.y + contentPane.getPreferredSize().height) - getExtremeY())));
				} else {
					System.out.println("all can't fit. Doing best possible");
					instance.setLocation(new Point(point.x, dmgui.rightPanel.getLocationOnScreen().y));
				}

			} else {
				instance.setLocation(point);
			}

			pointer = (Point) point.clone();
			SwingUtilities.convertPointFromScreen(pointer, contentPane);

			instance.pack();
			instance.setVisible(true);
		}

		public void setNotes(String newNotes) {
			notes.setText(newNotes);
		}

		public static int getExtremeX() {
			DeckManagerGUI dmgui = DeckManager.getDeckManagerGUI();
			return dmgui.getLocationOnScreen().x + dmgui.getWidth() - dmgui.getInsets().right;
		}

		public static int getExtremeY() {
			DeckManagerGUI dmgui = DeckManager.getDeckManagerGUI();
			return dmgui.getLocationOnScreen().y + dmgui.getHeight() - dmgui.getInsets().bottom;
		}
	}

	private class insideTimerAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isMouseInside) {
				JList<?> list = getPopupList();

				if (null != list.getMousePosition()) {
					// System.out.println("--> " +
					// String.valueOf(list.getSelectedValue()));
					int selectedIndex = list.getSelectedIndex();
					Rectangle bounds = list.getCellBounds(selectedIndex, selectedIndex);
					Point p = bounds.getLocation();
					SwingUtilities.convertPointToScreen(p, list);
					p.x += getSize().width;
					Deck d = (Deck) list.getSelectedValue();
					System.out.println(d + d.getNotes());
					nw = NoteWindow.getInstance(d.getNotes(), p);

					if (null != nw) {
						nw.repaint();
					}
				}
			}

		}

	}
}