package com.thaplayaslaya;

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

@SuppressWarnings({ "serial", "hiding" })
public class XComboBox<Object> extends JComboBox<Object> {
	private boolean isMouseInside = false;
	private ListSelectionListener listener;
	private MouseListener mouseListener;
	private PopupMenuListener popupListener;
	private NoteWindow nw;
	private Timer enterTimer;

	public XComboBox() {
		enterTimer = new Timer(750, new insideTimerAction());
		enterTimer.setRepeats(false);
		uninstall();
		install();

		mouseListener = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				enterTimer.start();
				isMouseInside = true;
			}

			public void mouseExited(MouseEvent e) {
				enterTimer.stop();

				NoteWindow.instance.dispose();
				isMouseInside = false;
			}
		};
		getPopupList().addMouseListener(mouseListener);

		popupListener = new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				enterTimer.stop();

				NoteWindow.instance.dispose();
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

	static class NoteWindow extends JWindow {
		private static JTextArea notes;
		private static final Color BACKGROUND_COLOR_LIGHT = new Color(226, 233, 239);
		private static final Color BACKGROUND_COLOR_DARK = new Color(199, 213, 224);
		private static final Color TEXT_COLOR = new Color(48, 69, 90);
		private static final JPanel contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_COLOR_LIGHT, 0, getHeight(), BACKGROUND_COLOR_DARK);
				g2d.setPaint(gp);

				int[] x = new int[] { 0, getWidth() -1, getWidth()-1, 7, 7 };
				int[] y = new int[] { 0, 0, getHeight() -1, getHeight()-1, 7 };

				g2d.drawPolygon(x, y, x.length);
				g2d.fillPolygon(x, y, x.length);

				super.paintComponent(g);
			}

			@Override
			protected void paintBorder(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setPaint(TEXT_COLOR);
				int[] x = new int[] { 0, getWidth() - 1, getWidth() - 1, 7, 7 };
				int[] y = new int[] { 0, 0, getHeight() - 1, getHeight() - 1, 7 };
				g2d.drawPolygon(x, y, x.length);
				super.paintBorder(g);
			}
		};
		
		//TODO: Should adjust this later to be smart
		public static final Dimension SIZE = new Dimension(209, 140);
		private static final NoteWindow instance = new NoteWindow();

		private NoteWindow() {
			setBackground(new Color(0, 255, 0, 0));
			contentPane.setOpaque(false);
			contentPane.setMaximumSize(SIZE);
			contentPane.setBorder(BorderFactory.createEmptyBorder(5, 17, 5, 10));
			contentPane.setLayout(new BorderLayout());
			setContentPane(contentPane);
			setPreferredSize(SIZE);
			setFocusableWindowState(false);
		}

		public static NoteWindow getInstance(String newNotes, Point point) {
			if (null == newNotes || newNotes.equals("")) {
				instance.setVisible(false);
				return null;
			}

			if (null == notes) {
				notes = new JTextArea();
				notes.setOpaque(false);
				notes.setForeground(TEXT_COLOR);
				notes.setLineWrap(true);
				notes.setWrapStyleWord(true);
				//notes.setMaximumSize(SIZE);

				readyInstance(newNotes, point);

				return instance;
			} else {
				// removing notes is super important for getting current notes
				contentPane.remove(notes);
				readyInstance(newNotes, point);
				return instance;
			}
		}

		private static void readyInstance(String newNotes, Point point) {
			notes.setText(newNotes);
			contentPane.add(notes, BorderLayout.CENTER);
			instance.setLocation(point);
			instance.setVisible(true);
			instance.pack();

		}

		public void setNotes(String newNotes) {
			notes.setText(newNotes);
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
					nw = NoteWindow.getInstance(d.getNotes(), p);

					if (null != nw) {
						nw.repaint();
					}
				}
			}

		}

	}
}