package com.thaplayaslaya;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DeckManager {

	public static final String VERSION_ID = "v1.0.2";
	private static DeckManagerGUI DMGUI;
	public static Gson gson;
	public static Config cfg;

	public DeckManager() {
		init();
	}

	private static void init() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		DMGUI = new DeckManagerGUI();
		cfg = new Config();
		DMGUI.setComponents();
	}

	public static Case getCase() {
		return cfg.getCase();
	}

	public static DeckManagerGUI getDeckManagerGUI() {
		return DMGUI;
	}

	public static void saveAndExit() {
		cfg.writeToFile();
		System.exit(0);
	}
}
