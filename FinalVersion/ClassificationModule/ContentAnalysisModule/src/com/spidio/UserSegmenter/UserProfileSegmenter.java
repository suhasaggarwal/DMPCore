package com.spidio.UserSegmenter;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.xml.sax.InputSource;

import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.extractors.ArticleExtractor;
import com.kohlschutter.boilerpipe.sax.BoilerpipeSAXInput;
import com.kohlschutter.boilerpipe.sax.HTMLFetcher;
import com.sree.textbytes.jtopia.TermDocument;
import com.sree.textbytes.jtopia.TermsExtractor;

public class UserProfileSegmenter {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	  
		Client client = new TransportClient()
		.addTransportAddress(new InetSocketTransportAddress(
				"localhost", 9300));
		
		String keywords = null;
		String description = null;
		String [] searchkeyword = null;
		String [] searchkeyword1 = null;
		
//		String [] finalsearchkeyword = null;
		String finalsearchkeyword;
		SearchHit[] matchingsegmentrecords = null;
		String category = null;
		String subcategory = null;
		String refurl = null;
		String audienceSegment = null; 
		String [] parts = null;
		TreeMap<String, Integer> categoryCount = new TreeMap<String, Integer>();
        TreeMap<String,Integer> subcategoryMap = new TreeMap<String,Integer>();
		Integer count = 0;
         Integer flag = 0;
         Integer count1 = 0;
	     String id;
         Integer count2 = 0;
	     
         SearchHit[] results = IndexCategoriesData.searchEntireUserData(client, "testdata2","core2");		
		 for (SearchHit hit : results) {
			System.out.println("------------------------------");
		    Map<String, Object> result = hit.getSource();
	        refurl = (String) result.get("referrer");     
		    id = (String) hit.getId();
			
		    refurl = "http://timesofindia.indiatimes.com/entertainment/hindi/bollywood/news/Deepika-Padukone-goes-house-hunting-with-Ruby-Rose/articleshow/51373194.cms"; 
		    keywords = ProcessRefurl.getKeywords(refurl);
	        description = ProcessRefurl.getDescription(refurl);
	        System.out.println("Description:"+description);
			if(keywords != null || keywords != "" || keywords != "-" || refurl.contains("voindia")==false)
			{
				
				searchkeyword = keywords.split(",");
			}
			
			if(audienceSegment != null || audienceSegment != "" ){
			for(int j=0; j<searchkeyword.length; j++)
	        {
	        	/*
				if(count2 == 0)
					
	        	{
	        	finalsearchkeyword = searchkeyword[j];
	            System.out.println(finalsearchkeyword);
	        	searchkeyword1 = finalsearchkeyword.split("/");
	            finalsearchkeyword = searchkeyword1[3];
	          	
	        	}
				
				*/
				finalsearchkeyword = searchkeyword[j].trim();
	       // 	finalsearchkeyword = refurl;
	            System.out.println(finalsearchkeyword+"\n");
	        	matchingsegmentrecords = IndexCategoriesData.searchDocumentContent(client,"dmpcategoriesdata","categoriesdata","content",finalsearchkeyword);
	            System.out.println("Size:"+matchingsegmentrecords.length);
	        	
	        	if(matchingsegmentrecords != null){
	            	for(SearchHit hit1 : matchingsegmentrecords)  {
	                Map<String, Object> result1 = hit1.getSource();
	    	        System.out.println(result1);
	                category = (String) result1.get("category");     
	    		    subcategory = (String)result1.get("subcategory");
	    		   
	    		    
	    		    if(category != null && !category.isEmpty()){
	         			if(categoryCount.containsKey(category) == false){
	    					categoryCount.put(category,1);    
	    					
	         			}
	         			 else{
	    						 
	         				     count=categoryCount.get(category);	
	         				     categoryCount.put(category,count+1);	
	         				     
	         			 }
	          
	         			
	         			if(subcategoryMap.containsKey(category+":"+subcategory) == false)
	    	    		    subcategoryMap.put(category+":"+subcategory,1);
	         			else
	         			{
	         				count1= subcategoryMap.get(category+":"+subcategory);
	         				subcategoryMap.put(category+":"+subcategory,count1+1);
	         			}
	         			
	         			
	         			//      subcategory = (String) result1.get("subcategory");
	            	System.out.println("Category:" + category + "\n");
	    	     //   System.out.println("Sub Category:" + subcategory + "\n");
	    	  
	    	        
	    	    //Have to add update elasticsearch API    
	    	        
	    	        //  if(category != null)
	            //	break;
	            	}
	            }
	        
	        }
	      
		}
			
			  int maxValueInMap=(Collections.max(categoryCount.values()));  // This will return max value in the Hashmap
		        for (Entry<String, Integer> entry : categoryCount.entrySet()) {  // Itrate through hashmap
		            if (entry.getValue()==maxValueInMap) {
		                category = entry.getKey();     // Print the key with max value
		            }
		        }
		        
	//		 subcategory = subcategoryMap.firstKey();
   		//     parts = subcategory.split(":");
   		//     subcategory = parts[1];
	     	 IndexCategoriesData.updateDocument(client, "livedmpindex","core2",id,"category",category);		
			}
			
	
		 }
		
	}

}	
	

