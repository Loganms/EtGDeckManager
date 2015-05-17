package com.thaplayaslaya.gui;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.thaplayaslaya.DeckManager;

public class LabelImage extends JLabel implements ILabelImage {

	private static final long serialVersionUID = -7289961803730609648L;
	protected ImageIcon full;
	protected ImageMagnifier im;

	public LabelImage() {
		super();
	}

	public LabelImage(String text) {
		super(text);
	}

	public LabelImage(Icon image) {
		super(image);
	}

	public LabelImage(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	public LabelImage(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
	}

	public LabelImage(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		if (null != full) {
			im = new ImageMagnifier(full, DeckManager.getDeckManagerGUI());
		}
	}

	@Override
	public void mouseExited(MouseEvent event) {
		if (null != im) {
			im.dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}
}
