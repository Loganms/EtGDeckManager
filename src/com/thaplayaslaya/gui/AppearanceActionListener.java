package com.thaplayaslaya.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import com.thaplayaslaya.datastructures.IStylish;

public class AppearanceActionListener implements ActionListener {

	private Window parent;
	private Component comp;
	private IStylish datastruct;

	public AppearanceActionListener(Window parent, Component c, IStylish styleObject) {
		super();
		this.parent = parent;
		this.comp = c;
		this.datastruct = styleObject;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Foreground Color")) {
			Color color = JColorChooser.showDialog(parent, "Choose Foreground Color", comp.getForeground());
			if (null != color) {
				comp.setForeground(color);
				datastruct.getStyle().setForegroundColor(color);
			}

		} else if (cmd.equals("Background Color")) {
			Color color = JColorChooser.showDialog(parent, "Choose Background Color", comp.getBackground());
			if (null != color) {
				comp.setBackground(color);
				datastruct.getStyle().setBackgroundColor(color);
			}
		} else if (cmd.equals("Default")) {
			comp.setForeground(null);
			comp.setBackground(null);
			datastruct.getStyle().setForegroundColor(null);
			datastruct.getStyle().setBackgroundColor(null);
		}
	}
}
