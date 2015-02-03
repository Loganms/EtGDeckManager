package com.thaplayaslaya;
import java.util.List;

import com.google.gson.Gson;


public class DeckManager {
	
	//NOTES: adding func. to button "rename"
	// Having issues with deckbinderpanel not refreshing
	//trying to find way to have list of dbp s so that i 
	//can reference them individually, good luck.
	
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
