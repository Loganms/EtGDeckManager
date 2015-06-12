package com.thaplayaslaya.gui;

import java.awt.Component;

import javax.swing.BorderFactory;
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
		System.out.println(deck.getName());
		Style style = deck.getStyle();
		Style.applyStyle(this, style);
		setText(deck.getName());

		if (cellHasFocus || isSelected)
			setBorder(BorderFactory.createEmptyBorder(0, 5, 2, 0));

		Style.applyFont(list, style);
		list.setSelectionForeground(style.getForegroundColor());
		list.setSelectionBackground(style.getBackgroundColor());

		return this;
	}
}
