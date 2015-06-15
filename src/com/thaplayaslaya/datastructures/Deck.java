package com.thaplayaslaya.datastructures;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

public class Deck implements IStylish {

	private static final String DEFAULT_DECK_NAME = "add new deck";
	public static final Deck DEFAULT = new Deck(DEFAULT_DECK_NAME);

	// For "View Deck" feature, cache deckImage just in case
	private transient BufferedImage deckImage;

	private String name;
	private Style style = new Style(UIManager.getColor("ComboBox.foreground"), UIManager.getColor("ComboBox.background"));
	private String importCode;
	private String notes;

	public Deck() {
	}

	// Copy constructor
	protected Deck(Deck d) {
		this.name = d.name;
		this.style = d.style.copy();
		this.importCode = d.importCode;
		this.notes = d.notes;
	}

	private Deck(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public String getImportCode() {
		return importCode;
	}

	public void setImportCode(String importCode) {
		if (this.importCode != null) {
			if (!this.importCode.equals(importCode)) {
				importCode = importCode.trim();
				importCode = importCode.replaceAll("[\\\t|\\\n|\\\r]", "");
				this.importCode = importCode;
				this.deckImage = null;
			}
		} else {
			importCode = importCode.trim();
			importCode = importCode.replaceAll("[\\\t|\\\n|\\\r]", "");
			this.importCode = importCode;
		}
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
			if (generateDeckImage()) {
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
			/*
			 * Use /deck/ over /cache/ /deck/ is always available /cache/ is
			 * only made after /deck/ has been called once.
			 */
			StringBuilder urlpath = new StringBuilder("http://dek.im/deck/");
			int i;
			String currentCard = null;
			int counter = 0;

			for (i = 0; i < cardArray.length - 1; i++) {
				currentCard = cardArray[i];
				if (currentCard.equals(cardArray[i + 1])) {
					counter++;
				} else {
					// 1-9 = 1-9
					if (counter > 0 && counter < 10) {
						urlpath.append("z" + counter + currentCard);
						// 10-35 = A-Y
					} else if (counter >= 10 && counter < 36) {
						char increment = (char) ('A' + (counter - 10));
						urlpath.append("z" + increment + currentCard);
						// >=36 = a-y
					} else if (counter >= 36) {
						char increment = (char) ('A' + (counter - 10) + 6);
						urlpath.append("z" + increment + currentCard);
					} else {
						urlpath.append(currentCard);
					}
					counter = 0;
				}
			}

			if (counter != 0) {
				if (counter > 0 && counter < 10) {
					urlpath.append("z" + counter + currentCard);
				} else if (counter >= 10 && counter < 36) {
					char increment = (char) ('A' + (counter - 10));
					urlpath.append("z" + increment + currentCard);
				} else if (counter >= 36) {
					char increment = (char) ('A' + (counter - 10) + 6);
					urlpath.append("z" + increment + currentCard);
				}
			} else {
				urlpath.append(cardArray[i] + ".png");
			}

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

	public static String convertURLToCode(URL url) {
		char[] link = url.toString().substring(20, url.toString().length() - 4).toCharArray();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < link.length; i++) {
			if (link[i] == 'z') {
				for (int n = 0; n < charToNumber(link[i + 1]); n++) {
					sb.append("" + link[i + 2] + link[i + 3] + link[i + 4] + ' ');
				}
				i = i + 4;
			} else {
				sb.append("" + link[i] + link[i + 1] + link[i + 2] + ' ');
				i = i + 2;
			}
		}
		return sb.toString().trim();
	}

	// returns -1 if something goes wrong
	private static int charToNumber(char c) {
		if (c >= '1' && c <= '9') {
			return c - 47;
		} else if (c >= 'A' && c <= 'Z') {
			return c - 54;
		} else if (c >= 'a' && c <= 'y') {
			return c - 60;
		} else {
			System.out.println("From Deck.java - Function \"charToNumber\" has failed");
			return -1;
		}
	}

	@Override
	public Deck clone() {
		return new Deck(this);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
