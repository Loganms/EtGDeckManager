package com.thaplayaslaya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DownloadPage {

	public static List<URL> getRecommendedDeckURLS(FalseGod god) {

		StringBuilder godURL = new StringBuilder("http://elementscommunity.org/forum/akebono/oracle-");

		godURL.append(god.getURLName());

		godURL.append("/");
		try {
			return getRecommendedDeckURLS(godURL.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Returns null if no deck image links are found
	private static List<URL> getRecommendedDeckURLS(String baseURL) throws IOException {

		List<URL> deckImageURLs = new ArrayList<URL>();
		URL url = new URL(baseURL);

		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;

		boolean found = false;

		while ((line = br.readLine()) != null) {
			if (line.contains("http://dek.im/cache/")) {
				found = true;
				String[] strings = line.split("http://dek.im/d/");
				// moved builder out of for loop.
				StringBuilder builder = new StringBuilder();
				for (String s : strings) {
					if (s.contains("http://dek.im/cache/")) {
						int index1 = s.lastIndexOf("http://dek.im/cache/");
						// (+4) because I want to include the ".png"
						int index2 = s.indexOf(".png", index1) + 4;
						builder.append(s.substring(index1, index2));
						deckImageURLs.add(new URL(builder.toString()));
						// empty out the builder.
						builder.setLength(0);
					}
				}
			}
			if (found) {
				return deckImageURLs;
			}
		}
		return null;
	}

	public static URL getFalseGodDeckURL(FalseGod god) throws IOException {

		URL url = new URL("http://elementscommunity.org/forum/false-gods/here-are-the-decks-of-the-actual-false-gods/");
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		String godSet1 = null;
		String godSet2 = null;
		readingLoop: while ((line = br.readLine()) != null) {
			if (line.length() > 10000) {
				if (godSet1 == null)
					godSet1 = line;
				else {
					godSet2 = line;
					break readingLoop;
				}
			}
		}
		char initial = god.name().charAt(0);
		if (initial <= 'G') {
			return extractDeckURL((godSet1.split("The False Gods:"))[1], god);
		} else if (initial >= 'H') {
			if (god.equals(FalseGod.JEZEBEL)) {
				return extractJezebelURL(godSet2);
			}
			return extractDeckURL(godSet2, god);
		} else {
			return null;
		}

	}

	// Stupid fix for stupid problem
	// Forums aren't uniform enough to use one method for all gods, Jezebel is
	// only outlier.
	private static URL extractJezebelURL(String string) throws MalformedURLException {
		// Jezebel has an erroneous space before his name so a different key
		// must be used.
		string = string.split("post_" + FalseGod.JEZEBEL.toString())[1];
		int index1, index2;
		index1 = string.indexOf("http://dek.im/cache/");
		index2 = string.indexOf(".png", index1) + 4;
		string = string.substring(index1, index2);
		return new URL(string);
	}

	private static URL extractDeckURL(String string, FalseGod god) throws MalformedURLException {
		string = string.split(">" + god.toString())[1];
		int index1, index2;
		index1 = string.indexOf("http://dek.im/cache/");
		index2 = string.indexOf(".png", index1) + 4;
		string = string.substring(index1, index2);
		return new URL(string);
	}
}