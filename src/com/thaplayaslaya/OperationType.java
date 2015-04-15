package com.thaplayaslaya;

public enum OperationType {
	ADD_NEW_DECK(null), ADD_NEW_DECKBINDER(null), EDIT_DECK("Edit Deck"), RENAME_DECKBINDER("R");

	private String buttonText;

	private OperationType(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getButtonText() {
		return buttonText;
	}
}
