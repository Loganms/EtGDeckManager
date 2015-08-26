package com.thaplayaslaya.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.UIManager;

import com.thaplayaslaya.SwingUtil;
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
			Color color = SwingUtil.showColorChooserDialog(parent, "Choose Foreground Color", comp.getForeground(), false);
			if (null != color) {
				comp.setForeground(color);
				datastruct.getStyle().setForegroundColor(color);
			}

		} else if (cmd.equals("Background Color")) {
			Color color = SwingUtil.showColorChooserDialog(parent, "Choose Background Color", comp.getBackground(), false);
			if (null != color) {
				comp.setBackground(color);
				datastruct.getStyle().setBackgroundColor(color);
			}
		} else if (cmd.equals("Default")) {
			if (comp instanceof JLabel) {
				comp.setForeground(UIManager.getColor("Label.foreground"));
				comp.setBackground(UIManager.getColor("Label.background"));
				datastruct.getStyle().setForegroundColor(UIManager.getColor("Label.foreground"));
				datastruct.getStyle().setBackgroundColor(UIManager.getColor("Label.background"));
			}
		}
	}
}
