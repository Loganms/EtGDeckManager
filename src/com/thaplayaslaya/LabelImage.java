package com.thaplayaslaya;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LabelImage extends JLabel implements ILabelImage {

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
		im = new ImageMagnifier(full, DeckManager.getDeckManagerGUI());
	}

	@Override
	public void mouseExited(MouseEvent event) {
		im.dispose();
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}
}
