package com.thaplayaslaya.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.thaplayaslaya.DeckManager;

public interface ILabelImage extends MouseListener {

	static final Dimension DEFAULT_ICON_IMAGE_SIZE = new Dimension(65, 65);

	public void mouseClicked(MouseEvent event);

	public void mouseEntered(MouseEvent event);

	public void mouseExited(MouseEvent event);

	public void mousePressed(MouseEvent event);

	public void mouseReleased(MouseEvent event);
}

@SuppressWarnings("serial")
class ImageMagnifier extends JWindow {
	ImageIcon full;
	int ploc = DeckManagerMenuBar.preferenceToIntCode(DeckManager.getDeckManagerGUI().getPreferredDeckImageLocation());
	int pmod = DeckManagerMenuBar.preferenceToIntCode(DeckManager.getDeckManagerGUI().getPreferredDeckImageLocationMod());

	public ImageMagnifier(ImageIcon imageFile, JFrame parent) {
		full = imageFile;
		getContentPane().add(new JLabel(full));
		Point loc = parent.getLocation();

		if (ploc == DeckManagerMenuBar.TOP) {
			loc.y -= full.getIconHeight();
		} else if (ploc == DeckManagerMenuBar.BOTTOM) {
			loc.y += parent.getHeight();
		} else if (ploc == DeckManagerMenuBar.LEFT) {
			loc.x -= full.getIconWidth();
		} else if (ploc == DeckManagerMenuBar.RIGHT) {
			loc.x += parent.getWidth();
		}

		if (pmod == DeckManagerMenuBar.CENTER) {
			if (ploc == DeckManagerMenuBar.TOP || ploc == DeckManagerMenuBar.BOTTOM) {
				loc.x = loc.x + (parent.getWidth() / 2) - (full.getIconWidth() / 2);
			} else if (ploc == DeckManagerMenuBar.LEFT || ploc == DeckManagerMenuBar.RIGHT) {
				loc.y = loc.y + parent.getHeight() / 2 - full.getIconHeight() / 2;
			}
		} else if (pmod == DeckManagerMenuBar.FLUSH_RIGHT) {
			loc.x = loc.x + parent.getWidth() - full.getIconWidth();
		} else if (pmod == DeckManagerMenuBar.FLUSH_BOTTOM) {
			loc.y = loc.y + parent.getHeight() - full.getIconHeight();
		}

		setFocusableWindowState(false);
		setLocation(loc);
		setSize(full.getIconWidth(), full.getIconHeight());
		setVisible(true);
	}
}