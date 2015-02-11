package com.thaplayaslaya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Config {

	private String cfgFile = "cfg.json";
	private File file;
	private Case briefcase;
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
		// reconcile changes to json (or maybe just the briefcase?)
		// json = DeckManager.gson.toJson(briefcase);
		// put json back into cfg.json

		json = DeckManager.gson.toJson(briefcase);

		try {
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
