package com.thaplayaslaya;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	static DeckManager DM;

	public static void main(String[] args) {

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

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createDeckManager();
			}
		});
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				//Mostly for Command+Q but saves on End Task as well
				DeckManager.saveAndExit();
			}
		});
	}

	private static void createDeckManager() {
		DM = new DeckManager();
	}
}
