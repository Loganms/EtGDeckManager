package com.thaplayaslaya;
import java.util.ArrayList;

public class Case {

	private ArrayList<DeckBinder> deckBinders;
	
	public Case() {
		
	}
	
	public void addDeckBinder(DeckBinder deckBinder) {
		this.deckBinders.add(deckBinder);
	}
	
	public void addDeck(Deck deck, DeckBinder deckBinder) {
		loop:
		for(DeckBinder db: this.deckBinders) {
			if(db.equals(deckBinder)){
				db.addDeck(deck);
				break loop;
			}
		}
	}
	
	public ArrayList<DeckBinder> getDeckBinders() {
		return deckBinders;
	}
	
	public DeckBinder getDeckBinder(String name) {
		for (DeckBinder db : this.deckBinders) {
			if (db.getName().equals(name)){
				return db;
			}
		}
		return null;
		
	}
	
	public void setDeckBinders(ArrayList<DeckBinder> deckBinders) {
		this.deckBinders = deckBinders;
	}

	/*public void addToPanel(JPanel leftPanel) {
		for(DeckBinder db : deckBinders) {
			db.addAsPanelTo(leftPanel);
		}
	}*/
	
	public boolean containsDeckBinder(String name) {
		for( DeckBinder db : this.getDeckBinders()) {
			if(db.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
}
