package com.thaplayaslaya;

import com.google.gson.Gson;

public class DeckManager {

	private static DeckManagerGUI DMGUI;
	public static Gson gson;
	public static Config cfg;

	public DeckManager() {
		init();
	}

	private static void init() {
		gson = new Gson();
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
}
