package com.thaplayaslaya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.thaplayaslaya.datastructures.Case;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.gui.DeckManagerMenuBar;

public class Config {

	private static final String cfgFile = "cfg.json";
	private static Case briefcase;
	private File file;
	private String json;
	private Gson gson = DeckManager.gson;

	public Config() {
		file = new File(cfgFile);
		if (!file.isFile()) {

			JOptionPane.showMessageDialog(new JFrame(), "No cfg.json file can be found.\n EtG Deck Manager will create a new one.");
			System.out.println("no file");

			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			briefcase = new Case();
			briefcase.initializeNewFGCounterMap();

			((DeckManagerMenuBar) DeckManager.getDeckManagerGUI().getJMenuBar()).getDeckImagesOptions()[DeckManagerMenuBar
					.preferenceToIntCode(briefcase.getPreferredDeckImageLocation())].doClick();
			((DeckManagerMenuBar) DeckManager.getDeckManagerGUI().getJMenuBar()).getDeckImagesOptions()[DeckManagerMenuBar
					.preferenceToIntCode(briefcase.getPreferredDeckImageLocationMod())].doClick();

		} else {
			readCaseData();
		}
	}

	public Case getCase() {
		return briefcase;
	}

	private void readCaseData() {
		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			briefcase = gson.fromJson(br, Case.class);

			if (null == briefcase) {
				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"Your cfg.json file is probably empty. EtG Deck Manager will attemp to populate the file now.\nIf you think this is an error do not click OK.\nSave a copy of your cfg.json file outside of this program's directory and contact the creator of this program.\nOnce a copy of the cfg.json file is created, feel free to continue.",
								"Deck Manager Error", JOptionPane.ERROR_MESSAGE);
				briefcase = new Case();
			}

			if (null == briefcase.VERSION_ID || !briefcase.VERSION_ID.equals(DeckManager.VERSION_ID)) {
				JOptionPane.showMessageDialog(new JFrame(), "The version of your save data does not match the version of your Deck Manager.\n"
						+ "You will now be updated from " + briefcase.VERSION_ID + " to " + DeckManager.VERSION_ID + ".", "Deck Manager Update",
						JOptionPane.INFORMATION_MESSAGE);
				// TODO: Don't forget to work on this later
				Update.updateFrom(briefcase.VERSION_ID);

			}

			for (DeckBinder db : briefcase.getDeckBinders()) {
				db.setDBP();
			}

			((DeckManagerMenuBar) DeckManager.getDeckManagerGUI().getJMenuBar()).getDeckImagesOptions()[DeckManagerMenuBar
					.preferenceToIntCode(briefcase.getPreferredDeckImageLocation())].doClick();
			((DeckManagerMenuBar) DeckManager.getDeckManagerGUI().getJMenuBar()).getDeckImagesOptions()[DeckManagerMenuBar
					.preferenceToIntCode(briefcase.getPreferredDeckImageLocationMod())].doClick();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile() {
		briefcase.prepareForSave();
		json = DeckManager.gson.toJson(briefcase);
		try {
			if (!file.isFile()) {
				file.createNewFile();
			}

			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
