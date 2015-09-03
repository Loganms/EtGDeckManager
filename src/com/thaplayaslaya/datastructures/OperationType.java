package com.thaplayaslaya.datastructures;

public enum OperationType {
	ADD_NEW_FG_COUNTER_DECK(null), EDIT_FG_COUNTER_DECK(null);

	private String text;

	private OperationType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
