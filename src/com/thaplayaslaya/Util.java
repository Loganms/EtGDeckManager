package com.thaplayaslaya;

import java.util.Collections;
import java.util.Queue;

import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.gui.dialogs.SortDialog;

public class Util {

	public static final String[] ELEMENTS = new String[] {
			"Entropy",
			"Death",
			"Gravity",
			"Earth",
			"Life",
			"Fire",
			"Water",
			"Light",
			"Air",
			"Time",
			"Darkness",
			"Aether" };

	public static final String[] MARK_CODES = new String[] { "8pj", "8pk", "8pl", "8pm", "8pn", "8po", "8pp", "8pq", "8pr", "8ps", "8pt", "8pu" };

	public static void Sort(DeckBinder currentlySelectedDeckBinder, Queue<String> sortList) {
		
		if (!sortList.isEmpty()){
			String sortOption = sortList.peek();
			sortList.remove();
			
			if (sortOption.equals(SortDialog.levelOneOptions[0])){
				sortOption = sortList.peek();
				sortList.remove();
				if (sortOption.equals(SortDialog.alphaOptions[0])){
					System.out.println(currentlySelectedDeckBinder.getDecks());
					Collections.sort(currentlySelectedDeckBinder.getDecks(), new Deck.DeckNameComparator(1));
					System.out.println(currentlySelectedDeckBinder.getDecks());
				} else if (sortOption.equals(SortDialog.alphaOptions[1])){
					System.out.println(currentlySelectedDeckBinder.getDecks());
					Collections.sort(currentlySelectedDeckBinder.getDecks(), new Deck.DeckNameComparator(-1));
					System.out.println(currentlySelectedDeckBinder.getDecks());
				}
			} else if (sortOption.equals(SortDialog.levelOneOptions[1])){
				sortOption = sortList.peek();
				sortList.remove();
				if (sortOption.equals(SortDialog.mostOptions[0])){
					Collections.sort(currentlySelectedDeckBinder.getDecks(), new Deck.DeckNameComparator(1));
				} else if (sortOption.equals(SortDialog.mostOptions[1])){
					
				} else if (sortOption.equals(SortDialog.mostOptions[2])){
					
				} else if (sortOption.equals(SortDialog.mostOptions[3])){
					
				} 
				
			} else if (sortOption.equals(SortDialog.levelOneOptions[2])){
				sortOption = sortList.peek();
				sortList.remove();	
			}
		} else {
			return;
		}
		Sort(currentlySelectedDeckBinder, sortList);
	}

}
