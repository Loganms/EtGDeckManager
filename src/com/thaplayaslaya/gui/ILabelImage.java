package com.thaplayaslaya.gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.thaplayaslaya.DeckManager;

public interface ILabelImage extends MouseListener {

	static final Dimension DEFAULT_ICON_IMAGE_SIZE = new Dimension(65, 65);

	@Override
	public void mouseClicked(MouseEvent event);

	@Override
	public void mouseEntered(MouseEvent event);

	@Override
	public void mouseExited(MouseEvent event);

	@Override
	public void mousePressed(MouseEvent event);

	@Override
	public void mouseReleased(MouseEvent event);
}

class ImageMagnifier extends JWindow {

	private static final long serialVersionUID = 3772686270645875087L;
	private ImageIcon full;

	public ImageMagnifier(ImageIcon imageFile) {
		full = imageFile;
		getContentPane().add(new JLabel(full));

		setFocusableWindowState(false);
		setSize(full.getIconWidth(), full.getIconHeight());
		setLocation(DeckManager.getDeckManagerGUI().getSmartExternalWindowLocation(this));
		setVisible(true);
	}
}