package com.thaplayaslaya;

import java.util.List;

import com.google.gson.Gson;

public class DeckManager {

	// Having issues with deckbinderpanel not refreshing

	private static DeckManagerGUI DMGUI;
	// List<DeckBinder> deckBinders;
	public static Gson gson;
	public static Config cfg;

	public DeckManager() {
		init();

		System.out.print("<From DeckManager line 19> DeckBinders: [ ");
		for (DeckBinder db : cfg.getCase().getDeckBinders()) {
			System.out.print(db.getName() + " ");
		}
		System.out.println("]");
	}

	private static void init() {
		gson = new Gson();
		setDeckManagerGUI();
		cfg = new Config();
		DMGUI.setComponents();

	}

	public static DeckManagerGUI getDeckManagerGUI() {
		return DMGUI;
	}

	public static void setDeckManagerGUI() {
		DMGUI = new DeckManagerGUI();
	}
}
