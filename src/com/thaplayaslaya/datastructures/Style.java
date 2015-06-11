package com.thaplayaslaya.datastructures;

import java.awt.Color;

import javax.swing.UIManager;

public class Style {

	private Color foregroundColor = UIManager.getColor("Label.foreground");
	private Color backgroundColor = UIManager.getColor("Label.background");
	private boolean isBold = true;
	private boolean isItalic = false;
	private boolean isUnderline = false;
	private boolean isStrikethrough = false;

	public Style() {
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
