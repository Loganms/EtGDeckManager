package com.thaplayaslaya.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FGCounterMap {

	private Map<String, ArrayList<FGCounterDeck>> counterMap = new HashMap<String, ArrayList<FGCounterDeck>>();

	public FGCounterMap() {

	}

	public ArrayList<FGCounterDeck> getFGCounterDeckList(String name) {
		return counterMap.get(name);
	}

	public void addFGCounterDeck(String nameofFG, FGCounterDeck counter) {
		counterMap.get(nameofFG).add(counter);
	}

}
