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
import javax.swing.border.Border;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.OperationType;
import com.thaplayaslaya.gui.dialogs.CustomDialog;

public class CounterDeckLabelImage extends LabelImage {

	private static final long serialVersionUID = 5301128933866476628L;
	public static final CounterDeckLabelImage DEFAULT = new CounterDeckLabelImage();
	public static final Border DEFAULT_BORDER = BorderFactory.createRaisedBevelBorder();
	public static final Border DEFAULT_SELECTED_BORDER = BorderFactory.createLoweredBevelBorder();

	private String deckCode = null;
	private Deck deck = null;

	public CounterDeckLabelImage() {
		super(" + ", SwingConstants.CENTER);
		setMinimumSize(DEFAULT_ICON_IMAGE_SIZE);
		setPreferredSize(DEFAULT_ICON_IMAGE_SIZE);
		setFont(new Font(getFont().getFontName(), Font.PLAIN, 52));
		this.setOpaque(true);
		setBackground(Color.GRAY);
		setForeground(Color.LIGHT_GRAY);
		addMouseListener(this);
		setBorder(DEFAULT_BORDER);
		setToolTipText("Click to add a new deck");
	}

	public CounterDeckLabelImage(Deck deck) {
		super();
		try {
			this.deck = deck;
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

		this.setBorder(DEFAULT_BORDER);
		setToolTipText("Click to copy import code");
	}

	public String getDeckCode() {
		return deckCode;
	}

	public Deck getDeck() {
		return deck;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (null == deckCode) {
			new CustomDialog(DeckManager.getDeckManagerGUI(), OperationType.ADD_NEW_FG_COUNTER_DECK, DeckManager.getDeckManagerGUI().getOraclePanel()
					.getCurrentlySelectedFG().name());
		} else {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.getDeckCode()), null);
			OraclePanel op = DeckManager.getDeckManagerGUI().getOraclePanel();
			if (null != op.getCurrentlySelectedDeck()) {
				op.getCurrentlySelectedDeck().setBorder(DEFAULT_BORDER);
			}
			this.setBorder(DEFAULT_SELECTED_BORDER);
			DeckManager.getDeckManagerGUI().getOraclePanel().setCurrentlySelectedDeck(this);
		}
	}

	public boolean isSameAs(CounterDeckLabelImage cdli) {
		if (null != cdli.getDeck() && cdli.getDeck().equals(getDeck())) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getDeckCode();
	}

}
