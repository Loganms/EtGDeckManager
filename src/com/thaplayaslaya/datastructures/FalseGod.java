package com.thaplayaslaya.datastructures;

public enum FalseGod {
	AKEBONO("Akebono"), CHAOS_LORD("Chaos Lord"), DARK_MATTER("Dark Matter"), DECAY("Decay"), DESTINY("Destiny"), DIVINE_GLORY("Divine Glory"), DREAM_CATCHER(
			"Dream Catcher"), ELIDNIS("Elidnis"), ETERNAL_PHOENIX("Eternal Phoenix"), FEROX("Ferox"), FIRE_QUEEN("Fire Queen"), GEMINI("Gemini"), GRAVITON(
			"Graviton"), HECATE("Hecate"), HERMES("Hermes"), INCARNATE("Incarnate"), JEZEBEL("Jezebel"), LIONHEART("Lionheart"), MIRACLE("Miracle"), MORTE(
			"Morte"), NEPTUNE("Neptune"), OBLITERATOR("Obliterator"), OCTANE("Octane"), OSIRIS("Osiris"), PARADOX("Paradox"), RAINBOW("Rainbow"), SCORPIO(
			"Scorpio"), SEISM("Seism"), SERKET("Serket");
	private String prettyName;

	FalseGod(String name) {
		this.prettyName = name;
	}

	@Override
	public String toString() {
		return prettyName;
	}

	public String getURLName() {
		String s = toString().toLowerCase();

		if (s.contains(" ")) {
			s = s.replace(' ', '-');
			return s;
		} else {
			return s;
		}
	}
}
