package com.voindia.categorize;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class CategorizeDataVoindia {

	public static List<String> extractLinks(String url) throws Exception {
		final ArrayList<String> result = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(1000*1000).get(); //Configure socket read timeout to prevent timeouts on load intensive websites
		Elements links = doc.select("a[href]");
		for (Element link : links) {
		//	System.out.println(link.toString());
			result.add(link.attr("abs:href"));
		}
		return result;
	}
	//Getting the links will index these links 
	
	public final static void main(String[] args) throws Exception {
		// String site1 = "http://timesofindia.indiatimes.com/";

//		String category = getCategory("http://www.voindia.com/leonardo-dicaprio-wins-best-actor-for-the-revenant");
	//    System.out.println(category);
//	    String category1 = getCategory("http://www.voindia.com/kangana-ranaut-actress-speaks-at-women-in-the-world-summit-in-london/");
	//    System.out.println(category1);
	     extractLinks("http://www.voindia.com/kangana-ranaut-actress-speaks-at-women-in-the-world-summit-in-london/");
	
	
	}
		
	public static String getCategory(String url) throws Exception {
		      		
		
		
		String domain = "http://www.voindia.com";
		String domain1 = "http://www.voindia.com/";
		String junkUrl1 = "www.voindia.com/tag";
		String junkUrl2 = "www.voindia.com/category";
		String junkUrl3 = "www.voindia.com/feed";
		String junkData = "contact-us";
		List<String> nation_links = new ArrayList<String>();
		List<String> business_links = new ArrayList<String>();
		List<String> sports_links = new ArrayList<String>();
		List<String> entertainment_links = new ArrayList<String>();
		List<String> lifestyle_links = new ArrayList<String>();
		List<String> en_links = new ArrayList<String>();
		Set<String> nation_set = new HashSet<String>();
		Set<String> business_set = new HashSet<String>();
		Set<String> sports_set = new HashSet<String>();
		Set<String> entertainment_set = new HashSet<String>();
		Set<String> lifestyle_set = new HashSet<String>();
			
		  
	  	
		

		
		business_links = extractLinks("http://www.voindia.com/category/business/");
		
		for(int i=0; i<business_links.size(); i++){
		if(business_links.get(i).contains(url) == true && business_links.get(i).contains(junkUrl1)==false && business_links.get(i).contains(junkUrl2)==false && business_links.get(i).contains(junkUrl3)==false && business_links.get(i).contains(junkData)==false && business_links.get(i).equals(domain)== false && business_links.get(i).equals(domain1)== false)
		{
			return "business";
		}
		
		}
		 
		
		
		sports_links = extractLinks("http://www.voindia.com/category/sports/");
		
		for(int j=0; j<sports_links.size(); j++){
		if(sports_links.get(j).contains(url)== true && sports_links.get(j).contains(junkUrl1)==false && sports_links.get(j).contains(junkUrl2)==false && sports_links.get(j).contains(junkUrl3)==false && sports_links.get(j).contains(junkData)==false && sports_links.get(j).equals(domain)== false && sports_links.get(j).equals(domain1)== false)
		{
	        return "sports";	
		}
		}
		
		
		
		entertainment_links = extractLinks("http://www.voindia.com/category/entertaiment/");
		
		
		for(int k=0;k<entertainment_links.size();k++){
		if(entertainment_links.get(k).contains(url)== true && entertainment_links.get(k).contains(junkUrl1)==false && entertainment_links.get(k).contains(junkUrl2)==false && entertainment_links.get(k).contains(junkUrl3)==false && entertainment_links.get(k).contains(junkData)==false && entertainment_links.get(k).equals(domain)== false && entertainment_links.get(k).equals(domain1)== false) 
		{
			return "entertainment";
		}
		
		}
    	
		
		
		
		lifestyle_links = extractLinks("http://www.voindia.com/category/life-style/");
	   
		for(int l=0;l<lifestyle_links.size();l++){
		
			if(lifestyle_links.get(l).contains(url)== true && lifestyle_links.get(l).contains(junkUrl1)==false && lifestyle_links.get(l).contains(junkUrl2)==false && lifestyle_links.get(l).contains(junkUrl3)==false && lifestyle_links.get(l).contains(junkData)==false && lifestyle_links.get(l).equals(domain)== false && lifestyle_links.get(l).equals(domain1)== false)
			{
				return "lifestyle";
		  	}
		}

		
	
	
		    nation_links = extractLinks("http://www.voindia.com/");
			
			for(int i=0; i< nation_links.size(); i++){
			if(nation_links.get(i).contains(url)== true && nation_links.get(i).contains(junkUrl1)==false && nation_links.get(i).contains(junkUrl2)==false && nation_links.get(i).contains(junkUrl3)==false && nation_links.get(i).contains(junkData)==false && nation_links.get(i).equals(domain)== false && nation_links.get(i).equals(domain1)== false)
			{
			
			   return "nation-news";
				
			}
			
			}
	
	     return "No Match Found";
	}
	}  
	


