package com.thaplayaslaya.datastructures;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.UIManager;

public class Style {

	private Color foregroundColor;
	private Color backgroundColor;
	private boolean isBold = true;
	private boolean isItalic = false;
	private boolean isUnderline = false;
	private boolean isStrikethrough = false;

	public Style() {
	}

	// Copy constructor
	private Style(Style s) {
		this.foregroundColor = s.foregroundColor;
		this.backgroundColor = s.backgroundColor;
		this.isBold = s.isBold;
		this.isItalic = s.isItalic;
		this.isUnderline = s.isUnderline;
		this.isStrikethrough = s.isStrikethrough;
	}

	public Style(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public Style(Color foregroundColor, Color backgroundColor) {
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}

	public Style(Color foregroundColor, Color backgroundColor, boolean isBold) {
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.isBold = isBold;
	}

	public Style(Color foregroundColor, Color backgroundColor, boolean isBold, boolean isItalic) {
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.isBold = isBold;
		this.isItalic = isItalic;
	}

	public Style(Color foregroundColor, Color backgroundColor, boolean isBold, boolean isItalic, boolean isUnderline) {
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.isBold = isBold;
		this.isItalic = isItalic;
		this.isUnderline = isUnderline;
	}

	public Style(Color foregroundColor, Color backgroundColor, boolean isBold, boolean isItalic, boolean isUnderline, boolean isStrikethrough) {
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.isBold = isBold;
		this.isItalic = isItalic;
		this.isUnderline = isUnderline;
		this.isStrikethrough = isStrikethrough;
	}

	public static void applyFont(JComponent c, Style s) {
		Font font = new Font(c.getFont().getFamily(), (s.isBold() ? Font.BOLD : 0) | (s.isItalic() ? Font.ITALIC : 0), c.getFont().getSize());
		@SuppressWarnings("unchecked")
		Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
		if (s.isUnderline()) {
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		}
		if (s.isStrikethrough()) {
			attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		}
		c.setFont(new Font(attributes));
	}

	public static void applyStyle(JComponent c, Style s) {
		c.setOpaque(true);
		c.setForeground(s.getForegroundColor());
		c.setBackground(s.getBackgroundColor());
		if (c instanceof JComboBox) {
			c.getComponent(0).setBackground(UIManager.getColor("ComboBox.background"));
		}
		applyFont(c, s);
	}

	public Style copy() {
		return new Style(this);
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	public boolean isItalic() {
		return isItalic;
	}

	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}

	public boolean isUnderline() {
		return isUnderline;
	}

	public void setUnderline(boolean isUnderline) {
		this.isUnderline = isUnderline;
	}

	public boolean isStrikethrough() {
		return isStrikethrough;
	}

	public void setStrikethrough(boolean isStrikethrough) {
		this.isStrikethrough = isStrikethrough;
	}
}
