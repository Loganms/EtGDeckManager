package com.thaplayaslaya.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

public class DeckSort {

	public static final String[] LEVEL_ONE_OPTIONS = new String[] { "Alpha", "Most", "Least", "Mark", "" };
	public static final String[] ALPHA_OPTIONS = new String[] { "Abc", "Zyx" };
	public static final String[] M_L_OPTIONS = new String[] { "Upgraded", "Copies of", "Cards", "Element" };
	public static final String[] ELEMENT_OPTIONS = Element.DISPLAY_NAMES;

	public static final String ELEMENT_OPTIONS_ALT = "Variety";

	public static final int L1Alpha = 0;
	public static final int L1Most = 1;
	public static final int L1Least = 2;
	public static final int L1Mark = 3;
	public static final int L1Empty = 4;

	public static final int AlphaAbc = 0;
	public static final int AlphaZyx = 1;

	public static final int MLUpgraded = 0;
	public static final int MLCopiesOf = 1;
	public static final int MLCards = 2;
	public static final int MLElements = 3;

	private static ArrayList<Comparator<Deck>> comparatorList = new ArrayList<>();

	public static class DeckFirstLetterComparator implements Comparator<Deck> {

		private int order;

		public DeckFirstLetterComparator(int order) {
			this.order = order;
		}

		@Override
		public int compare(Deck o1, Deck o2) {
			return order * Integer.compare(o1.getName().toLowerCase().charAt(0), o2.getName().toLowerCase().charAt(0));
		}
	}

	public static class DeckNameComparator implements Comparator<Deck> {

		private int order;

		public DeckNameComparator(int order) {
			this.order = order;
		}

		@Override
		public int compare(Deck o1, Deck o2) {
			return order * o1.getName().compareToIgnoreCase(o2.getName());
		}
	}

	public static class DeckSizeComparator implements Comparator<Deck> {

		private int order;

		public DeckSizeComparator(int order) {
			this.order = order;
		}

		@Override
		public int compare(Deck o1, Deck o2) {
			return order * Integer.compare(o1.getImportCode().length(), o2.getImportCode().length());
		}

	}

	public static class DeckUpgradesComparator implements Comparator<Deck> {

		private int order;

		public DeckUpgradesComparator(int order) {
			this.order = order;
		}

		@Override
		public int compare(Deck o1, Deck o2) {
			/*
			 * System.out.println(o1.getName() + " Upgrades: " +
			 * o1.getNumUpgradedCards() + " / " + o2.getName() + " Upgrades: " +
			 * o2.getNumUpgradedCards());
			 */
			return order * Integer.compare(o1.getNumUpgradedCards(), o2.getNumUpgradedCards());
		}

	}

	public static class DeckCardCopiesComparator implements Comparator<Deck> {

		private String cardCode;
		private int order;

		public DeckCardCopiesComparator(String cardCode, int order) {
			this.cardCode = cardCode;
			this.order = order;
		}

		@Override
		public int compare(Deck o1, Deck o2) {
			/*
			 * System.out.println(o1.getName() + " Copies: " +
			 * o1.getNumCopiesOf(cardCode) + " / " + o2.getName() + " Copies: "
			 * + o2.getNumCopiesOf(cardCode));
			 */

			return order * Integer.compare(o1.getNumCopiesOf(cardCode), o2.getNumCopiesOf(cardCode));
		}
	}

	public static class DeckNumDiffElementComparator implements Comparator<Deck> {

		private int order;

		public DeckNumDiffElementComparator(int order) {
			this.order = order;
		}

		@Override
		public int compare(Deck o1, Deck o2) {
			return order * Integer.compare(o1.getNumDiffElements(), o2.getNumDiffElements());
		}

	}

	public static class DeckNumCardsOfSameElementComparator implements Comparator<Deck> {

		private String cardCode;
		private int order;

		public DeckNumCardsOfSameElementComparator(String cardCode, int order) {
			this.cardCode = cardCode;
			this.order = order;
		}

		@Override
		public int compare(Deck o1, Deck o2) {
			return order * Integer.compare(o1.getNumCardsOfSameElement(cardCode), o2.getNumCardsOfSameElement(cardCode));
		}
	}

	public static class DeckElementComparator implements Comparator<Deck> {

		private char element;

		public DeckElementComparator(String element) {
			this.element = Element.stringToElement(element).getMark().charAt(2);
		}

		@Override
		public int compare(Deck o1, Deck o2) {
			char m1 = o1.getMark().charAt(2);
			char m2 = o2.getMark().charAt(2);

			int dif1 = m1 - element;
			int dif2 = m2 - element;

			if (dif1 == dif2) {
				return 0;
			} else if (dif1 == 0) {
				return -1;
			} else if (dif2 == 0) {
				return 1;
			} else if ((dif1 > 0 && dif2 > 0) || (dif1 < 0 && dif2 < 0)) {
				return (dif1 < dif2) ? -1 : 1;
			} else {
				return (dif1 > 0) ? -1 : 1;
			}
		}
	}

	public static void sort(DeckBinder currentlySelectedDeckBinder, Queue<String> sortList) {

		/*
		 * System.out.print("["); for (Deck d :
		 * currentlySelectedDeckBinder.getDecks()) {
		 * System.out.print(d.getName() + " " + d.getMark() + ", "); }
		 * System.out.print("]");
		 */
		if (!sortList.isEmpty()) {

			String sortOption = sortList.peek();
			sortList.remove();
			int order = 0;

			if (sortOption.equals(LEVEL_ONE_OPTIONS[L1Alpha])) {
				sortOption = sortList.peek();
				sortList.remove();

				if (sortOption.equals(ALPHA_OPTIONS[AlphaAbc])) {
					order = 1;
				} else if (sortOption.equals(ALPHA_OPTIONS[AlphaZyx])) {
					order = -1;
				}

				if (sortList.isEmpty()) {
					comparatorList.add(new DeckNameComparator(order));
				} else {
					comparatorList.add(new DeckFirstLetterComparator(order));
				}

				// Collections.sort(currentlySelectedDeckBinder.getDecks(), new
				// DeckNameComparator(order));

			} else if (sortOption.equals(LEVEL_ONE_OPTIONS[L1Most]) || sortOption.equals(LEVEL_ONE_OPTIONS[L1Least])) {
				if (sortOption.equals(LEVEL_ONE_OPTIONS[L1Most]))
					order = -1;
				if (sortOption.equals(LEVEL_ONE_OPTIONS[L1Least]))
					order = 1;

				sortOption = sortList.peek();
				sortList.remove();

				if (sortOption.equals(M_L_OPTIONS[MLUpgraded])) {
					comparatorList.add(new DeckUpgradesComparator(order));
					// Collections.sort(currentlySelectedDeckBinder.getDecks(),
					// new DeckUpgradesComparator(order));
				} else if (sortOption.equals(M_L_OPTIONS[MLCopiesOf])) {
					sortOption = sortList.peek();
					sortList.remove();
					comparatorList.add(new DeckCardCopiesComparator(sortOption, order));
					// Collections.sort(currentlySelectedDeckBinder.getDecks(),
					// new DeckCardCopiesComparator(sortOption, order));
				} else if (sortOption.equals(M_L_OPTIONS[MLCards])) {
					comparatorList.add(new DeckSizeComparator(order));
					// Collections.sort(currentlySelectedDeckBinder.getDecks(),
					// new DeckSizeComparator(order));
				} else if (sortOption.equals(M_L_OPTIONS[MLElements])) {
					sortOption = sortList.peek();
					sortList.remove();
					if (sortOption.equals(ELEMENT_OPTIONS_ALT)) {
						comparatorList.add(new DeckNumDiffElementComparator(order));
						// Collections.sort(currentlySelectedDeckBinder.getDecks(),
						// new DeckNumDiffElementComparator(order));
					} else {
						comparatorList.add(new DeckNumCardsOfSameElementComparator(sortOption, order));
						// Collections.sort(currentlySelectedDeckBinder.getDecks(),
						// new DeckNumCardsOfSameElementComparator(sortOption,
						// order));
					}
				}
			} else if (sortOption.equals(LEVEL_ONE_OPTIONS[L1Mark])) {
				sortOption = sortList.peek();
				sortList.remove();
				comparatorList.add(new DeckElementComparator(sortOption));
				// Collections.sort(currentlySelectedDeckBinder.getDecks(), new
				// DeckElementComparator(sortOption));
			}
		} else {

			Collections.sort(currentlySelectedDeckBinder.getDecks(), new MultiComparator<Deck>(comparatorList));
			comparatorList.clear();

			return;
		}
		sort(currentlySelectedDeckBinder, sortList);
	}
}
