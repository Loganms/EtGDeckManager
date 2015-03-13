package com.thaplayaslaya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Config {

	private static final String cfgFile = "cfg.json";
	private static Case briefcase;
	private File file;
	private String json;
	private Gson gson = DeckManager.gson;

	public Config() {
		file = new File(cfgFile);
		readCaseData();
	}

	public Case getCase() {
		return briefcase;
	}

	private void readCaseData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			briefcase = gson.fromJson(br, Case.class);

			for (DeckBinder db : briefcase.getDeckBinders()) {
				db.setDBP();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile() {
		json = DeckManager.gson.toJson(briefcase);

		try {
			//TODO:
			//"document.json" is a test file will be replaced
			//with file field created above in final program.
			FileWriter writer = new FileWriter("document.json");
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
