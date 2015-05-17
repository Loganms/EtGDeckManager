package com.thaplayaslaya;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class FalseGodLabelImage extends LabelImage {

	@SuppressWarnings("unused")
	private URL deckURL;

	public FalseGodLabelImage() {
		super(" ? ", SwingConstants.CENTER);
		setMinimumSize(DEFAULT_ICON_IMAGE_SIZE);
		setPreferredSize(DEFAULT_ICON_IMAGE_SIZE);
		setFont(new Font(getFont().getFontName(), Font.PLAIN, 52));
		this.setOpaque(true);
		setBackground(Color.GRAY);
		setForeground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createRaisedBevelBorder());
	}

	public FalseGodLabelImage(URL url) {
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
		setToolTipText("Powers: 3x Mark, 2x Cards, 2x Draw");
	}
}
