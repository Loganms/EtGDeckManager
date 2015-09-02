package com.thaplayaslaya;

import com.thaplayaslaya.datastructures.Case;

public class Update {

	@SuppressWarnings("unused")
	private static final String[] versions = new String[] { "v1.0", "v1.0.1", "v1.0.2", "v1.0.3", "v1.0.4" , "v1.0.5"};

	public Update() {
	}

	public static void updateFrom(Case briefcase, String ver) {
		if (null != ver) {

		} else {
			totalUpdate();
		}
		briefcase.VERSION_ID = DeckManager.VERSION_ID;
	}

	private static void totalUpdate() {

	}

}
