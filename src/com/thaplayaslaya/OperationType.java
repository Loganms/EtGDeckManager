package com.thaplayaslaya;

public enum OperationType {
	ADD_NEW_DECK(1), ADD_NEW_DECKBINDER(2), EDIT_DECK(3);
	private int value;
	
	private OperationType(int value) {
		this.value = value;
	}
}
