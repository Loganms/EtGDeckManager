package com.thaplayaslaya;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
class LabelImage extends JLabel implements MouseListener {

	private static final Dimension DEFAULT_ICON_IMAGE_SIZE = new Dimension(65, 65);
	private URL deckURL;
	private String deckCode = null;
	private ImageIcon full;
	private ImageMagnifier im;
	private boolean clickable = true;
	private boolean isGod;

	public LabelImage() {
		super(" ? ", SwingConstants.CENTER);
		setMinimumSize(DEFAULT_ICON_IMAGE_SIZE);
		setPreferredSize(DEFAULT_ICON_IMAGE_SIZE);
		setFont(new Font(getFont().getFontName(), Font.PLAIN, 52));
		this.setOpaque(true);
		setBackground(Color.GRAY);
		setForeground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createRaisedBevelBorder());
	}

	public LabelImage(URL url) {
		super();
		try {
			deckURL = url;
			BufferedImage temp = ImageIO.read(url);
			full = new ImageIcon(temp);
			// Custom personal-preference sub-image: includes first 3 columns
			// (30 cards)
			temp = temp.getSubimage(4, 2, 278, 245);
			int height = temp.getHeight()/4;
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

	public void mouseClicked(MouseEvent event) {
		if (this.isClickable()) {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.getDeckCode()), null);
			for (LabelImage li : DeckManager.getDeckManagerGUI().getOraclePanel().getImages()) {
				li.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			this.setBorder(BorderFactory.createLoweredBevelBorder());
		}
	}

	public void mouseEntered(MouseEvent event) {
		im = new ImageMagnifier(full, DeckManager.getDeckManagerGUI(), isGod);
	}

	public void mouseExited(MouseEvent event) {
		im.dispose();
	}

	public void mousePressed(MouseEvent event) {
	}

	public void mouseReleased(MouseEvent event) {
	}

	public void setClickable(boolean b) {
		this.clickable = b;
	}

	public boolean isClickable() {
		return this.clickable;
	}

	public void setGod(boolean b) {
		this.isGod = b;
	}

	public boolean isGod() {
		return this.isGod;
	}
}

@SuppressWarnings("serial")
class ImageMagnifier extends JFrame {
	ImageIcon full;
	int ploc = DeckManagerMenuBar.preferenceToIntCode(DeckManager.getDeckManagerGUI().getPreferredDeckImageLocation());
	int pmod = DeckManagerMenuBar.preferenceToIntCode(DeckManager.getDeckManagerGUI().getPreferredDeckImageLocationMod());

	public ImageMagnifier(ImageIcon imageFile, JFrame parent, boolean isGod) {
		setUndecorated(true);
		full = imageFile;
		getContentPane().add(new JLabel(full));
		Point loc = parent.getLocation();

		if (ploc == DeckManagerMenuBar.TOP) {
			loc.y -= full.getIconHeight();
		} else if (ploc == DeckManagerMenuBar.BOTTOM) {
			loc.y += parent.getHeight();
		} else if (ploc == DeckManagerMenuBar.LEFT) {
			loc.x -= full.getIconWidth();
		} else if (ploc == DeckManagerMenuBar.RIGHT) {
			loc.x += parent.getWidth();
		}

		if (pmod == DeckManagerMenuBar.CENTER) {
			if (ploc == DeckManagerMenuBar.TOP || ploc == DeckManagerMenuBar.BOTTOM) {
				loc.x = loc.x + (parent.getWidth() / 2) - (full.getIconWidth() / 2);
			} else if (ploc == DeckManagerMenuBar.LEFT || ploc == DeckManagerMenuBar.RIGHT) {
				loc.y = loc.y + parent.getHeight() / 2 - full.getIconHeight() / 2;
			}
		} else if (pmod == DeckManagerMenuBar.FLUSH_RIGHT) {
			loc.x = loc.x + parent.getWidth() - full.getIconWidth();
		} else if (pmod == DeckManagerMenuBar.FLUSH_BOTTOM) {
			loc.y = loc.y + parent.getHeight() - full.getIconHeight();
		}

		setLocation(loc);
		setSize(full.getIconWidth(), full.getIconHeight());
		setVisible(true);
	}
}