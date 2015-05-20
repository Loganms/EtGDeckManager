package com.thaplayaslaya.gui;

import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.thaplayaslaya.DeckManager;

class ImageMagnifier extends JWindow {

	private static final long serialVersionUID = 3772686270645875087L;
	private static Container contentPane;
	private static JLabel label;
	private static ImageIcon full;
	private static final ImageMagnifier instance = new ImageMagnifier();

	private ImageMagnifier() {
		contentPane = getContentPane();
		setFocusableWindowState(false);
		
	}

	public static ImageMagnifier getInstance(ImageIcon imageFile) {
		if (null == label) {
			full = imageFile;
			instance.setSize(full.getIconWidth(), full.getIconHeight());
			readyInstance(imageFile);
			return instance;
		} else {
			contentPane.remove(label);
			readyInstance(imageFile);
			return instance;
		}
	}
	
	private static void readyInstance(ImageIcon imageFile) {
		label = new JLabel(imageFile);
		contentPane.add(label);
		
		instance.setLocation(DeckManager.getDeckManagerGUI().getSmartExternalWindowLocation(instance));
		instance.setVisible(true);
	}
	
	public static void cleanInstance() {
		if (null != instance) {
			instance.dispose();
		}
	}
}