package com.thaplayaslaya;

import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultEditorKit;

public class Main {

	static DeckManager DM;

	public static void main(String[] args) {

		String OS = System.getProperty("os.name").toLowerCase();

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		if (OS.indexOf("win") >= 0) {
			System.out.println("OS is Win");
			Util.isMac = false;
			Util.keyMod = "Ctrl";
		}

		if (OS.indexOf("mac") >= 0) {
			System.out.println("OS is Mac");
			Util.isMac = true;
			Util.keyMod = "Cmd";
			String[] properties = { "TextField.focusInputMap", "TextArea.focusInputMap" };
			for (int i = 0; i < 2; i++) {
				InputMap im = (InputMap) UIManager.get(properties[i]);
				im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, Util.MENU_SHORTCUT_KEY_MASK), DefaultEditorKit.copyAction);
				im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Util.MENU_SHORTCUT_KEY_MASK), DefaultEditorKit.pasteAction);
				im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, Util.MENU_SHORTCUT_KEY_MASK), DefaultEditorKit.cutAction);
			}

		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createDeckManager();
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				// Mostly for Command+Q but saves on End Task as well
				DeckManager.save();
			}
		});
	}

	private static void createDeckManager() {
		DM = new DeckManager();
	}
}
