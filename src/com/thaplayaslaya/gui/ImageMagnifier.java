package com.thaplayaslaya.gui;

import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

import com.thaplayaslaya.DeckManager;

public class ImageMagnifier extends JWindow {

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

		// Check if image will obscure where the mouse is hovering
		Rectangle r1 = new Rectangle(instance.getX(), instance.getY(), instance.getWidth(), instance.getHeight());
		if (r1.contains(MouseInfo.getPointerInfo().getLocation())) {
			JOptionPane.showMessageDialog(DeckManager.getDeckManagerGUI(),
					"There is not enough screen space to show the preview image.\nChange your View options or try moving the window.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		instance.setVisible(true);
	}

	public static void cleanInstance() {
		if (null != instance) {
			instance.dispose();
		}
	}
}