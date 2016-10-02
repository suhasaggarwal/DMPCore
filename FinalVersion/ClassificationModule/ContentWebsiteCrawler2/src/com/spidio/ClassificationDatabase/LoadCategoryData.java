
package com.spidio.ClassificationDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoadCategoryData {

	public static void LoadSportsData(String category, String subcategory,
			String keywords, String content, String domain, Document doc,
			List<String> links) throws Exception  {

		
			links = Crawler.extractLinks(domain);
		
            domain = "http://indianexpress.com/article/sports/";
		
		
		for (String sport_link : links) {

			if (sport_link.contains(domain) == true) {
				System.out.println(sport_link);
				subcategory = null;

				// sports
				if (sport_link.contains("articlelistls") == false
						& sport_link.contains("speciallist") == false
						& sport_link.contains("articlelist") == false
						& sport_link.contains("photostory") == false
						& sport_link.contains("sphome") == false
						& sport_link.contains("scorecenter") == false
						& sport_link.contains("trackcenter") == false)
				     {
				        try{
					    doc = Jsoup.connect(sport_link).timeout(1000*1000).get();
				        }
				        catch(Exception e)
				        {
				        	e.printStackTrace();
				        	continue;
				        }
				        
				        
				    }	 

				if (doc != null) {
					
					try{
					keywords = doc.select("meta[name=keywords]").first()
							.attr("content");
					System.out.println("Meta keyword : " + keywords);
					String description = doc.select("meta[name=description]")
							.get(0).attr("content");
					System.out.println("Meta description : " + description);
					

				sport_link.replace("//", "-");
				String[] retval = sport_link.split("/", 0);
				category = retval[4];
				// subcategory = retval[3]+"/"+retval[4];
			//	subcategory = retval[4] + "/" + retval[5];
				subcategory = retval[5] ;
				// link.replace(subcategory,"$");
				content = keywords;
			  }
				catch(Exception e){
						e.printStackTrace();
						continue;
					}
				System.out.println(category);
				System.out.println(subcategory);
				System.out.println(content);
				if(content == null || content == "" || content == "-")
				content = sport_link;
				
				Map<String, Object> categoriesData = null;
				try{
				
				categoriesData=IndexCategoriesData.putJsonDocument(category,subcategory,content);
				IndexCategoriesData.postElasticSearch(categoriesData);
				}
				catch(Exception e){
					e.printStackTrace();
				    continue;
				}
				}
				
				
				
				}
		}

	}

	public static void LoadEntertainmentData(String category,
			String subcategory, String keywords, String content, String domain,
			Document doc, List<String> links) {

		try{
		links = Crawler.extractLinks(domain);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		 domain = "http://indianexpress.com/article/entertainment/";
			
		
		
		
		for (String entertainment_link : links) {

			if (entertainment_link.contains(domain) == true) {
				System.out.println(entertainment_link);
				subcategory = null;

				// entertainment
				if (entertainment_link.contains("articlelistls") == false
						& entertainment_link.contains("speciallist") == false
						& entertainment_link.contains("articlelist") == false
						& entertainment_link.contains("?") == false
						& entertainment_link.contains("photostory") == false)
				{
					 try{
					doc = Jsoup.connect(entertainment_link).timeout(1000*1000).get();
					 }
				     catch(Exception e){
				    	 
				    	 e.printStackTrace();
				         continue;
				     }
				}	
			
				if (doc != null) {
					try{	
					
					keywords = doc.select("meta[name=keywords]").first()
							.attr("content");
					System.out.println("Meta keyword : " + keywords);
					String description = doc.select("meta[name=description]")
							.get(0).attr("content");
					System.out.println("Meta description : " + description);
				

				entertainment_link.replace("//", "-");
				String[] retval = entertainment_link.split("/", 0);
				category = retval[4];
			    subcategory = retval[5];
		//		subcategory = retval[4];
				// link.replace(subcategory,"$");
				content = keywords;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
				
				
				System.out.println(category);
				System.out.println(subcategory);
				System.out.println(entertainment_link);
				Map<String, Object> categoriesData = null;
				// categoriesData =
			   
				if(content == null || content == "" || content == "-")
					content = entertainment_link;
				
				
				try{
				categoriesData=IndexCategoriesData.putJsonDocument(category,subcategory,content);
				IndexCategoriesData.postElasticSearch(categoriesData);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
				
				}
				
				}
			
			}
			
			}

		

	

	public static void LoadBusinessData(String category, String subcategory,
			String keywords, String content, String domain, Document doc,
			List<String> links) {

		try{
		
		links = Crawler.extractLinks(domain);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		domain = "http://www.financialexpress.com/article/";
		
		// String [] content = null;

		for (String business_link : links) {

			if (business_link.contains(domain) == true) {
				System.out.println(business_link);
				subcategory = null;

				// business
				if (business_link.contains("articlelistls") == false
						& business_link.contains("speciallist") == false
						& business_link.contains("articlelist") == false
						& business_link.contains("photostory") == false
						& business_link.contains("sphome") == false)
				  {
					try{
				
					
					doc = Jsoup.connect(business_link).timeout(1000*1000).get();
					}
				    catch(Exception e)
				    {
				      e.printStackTrace();	
				        continue;
				    }
				}   
					
					
				if (doc != null) {
					
					try{
					keywords = doc.select("meta[name=keywords]").first()
							.attr("content");
					System.out.println("Meta keyword : " + keywords);
					String description = doc.select("meta[name=description]")
							.get(0).attr("content");
					System.out.println("Meta description : " + description);
				

				business_link.replace("//", "-");
				String[] retval = business_link.split("/", 0);
				if(business_link.contains("economictimes"))
				{
					category = "business";
					subcategory = retval[3];
				}
				
				else{
				category = "business";
				// subcategory = retval[3]+"/"+retval[4];
				subcategory = retval[4];
				// link.replace(subcategory,"$");
				}
				
					}
					catch(Exception e){
					   e.printStackTrace();	
					   continue;
					}
				
				
				content = keywords;
				System.out.println(category);
				System.out.println(subcategory);
				System.out.println(business_link);
				Map<String, Object> categoriesData = null;
				
					
				
				
				if(content == null || content == "" || content == "-")
					content = business_link;
				
				
				try{
				 categoriesData = IndexCategoriesData.putJsonDocument(category,subcategory,content);
				 IndexCategoriesData.postElasticSearch(categoriesData);
				}
				catch(Exception e){
					e.printStackTrace();
					continue;
				}
				}
				
			}

		}

	}

	public static void LoadTechData(String category, String subcategory,
			String keywords, String content, String domain, Document doc,
			List<String> links) {

		try{
		
		links = Crawler.extractLinks(domain);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		// String [] content = null;

		domain = "http://indianexpress.com/article/technology/";
		
		
		for (String tech_link : links) {

			if (tech_link.contains(domain) == true) {
				System.out.println(tech_link);
				subcategory = null;

				// tech
				if (tech_link.contains("articlelistls") == false
						& tech_link.contains("speciallist") == false
						& tech_link.contains("articlelist") == false
						& tech_link.contains("photostory") == false
						& tech_link.contains("sphome") == false
						& tech_link.contains("#") == false
						& tech_link.contains("1") == false
						& tech_link.contains("slideshow") == false
						& tech_link.contains("storylist") == false)
				{
					try{
					doc = Jsoup.connect(tech_link).timeout(1000*1000).get();
					}
				    catch(Exception e){
				    	
				    	 e.printStackTrace();
				         continue;
				    }
			
				}	
					
					if (doc != null) {
					
					try{	
					keywords = doc.select("meta[name=keywords]").first().attr("content");
					System.out.println("Meta keyword : " + keywords);
					String description = doc.select("meta[name=description]")
							.get(0).attr("content");
					System.out.println("Meta description : " + description);
				

				tech_link.replace("//", "-");
				String[] retval = tech_link.split("/", 0);
				category = retval[4];
				// subcategory = retval[3]+"/"+retval[4];
				subcategory = retval[5];
				// link.replace(subcategory,"$");
				content = keywords;
					}
				catch(Exception e)	
					{
					 continue;
					}
				
				System.out.println(category);
				System.out.println(subcategory);
				System.out.println(tech_link);
				Map<String, Object> categoriesData = null;
		 		// categoriesData =
				if(content == null || content == "" || content == "-")
					content = tech_link;
				
				
				
				
				try{
				categoriesData=IndexCategoriesData.putJsonDocument(category,subcategory,content);
				IndexCategoriesData.postElasticSearch(categoriesData);
				}
				catch(Exception e){
					e.printStackTrace();
					continue;
				}
			
					}
				
				
				
				}

		}

	}

	public static void LoadLifestyleData(String category, String subcategory,
			String keywords, String content, String domain, Document doc,
			List<String> links) {

		try{
		links = Crawler.extractLinks(domain);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// String [] content = null;

		domain = "http://indianexpress.com/article/lifestyle/";
		
		
		
		for (String lifestyle_link : links) {

			if (lifestyle_link.contains(domain) == true) {
				System.out.println(lifestyle_link);
				subcategory = null;

				// tech
				if (lifestyle_link.contains("articlelistls") == false
						& lifestyle_link.contains("speciallist") == false
						& lifestyle_link.contains("articlelist") == false
						& lifestyle_link.contains("photostory") == false
						& lifestyle_link.contains("sphome") == false
						& lifestyle_link.contains("#") == false
						& lifestyle_link.contains("1") == false
						& lifestyle_link.contains("slideshow") == false
						& lifestyle_link.contains("storylist") == false)
				 {
					try {
						doc = Jsoup.connect(lifestyle_link).timeout(1000*1000).get();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				        continue;
					}
				}
					
				if (doc != null) {
					
					try{
					keywords = doc.select("meta[name=keywords]").first()
							.attr("content");
					System.out.println("Meta keyword : " + keywords);
					String description = doc.select("meta[name=description]")
							.get(0).attr("content");
					System.out.println("Meta description : " + description);
				

				lifestyle_link.replace("//", "-");
				String[] retval = lifestyle_link.split("/", 0);
				category = retval[4];
				// subcategory = retval[3]+"/"+retval[4];
				subcategory = retval[5];
				// link.replace(subcategory,"$");
				content = keywords;
					}
					catch(Exception e)
					{
						
						continue;
					}
				System.out.println(content);
				System.out.println(category);
				System.out.println(subcategory);
				System.out.println(lifestyle_link);
				Map<String, Object> categoriesData = null;
				// categoriesData =
				
				if(content == null || content == "" || content == "-")
					content = lifestyle_link;
				
				
				try{
				categoriesData=IndexCategoriesData.putJsonDocument(category,subcategory,content);
				 IndexCategoriesData.postElasticSearch(categoriesData);
				}
				catch(Exception e){
					
					e.printStackTrace();
					continue;
				}
				 
				}
				 
			}
		}

	}

	
	
	public static void LoadTravelData(String category, String subcategory,
			String keywords, String content, String domain, Document doc,
			List<String> links)  {

		try {
			links = Crawler.extractLinks(domain);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// String [] content = null;

		for (String travel_link : links) {

			if (travel_link.contains(domain) == true) {
				System.out.println(travel_link);
				subcategory = null;

				// tech
				if (travel_link.contains("articlelistls") == false
						& travel_link.contains("speciallist") == false
						& travel_link.contains("articlelist") == false
						& travel_link.contains("photostory") == false
						& travel_link.contains("sphome") == false
						& travel_link.contains("#") == false
						& travel_link.contains("1") == false
						& travel_link.contains("slideshow") == false
						& travel_link.contains("twitter") == false
						& travel_link.contains("?") == false
				        & travel_link.contains("storylist") == false)
				{
					
					try {
						doc = Jsoup.connect(travel_link).timeout(1000*1000).get();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					    continue;
					}
				}
				
				
				if (doc != null) {
			      try{
					
					keywords = doc.select("meta[name=keywords]").first()
							.attr("content");
					System.out.println("Meta keyword : " + keywords);
					String description = doc.select("meta[name=description]")
							.get(0).attr("content");
					System.out.println("Meta description : " + description);
				

				travel_link.replace("//", "-");
				String[] retval = travel_link.split("/", 0);
				category = retval[3];
				// subcategory = retval[3]+"/"+retval[4];
				subcategory = retval[4];
				// link.replace(subcategory,"$");
			
				content = keywords;
			      }
			      catch(Exception e)
			      {
			    	 continue; 
			      }
				System.out.println(category);
				System.out.println(subcategory);
				System.out.println(travel_link);
				Map<String, Object> categoriesData = null;
		 		// categoriesData =
	      	 
				try{
				categoriesData=IndexCategoriesData.putJsonDocument(category,subcategory,content);
			    IndexCategoriesData.postElasticSearch(categoriesData);
				}
				catch(Exception e){
					
					e.printStackTrace();
					continue;
				}
			    
				}  
			}

		}

	}
	
	
	public static void LoadCurrentAffairsData(String category,
			String subcategory, String keywords, String content, String domain,
			Document doc, List<String> links)  {

		try {
			links = Crawler.extractLinks(domain);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (String lifestyle_link : links) {

			if (lifestyle_link.contains(domain) == true) {
				System.out.println(lifestyle_link);
				subcategory = null;

				// life_style
				if (lifestyle_link.contains("articlelistls") == false
						& lifestyle_link.contains("speciallist") == false
						& lifestyle_link.contains("articlelist") == false
						& lifestyle_link.contains("photostory") == false
						& lifestyle_link.contains("sphome") == false
						& lifestyle_link.contains("#") == false
						& lifestyle_link.contains("1") == false
						& lifestyle_link.contains("slideshow") == false
						& lifestyle_link.contains("storylist") == false)
			    	{
					try {
						doc = Jsoup.connect(lifestyle_link).timeout(1000*1000).get();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					    continue;
					}
				}
					
				if (doc != null) {
					
					try{
					keywords = doc.select("meta[name=keywords]").first()
							.attr("content");
					System.out.println("Meta keyword : " + keywords);
					String description = doc.select("meta[name=description]")
							.get(0).attr("content");
					System.out.println("Meta description : " + description);
				

				lifestyle_link.replace("//", "-");
				String[] retval = lifestyle_link.split("/", 0);
				category = retval[4];
				// subcategory = retval[3]+"/"+retval[4];
				subcategory = retval[5];
				// link.replace(subcategory,"$");
				content = keywords;
					}
				      catch(Exception e)
				      {
				    	 continue; 
				      }
				
				
				System.out.println(category);
				System.out.println(subcategory);
				System.out.println(lifestyle_link);
				Map<String, Object> categoriesData = null;
				// categoriesData =
			  
				if(content == null || content == "" || content == "-")
					content = lifestyle_link;
				
				
				
				try{
				categoriesData=IndexCategoriesData.putJsonDocument(category,subcategory,content);
				IndexCategoriesData.postElasticSearch(categoriesData);
				}
				catch(Exception e){
					
					e.printStackTrace();
					continue;
				}
				
				
				
				
				}
			}	
		}

	}

}
