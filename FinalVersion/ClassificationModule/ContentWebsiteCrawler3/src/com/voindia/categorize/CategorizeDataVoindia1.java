package com.voindia.categorize;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CategorizeDataVoindia1 {



	public static String extractCategory (String url) throws Exception {
		
		Document doc = Jsoup.connect(url).timeout(1000*1000).get(); //Configure socket read timeout to prevent timeouts on load intensive websites
		Elements article = doc.select("article");
		for (Element link : article) {
	    String articleText = link.attr("class");
	    System.out.println(articleText + "\n");
		
	    if(articleText.contains("entertainment"))
		return "entertainment";
		
		if(articleText.contains("sports"))
			return "sports";
		
		
		if(articleText.contains("business"))
			return "business";
		
		if(articleText.contains("lifestyle"))
			return "lifestyle";
	
		 
		
		}

	
		return "";
	
	}//Getting the links will index these links 
	
	

	public final static void main(String[] args) throws Exception {
		
		 extractCategory("http://www.voindia.com/kangana-ranaut-actress-speaks-at-women-in-the-world-summit-in-london/");
		 extractCategory("http://www.voindia.com/leonardo-dicaprio-wins-best-actor-for-the-revenant");
		 extractCategory("http://www.voindia.com/alibaba-funding-to-help-paytm-go-big/");
	
	
	}













}




