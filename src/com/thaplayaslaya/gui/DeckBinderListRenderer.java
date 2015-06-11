package com.thaplayaslaya.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.Style;

public class DeckBinderListRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 6745658636301831991L;

	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		Deck deck = (Deck) value;
		Style style = deck.getStyle();
		Font font = new Font(getFont().getFamily(), (style.isBold() ? Font.BOLD : 0) | (style.isItalic() ? Font.ITALIC : 0), getFont().getSize());
		@SuppressWarnings("unchecked")
		Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
		if (style.isUnderline()) {
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		}
		if (style.isStrikethrough()) {
			attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		}
		setFont(new Font(attributes));
		setText(deck.getName());
		setForeground(style.getForegroundColor());
		setBackground(style.getBackgroundColor());
		return this;
	}
}
