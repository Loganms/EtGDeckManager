package com.thaplayaslaya.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JToggleButton;

import com.thaplayaslaya.datastructures.IStylish;

public class FontEffectActionListener implements ActionListener {

	private Component comp;
	private IStylish datastruct;

	public FontEffectActionListener(Component c, IStylish styleObject) {
		super();
		this.comp = c;
		this.datastruct = styleObject;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void actionPerformed(ActionEvent e) {
		JToggleButton btn = (JToggleButton) e.getSource();
		boolean activate = btn.getModel().isSelected();
		Font f = comp.getFont();
		switch (btn.getText()) {
		case "B":
			comp.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
			datastruct.getStyle().setBold(comp.getFont().isBold());
			break;
		case "I":
			comp.setFont(f.deriveFont(f.getStyle() ^ Font.ITALIC));
			datastruct.getStyle().setItalic(comp.getFont().isItalic());
			break;
		case "U":
			Map attributes = f.getAttributes();
			if (activate) {
				attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				datastruct.getStyle().setUnderline(true);
			} else {
				attributes.put(TextAttribute.UNDERLINE, -1);
				datastruct.getStyle().setUnderline(false);
			}
			comp.setFont(f.deriveFont(attributes));
			break;
		case "abc":
			Map attributes1 = f.getAttributes();
			if (activate) {
				attributes1.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
				datastruct.getStyle().setStrikethrough(true);
			} else {
				attributes1.put(TextAttribute.STRIKETHROUGH, -1);
				datastruct.getStyle().setStrikethrough(false);
			}
			comp.setFont(f.deriveFont(attributes1));
			break;
		default:
			break;
		}
	}
}