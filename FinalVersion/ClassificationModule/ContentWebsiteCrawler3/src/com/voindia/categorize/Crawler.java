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



public class Crawler {

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
			
		  
	    Map<String, Object> categoriesData1 = null;
	    String content;
		String category;	
		

		
		business_links = extractLinks("http://www.voindia.com/category/business/");
		
		for(int i=0; i<business_links.size(); i++){
		if(business_links.get(i).contains(domain)== true && business_links.get(i).contains(junkUrl1)==false && business_links.get(i).contains(junkUrl2)==false && business_links.get(i).contains(junkUrl3)==false && business_links.get(i).contains(junkData)==false && business_links.get(i).equals(domain)== false && business_links.get(i).equals(domain1)== false)
		{
			business_set.add(business_links.get(i));
			System.out.println(business_links.get(i));
		}
		
		}
		 
		 
		for(String S : business_set){
		content = S;	
		category = "business";
	    if(content !=null && content !="-" && content != "")
	    {
		System.out.println("content:"+content);
	    categoriesData1 = IndexCategoriesData.putJsonDocument(content,category);
	 	IndexCategoriesData.postElasticSearch(categoriesData1);
        categoriesData1 = null;
	    }
	    
		}
		
		
		sports_links = extractLinks("http://www.voindia.com/category/sports/");
		
		for(int j=0; j<sports_links.size(); j++){
		if(sports_links.get(j).contains(domain)== true && sports_links.get(j).contains(junkUrl1)==false && sports_links.get(j).contains(junkUrl2)==false && sports_links.get(j).contains(junkUrl3)==false && sports_links.get(j).contains(junkData)==false && sports_links.get(j).equals(domain)== false && sports_links.get(j).equals(domain1)== false)
		{
	     	sports_set.add(sports_links.get(j));
		    System.out.println(sports_links.get(j));
		}
		}
	
		Map<String, Object> categoriesData2 = null;
		
		
		for(String S : sports_set){
		content = S;	
		category = "sports";
	    if(content !=null && content != "-" && content != "")
	    {
	    System.out.println("content:"+content);
	    categoriesData2 = IndexCategoriesData.putJsonDocument(content,category);
		IndexCategoriesData.postElasticSearch(categoriesData2);
        categoriesData2 = null;
	    }
		}
		
		entertainment_links = extractLinks("http://www.voindia.com/category/entertaiment/");
		
		
		for(int k=0;k<entertainment_links.size();k++){
		if(entertainment_links.get(k).contains(domain)== true && entertainment_links.get(k).contains(junkUrl1)==false && entertainment_links.get(k).contains(junkUrl2)==false && entertainment_links.get(k).contains(junkUrl3)==false && entertainment_links.get(k).contains(junkData)==false && entertainment_links.get(k).equals(domain)== false && entertainment_links.get(k).equals(domain1)== false) 
		{
			entertainment_set.add(entertainment_links.get(k));
		    System.out.println(entertainment_links.get(k));
		}
		
		}
    	
		
		 Map<String, Object> categoriesData3 = null;
		
		 for(String S : entertainment_set){
				content = S;	
				category = "entertainment";
				if(content !=null && content !="-" && content !="" ){
				System.out.println("content:"+content);
				categoriesData3 = IndexCategoriesData.putJsonDocument(content,category);
				IndexCategoriesData.postElasticSearch(categoriesData3);
                categoriesData3 = null;
				}
				}
				
		
		lifestyle_links = extractLinks("http://www.voindia.com/category/life-style/");
	   
		for(int l=0;l<lifestyle_links.size();l++){
		
			if(lifestyle_links.get(l).contains(domain)== true && lifestyle_links.get(l).contains(junkUrl1)==false && lifestyle_links.get(l).contains(junkUrl2)==false && lifestyle_links.get(l).contains(junkUrl3)==false && lifestyle_links.get(l).contains(junkData)==false && lifestyle_links.get(l).equals(domain)== false && lifestyle_links.get(l).equals(domain1)== false)
			{
				lifestyle_set.add(lifestyle_links.get(l));
				System.out.println(lifestyle_links.get(l));
		  	}
		}

		 Map<String, Object> categoriesData4 = null;
		
		 for(String S : lifestyle_set){
				content = S;	
				category = "lifestyle";
			    if(content !=null && content !="-" && content != ""){
			    System.out.println("content:"+content);
			    categoriesData4 = IndexCategoriesData.putJsonDocument(content,category);
				IndexCategoriesData.postElasticSearch(categoriesData4);
                categoriesData4 = null;   
			    }
				}	
	
	
		    nation_links = extractLinks("http://www.voindia.com/");
			
			for(int i=0; i< nation_links.size(); i++){
			if(nation_links.get(i).contains(domain)== true && nation_links.get(i).contains(junkUrl1)==false && nation_links.get(i).contains(junkUrl2)==false && nation_links.get(i).contains(junkUrl3)==false && nation_links.get(i).contains(junkData)==false && nation_links.get(i).equals(domain)== false && nation_links.get(i).equals(domain1)== false)
			{
				if(business_set.contains(nation_links.get(i))==false && sports_set.contains(nation_links.get(i))==false && entertainment_set.contains(nation_links.get(i))==false && lifestyle_set.contains(nation_links.get(i))==false ){
				nation_set.add(nation_links.get(i));
				System.out.println(nation_links.get(i));
				}
				
			}
			
			}
	

		 
				for(String S : nation_set){
				
				content = S;	
				if(content !=null && content !="-" && content !="")
				System.out.println("content:"+content);
				category = "india-news"; 
				categoriesData1 = IndexCategoriesData.putJsonDocument(content,category);
				IndexCategoriesData.postElasticSearch(categoriesData1);
		        categoriesData1 = null;
				
				}
			

	
	
	
	
	}
	}  
	


