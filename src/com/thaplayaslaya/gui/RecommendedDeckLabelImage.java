package com.thaplayaslaya.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;

public class RecommendedDeckLabelImage extends LabelImage {

	private static final long serialVersionUID = -6450108372428180522L;
	private URL deckURL;
	private String deckCode = null;

	public RecommendedDeckLabelImage(URL url) {
		super();
		try {
			deckURL = url;
			BufferedImage temp = ImageIO.read(url);
			full = new ImageIcon(temp);
			// Custom personal-preference sub-image: includes first 3 columns
			// (30 cards)
			temp = temp.getSubimage(4, 2, 278, 245);
			int height = temp.getHeight() / 4;
			setIcon(new ImageIcon(temp.getScaledInstance(height, height, Image.SCALE_SMOOTH)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		addMouseListener(this);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		setToolTipText("Click to copy import code");
	}

	public String getDeckCode() {
		if (deckCode == null) {
			deckCode = Deck.convertURLToCode(this.deckURL);
			return deckCode;
		} else {
			return deckCode;
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.getDeckCode()), null);
		for (RecommendedDeckLabelImage li : DeckManager.getDeckManagerGUI().getOraclePanel().getRecommendedDeckImages()) {
			li.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		this.setBorder(BorderFactory.createLoweredBevelBorder());
	}
}
