package com.thaplayaslaya;

import java.util.List;

import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;

public class Export {

	private static String credits = "Exported from [url=http://elementscommunity.org/forum/index.php?topic=58303.msg1188627#msg1188627]EtG Deck Manager[/url] "
			+ DeckManager.VERSION_ID;

	public Export() {
	}

	public static String exportCase() {
		return null;
	}

	public static String exportDeckBinder(String dbName, List<Deck> decks, boolean includeNotes, boolean spoiler) {
		return exportDeckBinder(dbName, decks, includeNotes, spoiler, true);
	}

	private static String exportDeckBinder(String dbName, List<Deck> decks, boolean includeNotes, boolean spoiler, boolean includeCredits) {
		String text = "";

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

		if (includeCredits)
			text += "[size=8pt]" + credits + "[/size]";

		if (spoiler)
			text = "[spoiler]".concat(text).concat("[/spoiler]");

		return text;
	}

	public static String exportCase(String caseName, List<DeckBinder> deckBinders, boolean includeNotes, boolean spoiler) {
		String text = "";

		text += "[b][size=18pt]" + caseName + "[/size][/b]\n[hr]";

		for (DeckBinder db : deckBinders) {
			text += exportDeckBinder(db.getName(), db.getDecks(), includeNotes, false, false);
		}

		text += "[size=8pt]" + credits + "[/size]";

		if (spoiler)
			text = "[spoiler]".concat(text).concat("[/spoiler]");

		return text;
	}
}
