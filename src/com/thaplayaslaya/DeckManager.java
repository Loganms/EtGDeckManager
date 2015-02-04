package com.thaplayaslaya;
import java.util.List;

import com.google.gson.Gson;


public class DeckManager {
	
	// Having issues with deckbinderpanel not refreshing
	
	private static DeckManagerGUI DMGUI;
	List<DeckBinder> deckBinders;
	public static Gson gson;
	public static Config cfg;

	
	public DeckManager() {
		init();
	}
	
	private static void init() {
		gson = new Gson();
		cfg = new Config();
		setDeckManagerGUI();
	}
	
	public static DeckManagerGUI getDeckManagerGUI() {
		return DMGUI;
	}
	
	public static void setDeckManagerGUI() {
		DMGUI = new DeckManagerGUI();
	}
}
