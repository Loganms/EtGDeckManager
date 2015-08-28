package com.thaplayaslaya.gui;

import javax.swing.JComboBox;

public class SortDialogComboBox<E> extends JComboBox<E> {
	private static final long serialVersionUID = 7039890626526317955L;

	private String previouslySelected = "";
	private int i;
	private int j;

	public SortDialogComboBox(int i, int j) {
		super();
		setPosition(i, j);
	}

	public SortDialogComboBox(E[] Items, int i, int j) {
		super(Items);
		setPosition(i, j);
	}

	public void setPosition(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public String getPosition() {
		return "" + i + ", " + j;
	}

	public int getI() {
		return this.i;
	}

	public int getJ() {
		return this.j;
	}

	public String getPreviouslySelected() {
		return previouslySelected;
	}

	public void setPreviouslySelected(String previouslySelected) {
		this.previouslySelected = previouslySelected;
	}

}
