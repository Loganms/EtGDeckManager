package com.thaplayaslaya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
				briefcase = new Case();
				briefcase.initializeNewFGCounterMap();
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
