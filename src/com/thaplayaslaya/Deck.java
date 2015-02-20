package com.thaplayaslaya;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class Deck {

	// private transient final Deck DEFAULT;
	private transient BufferedImage deckImage;
	private transient static String defaultDeckName = "add new deck";
	private String name;
	private String importCode;

	public Deck() {
		name = "[No Name Set]";
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImportCode() {
		return importCode;
	}

	public void setImportCode(String importCode) {
		if(!this.importCode.equals(importCode)){
			this.importCode = importCode;
			this.deckImage = null;
		}
	}

	public static Deck getDefaultDeck() {
		Deck deck = new Deck();
		deck.setName(defaultDeckName);
		return deck;
	}

	public boolean hasDeckImage() {
		if (deckImage == null) {
			return false;
		} else {
			return true;
		}
	}

	public BufferedImage getDeckImage() {
		if (this.hasDeckImage()) {
			return deckImage;
		} else {
			if(generateDeckImage()) {
				return deckImage;
			} else {
				return null;
			}
		}
	}

	public boolean generateDeckImage() {
		String[] cardArray;
		if (importCode != null && importCode.length() > 1) {
			cardArray = importCode.split(" ");
			StringBuilder urlpath = new StringBuilder("http://dek.im/deck/");
			int i;
			String currentCard;
			int counter = 0;

			for (i = 0; i < cardArray.length - 1; i++) {
				currentCard = cardArray[i];
				if (currentCard.equals(cardArray[i + 1])) {
					counter++;
				} else {
					if (counter > 0) {
						urlpath.append("z" + counter + currentCard);
					} else {
						urlpath.append(currentCard);
					}
					counter = 0;
				}
			}

			urlpath.append(cardArray[i] + ".png");
			System.out.println(urlpath.toString());

			try {
				URL url = new URL(urlpath.toString());
				InputStream inputStream = url.openStream();
				deckImage = ImageIO.read(inputStream);
				return true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("no importCode is set for " + this.name);
			return false;
		}
		return false;

	}

	public String toString() {
		return this.name;
	}
}
