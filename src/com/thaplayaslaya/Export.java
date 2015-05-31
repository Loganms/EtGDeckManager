package com.thaplayaslaya;

import java.util.List;

import com.thaplayaslaya.datastructures.Deck;

public class Export {

	private static String credits = "Exported from [url=http://elementscommunity.org/forum/index.php?topic=58303.msg1188627#msg1188627]EtG Deck Manager[/url] "
			+ DeckManager.VERSION_ID;
	private static String text = "";

	public Export() {
	}

	public static String exportCase() {
		return null;
	}

	public static String exportDeckBinder(String dbName, List<Deck> decks, boolean includeNotes, boolean spoiler) {
		text += "[b][size=14pt]" + dbName + "[/size][/b]\n[hr]";

		if (includeNotes) {
			for (Deck d : decks) {
				text += "[size=14pt]" + d.getName() + "[/size]\n";
				text += "[deck]" + d.getImportCode() + "[/deck]";
				if (null != d.getNotes() && d.getNotes().length() > 0) {
					text += "Notes:" + d.getNotes() + "\n";
				}
				text += "[hr]";
			}
		} else {
			for (Deck d : decks) {
				text += "[size=14pt]" + d.getName() + "[/size]\n";
				text += "[deck]" + d.getImportCode() + "[/deck][hr]";
			}
		}

		text += "[size=8pt]" + credits + "[/size]";

		if (spoiler) {
			text = "[spoiler]".concat(text).concat("[/spoiler]");
		}

		return text;
	}
}
