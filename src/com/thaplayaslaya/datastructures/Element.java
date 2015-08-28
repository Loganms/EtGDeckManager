package com.thaplayaslaya.datastructures;

public enum Element {

	ENTROPY("4vc", "50u", "6ts", "6ve", "4sj", "6r3", "8pj"), DEATH("52g", "542", "710", "72i", "4sk", "6r4", "8pk"), GRAVITY("55k", "576", "744",
			"75m", "4sl", "6r5", "8pl"), EARTH("58o", "5aa", "778", "786", "4sm", "6r6", "8pm"), LIFE("5bs", "5de", "7ac", "7bu", "4sn", "6r7", "8pn"), FIRE(
			"5f0", "5gi", "7dg", "7f2", "4so", "6r8", "8po"), WATER("5i4", "5ji", "7gk", "7i6", "4sp", "6r9", "8pp"), LIGHT("5l8", "5mq", "7jo",
			"7la", "4sq", "6ra", "8pq"), AIR("5oc", "5pu", "7ms", "7oe", "4sr", "6rb", "8pr"), TIME("5rg", "5t2", "7q0", "7ri", "4ss", "6rc", "8ps"), DARKNESS(
			"5uk", "606", "7t4", "7um", "4st", "6rd", "8pt"), AETHER("61o", "63a", "808", "81q", "4su", "6re", "8pu");

	public static final String[] DISPLAY_NAMES = new String[] {
			"Entropy",
			"Death",
			"Gravity",
			"Earth",
			"Life",
			"Fire",
			"Water",
			"Light",
			"Air",
			"Time",
			"Darkness",
			"Aether" };

	private String begin, end, uBegin, uEnd, card, uCard, mark;

	private Element(String begin, String end, String uBegin, String uEnd, String card, String uCard, String mark) {
		this.begin = begin;
		this.end = end;
		this.uBegin = uBegin;
		this.uEnd = uEnd;
		this.card = card;
		this.uCard = uCard;
		this.mark = mark;
	}

	public String getBegin() {
		return begin;
	}

	public String getEnd() {
		return end;
	}

	public String getuBegin() {
		return uBegin;
	}

	public String getuEnd() {
		return uEnd;
	}

	public String getCard() {
		return card;
	}

	public String getuCard() {
		return uCard;
	}

	public String getMark() {
		return mark;
	}

	public String[] displayNames() {
		return DISPLAY_NAMES;
	}

	public static Element stringToElement(String element) {
		Element e;
		switch (element) {
		case "Entropy":
			e = ENTROPY;
			break;
		case "Death":
			e = DEATH;
			break;
		case "Gravity":
			e = GRAVITY;
			break;
		case "Earth":
			e = EARTH;
			break;
		case "Life":
			e = LIFE;
			break;
		case "Fire":
			e = FIRE;
			break;
		case "Water":
			e = WATER;
			break;
		case "Light":
			e = LIGHT;
			break;
		case "Air":
			e = AIR;
			break;
		case "Time":
			e = TIME;
			break;
		case "Darkness":
			e = DARKNESS;
			break;
		case "Aether":
			e = AETHER;
			break;
		default:
			e = null;
			break;
		}
		if( null == e){
			System.out.println("element is NULL");
		}
		return e;
	}
}