package com.thaplayaslaya.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.OperationType;

public class CounterDeckLabelImage extends LabelImage {

	private static final long serialVersionUID = 5301128933866476628L;
	public static final CounterDeckLabelImage DEFAULT = new CounterDeckLabelImage();
	private String deckCode = null;

	public CounterDeckLabelImage() {
		super(" + ", SwingConstants.CENTER);
		setMinimumSize(DEFAULT_ICON_IMAGE_SIZE);
		setPreferredSize(DEFAULT_ICON_IMAGE_SIZE);
		setFont(new Font(getFont().getFontName(), Font.PLAIN, 52));
		this.setOpaque(true);
		setBackground(Color.GRAY);
		setForeground(Color.LIGHT_GRAY);
		addMouseListener(this);
		setBorder(BorderFactory.createRaisedBevelBorder());
		setToolTipText("Click to add a new deck");
	}

	public CounterDeckLabelImage(Deck deck) {
		super();
		try {
			deckCode = deck.getImportCode();
			BufferedImage temp = deck.getDeckImage();
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
		return deckCode;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (null == deckCode) {
			new CustomDialog(DeckManager.getDeckManagerGUI(), OperationType.ADD_NEW_FG_COUNTER_DECK, DeckManager.getDeckManagerGUI().getOraclePanel().getCurrentlySelectedFG().name());
		} else {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.getDeckCode()), null);
			for (CounterDeckLabelImage li : DeckManager.getDeckManagerGUI().getOraclePanel().getCounterDeckImages()) {
				li.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			this.setBorder(BorderFactory.createLoweredBevelBorder());
		}
	}
}
