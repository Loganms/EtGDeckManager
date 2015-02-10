package com.thaplayaslaya;
import java.util.ArrayList;

public class DeckBinder {

	private String name;
	transient DeckBinderPanel dBP;
	private ArrayList<Deck> decks = new ArrayList<Deck>();
	
	public DeckBinder(){
		
	}
	
	//If this constructor is called, it is because
	//the user made a new DeckBinder.
	public DeckBinder(String name) {
		this.name = name;
		DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(name);
		
		DeckManager.cfg.getCase().addDeckBinder(this);
		
		dBP = new DeckBinderPanel(this.name);
		finalizeDBSetup();
		
	}

	//called once Config has populated Case completely with JSON data.
	public void setDBP() {
		dBP = new DeckBinderPanel(this.name);
		for(Deck d : decks) {
			dBP.getComboBox().addItem(d);
		}
		System.out.println("adding default deck to " + name);
		finalizeDBSetup();
	}
	
	private void finalizeDBSetup() {
		dBP.getComboBox().addItem(Deck.getDefaultDeck());
		dBP.setListeners();
	}

	public void addDeck(Deck deck) {
		decks.add(deck);
		//not the most elegant way, I know.
		dBP.getComboBox().removeItem(dBP.getComboBox().getItemAt(dBP.getComboBox().getItemCount() - 1));
		dBP.getComboBox().addItem(deck);
		dBP.getComboBox().addItem(Deck.getDefaultDeck());
	}
	
	public void removeDeck(Deck deck) {
		decks.remove(deck);
		dBP.getComboBox().removeItem(deck);
		
	}
	
	public void setName(String name) {
		System.out.println("Setting name of DBP and DB");
		this.name = name;
		dBP.setName(name);
		
	}
		
	public String getName() {
		return name;
	}

	public Deck getDeck(String name) {
		for (Deck d : this.decks) {
			if (d.getName().equals(name)){
				return d;
			}
		}
		return null;
		
	}
	
	private DeckBinderPanel getDeckBinderPanel(){
		
		return dBP;
		/*for( DeckBinderPanel dbp: DeckManager.getDeckManagerGUI().getDeckBinderPanels()) {
			if(dbp.getName().equals(this.dBName)){
				return dbp;
			}
		}
		return null;*/
	}
	
	public ArrayList<Deck> getDecks() {
		return decks;
	}

	public void setDecks(ArrayList<Deck> decks) {
		for(Deck d : decks) {
			this.addDeck(d);
		};
	}

	/*public void addAsPanelTo(JPanel panel) {
		DeckBinderPanel DBP = new DeckBinderPanel(this);
		panel.add(DBP);
	}*/
	
	public boolean containsDeck(String name){
		for( Deck d : this.getDecks()) {
			if(d.getName().equals(name)){
				return true;
			}
		}
		return false;
	}

	public void addNewDeck() {
		CustomDialog cd = new CustomDialog(null);
		cd.setVisible(true);
	}
}
