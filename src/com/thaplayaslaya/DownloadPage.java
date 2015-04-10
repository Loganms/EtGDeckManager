package com.thaplayaslaya;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	
	//Returns null if no deck image links are found 
    public static List<URL> getRecommendedDeckURLS(String baseURL) throws IOException {
    	
    	List<URL> deckImageURLs = new ArrayList<URL>();
        URL url = new URL(baseURL);

        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;

        boolean found = false;
        
        while ((line = br.readLine()) != null) {
        	if(line.contains("http://dek.im/cache/")){
        		found = true;
        		String[] strings = line.split("http://dek.im/d/");
        		for(String s : strings) {
        			StringBuilder builder = new StringBuilder();
        			if (s.contains("http://dek.im/cache/")) {
						int index1 = s.lastIndexOf("http://dek.im/cache/");
						//(+4) because I want to include the ".png"
						int index2 = s.indexOf(".png", index1) + 4;
						builder.append(s.substring(index1, index2));
						deckImageURLs.add(new URL(builder.toString()));
						System.out.println("From function " + builder.toString());
					}
        		}
        	}
            if (found){
            	return deckImageURLs;
            }
        }
        return null;
    }
}