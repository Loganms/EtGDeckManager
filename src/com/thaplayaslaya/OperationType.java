package com.thaplayaslaya;

public enum OperationType {
	ADD_NEW_DECK(null), ADD_NEW_DECKBINDER(null), EDIT_DECK("Edit Deck"), RENAME_DECKBINDER("R");

	private String text;

	private OperationType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
