package com.thaplayaslaya.datastructures;

public enum OperationType {
	ADD_NEW_DECK(null), ADD_NEW_DECKBINDER(null), EDIT_DECK("Edit Deck"), RENAME_DECKBINDER("R"), ADD_NEW_FG_COUNTER_DECK(null), EDIT_FG_COUNTER_DECK(null);

	private String text;

	private OperationType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
