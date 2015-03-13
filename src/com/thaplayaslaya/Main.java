package com.thaplayaslaya;

import javax.swing.SwingUtilities;

public class Main {

	static DeckManager DM;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createDeckManager();
			}
		});
	}

	private static void createDeckManager() {
		DM = new DeckManager();
	}
}
