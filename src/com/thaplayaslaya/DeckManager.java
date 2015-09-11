package com.thaplayaslaya;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thaplayaslaya.datastructures.Case;
import com.thaplayaslaya.gui.DeckManagerGUI;

public class DeckManager {

	public static final String VERSION_ID = "v1.0.6";
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

	public static void save() {
		System.out.println("Config is Saving");
		cfg.writeToFile();
	}
}
