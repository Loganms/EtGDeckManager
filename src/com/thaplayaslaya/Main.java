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

		// STRING TO CASE TEST

		/*
		 * Config cfg = new Config(); System.out.println(cfg.getCase());
		 * System.out.println(cfg.getCase().getDeckBinders()); for(DeckBinder db
		 * : cfg.getCase().getDeckBinders()){ System.out.println(db); for(Deck d
		 * : db.getDecks()){ System.out.println(d);
		 * System.out.println(d.getImportCode()); } }
		 */

		// CASE TO STRING TEST

		/*
		 * Case briefcase = new Case();
		 * 
		 * ArrayList<DeckBinder> dbs = new ArrayList<DeckBinder>(); DeckBinder
		 * Arena = new DeckBinder(); Arena.setName("Arena"); DeckBinder FGs =
		 * new DeckBinder(); FGs.setName("Gods");
		 * 
		 * Deck pestal = new Deck(); pestal.setName("Pestal");
		 * pestal.setImportCode(
		 * "622 7t6 7t6 7t6 7t6 7t6 7t6 7ta 7ta 7tb 7td 7td 7um 7um 7um 7um 7um 7um 7um 7um 7um 7um 808 808 808 808 80i 80i 80i 80i 8pu"
		 * );
		 * 
		 * Deck sgFractal = new Deck(); sgFractal.setName("sgFractal");
		 * sgFractal.setImportCode(
		 * "56i 56i 56i 56i 56i 58o 59m 59m 5aa 5aa 5fu 63a 63a 63a 63a 63a 63a 63a 63a 63a 752 786 7ee 7ee 7ee 7ee 7ee 80i 80i 80i 8pm"
		 * );
		 * 
		 * Arena.addDeck(pestal); Arena.addDeck(sgFractal);
		 * 
		 * Deck RolHope = new Deck(); RolHope.setName("ROL/HOPE");
		 * RolHope.setImportCode(
		 * "5la 5lk 5lk 5lk 5lk 61o 61o 61u 61u 622 622 622 622 63a 63a 63a 63a 63a 63a 7jp 7jp 7jp 7jp 7jp 7jp 7jq 7k2 808 808 808 808 80i 8pu"
		 * );
		 * 
		 * FGs.addDeck(RolHope);
		 * 
		 * dbs.add(Arena); dbs.add(FGs); briefcase.setDeckBinders(dbs);
		 * System.out.println(new Gson().toJson(briefcase));
		 */
	}

	private static void createDeckManager() {
		DM = new DeckManager();
	}
}
