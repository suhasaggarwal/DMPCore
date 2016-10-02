package com.spidio.ClassificationDatabase;

import java.util.ArrayList;
import java.util.List;

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
			System.out.println(link.toString());
			result.add(link.attr("abs:href"));
		}
		return result;
	}

	public final static void main(String[] args) throws Exception {
		// String site1 = "http://timesofindia.indiatimes.com/";

		
        String ie_sports = "http://indianexpress.com/sports/";
        String ie_entertainment = "http://indianexpress.com/entertainment/";
        String ie_tech = "http://indianexpress.com/technology/";
        String ie_lifestyle = "http://indianexpress.com/lifestyle/";
        String ie_business1 = "http://www.financialexpress.com/mutual-fund/";
        String ie_business2 = "http://www.financialexpress.com/section/markets/";
        String ie_business3 = "http://www.financialexpress.com/section/industry/companies/";
		String ie_business4 = "http://www.financialexpress.com/stock-market/";
		String ie_business5 = "http://www.financialexpress.com/currency/";
		String ie_nation_news = "http://indianexpress.com/article/india/";
        
		String category = null;
		String subcategory = null;
		String keywords = null;
		String description = null;
		String content = null;
		String domain = null;
		Document doc = null;
		List<String> links = null;

        for( int i=0; i<10; i++){		
		
        	
        if(i==0){	
        domain = ie_sports;
		try{
        LoadCategoryData.LoadSportsData(category, subcategory, keywords, content, domain, doc, links);
        }
	    catch(Exception e){
	     e.printStackTrace();	
	    	continue;
	    }
       
        }
		
        
        if(i==1){
		domain = ie_entertainment;
		try{
		LoadCategoryData.LoadEntertainmentData(category, subcategory, keywords, content, domain, doc, links);
        }
		catch(Exception e){
			e.printStackTrace();
			continue;
			
		}
        }
        
        if(i==2){
	    domain = ie_tech;
		try{
	    LoadCategoryData.LoadTechData(category, subcategory, keywords, content, domain, doc, links);
        }
        catch(Exception e){
        	e.printStackTrace();
            continue;	
        }
        }
        
        
        if(i == 3){
        domain = ie_lifestyle;
		try{
        LoadCategoryData.LoadLifestyleData(category, subcategory, keywords, content, domain, doc, links);
		}
		catch(Exception e){
		  e.printStackTrace();
		  continue;
        }
        }
        
		
        if(i==4){
		domain = ie_business1;
		try{
		LoadCategoryData.LoadBusinessData(category, subcategory, keywords, content, domain, doc, links);
        }
		catch(Exception e){
		e.printStackTrace();
		continue;
		}
        }
        
        
        if(i==5){
		domain = ie_business2;
		try{
		LoadCategoryData.LoadBusinessData(category, subcategory, keywords, content, domain, doc, links);
		}
		catch(Exception e){
			e.printStackTrace();
		    continue;
		 } 
        }
		
        
        
        if(i==6){
		domain = ie_business3;
		try{
		LoadCategoryData.LoadBusinessData(category, subcategory, keywords, content, domain, doc, links);
        }
        catch(Exception e){
           e.printStackTrace();
           continue;
        }
        	
        }
        
        
		if(i==7){
		domain = ie_business4;
		try{
		LoadCategoryData.LoadBusinessData(category, subcategory, keywords, content, domain, doc, links);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			continue;
		}
		}

		if(i==8){
		domain = ie_business5;
        try{
		LoadCategoryData.LoadBusinessData(category, subcategory, keywords, content, domain, doc, links);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	continue;
        }
		}
		
		if(i==9){
		domain = ie_nation_news;
		try{
		LoadCategoryData.LoadCurrentAffairsData(category, subcategory, keywords, content, domain, doc, links);
		} 
        catch(Exception e){
        	
        	e.printStackTrace();
        	continue;
        }
		
		}
		
		}	
		
	
	
	}

}
