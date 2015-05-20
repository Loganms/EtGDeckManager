package com.thaplayaslaya.gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
