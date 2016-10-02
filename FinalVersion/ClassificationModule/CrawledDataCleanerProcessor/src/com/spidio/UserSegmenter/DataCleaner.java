package com.spidio.UserSegmenter;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class DataCleaner {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	Client client = new TransportClient()
	.addTransportAddress(new InetSocketTransportAddress(
			"10.12.2.61", 9300));
	
	String keywords = null;
	String description = null;
	String [] searchkeyword = null;
	String [] finalsearchkeyword = null;
	SearchHit[] matchingsegmentrecords = null;
	String category = null;
	String subcategory = null;
	String content = null;
	String postdate = null;
		
	SearchHit[] results = IndexCategoriesData.searchEntireUserData(client, "dmpcategoriesdata","core2");

	
	
	//Scroll until no hits are returned


	    for (SearchHit hit : results) {
	        
	    	System.out.println("------------------------------");
			Map<String, Object> result = hit.getSource();
	        content = (String) result.get("content");  
	        System.out.println(content);
	        category = (String) result.get("category");
	        postdate = (String) result.get("postDate");
	        
	        String documentId = hit.getId();
	        
	
	
	        
	        if(content == null || content.equals("") || content.equals("-") || content.equals("...")){
	        DeleteResponse response1 = client.prepareDelete("dmpcategoriesdata", "core2", documentId)
	            .execute()
	            .actionGet();
	           System.out.println(documentId+":"+content);
	        } 
	         
	        
	        if(postdate == null || postdate.equals("") || postdate.equals("-") || postdate.equals("...")){
	        	 DeleteResponse response2 = client.prepareDelete("dmpcategoriesdata", "core2", documentId)
	            .execute()
	            .actionGet();
	           System.out.println(documentId+":"+postdate);
	        } 
	        
	        
	        
	        
	        if(category == null || category.equals("") || category.equals("-") || category.equals("...")){
	        	 DeleteResponse response3 = client.prepareDelete("dmpcategoriesdata", "core2", documentId)
	    	            .execute()
	    	            .actionGet();
	    	           System.out.println(documentId+":"+category);
	        	
	        }
	       
	       
	        
	        if(category != null && category.isEmpty()==false && category.equals("article")){
	        	IndexCategoriesData.updateDocument(client, "dmpcategoriesdata","categoriesdata", documentId, "core2","News");
	    	           System.out.println(documentId+":"+category);
	        	
	        }
	        
	        
	        
	        if(category != null && category.isEmpty()==false && category.equals("india-news")){
	        	IndexCategoriesData.updateDocument(client, "dmpcategoriesdata","core2", documentId, "category","News");
	    	           System.out.println(documentId+":"+category);
	        	
	    	           
	        }
	        
	        
	        if(category != null && category.isEmpty()==false && category.equals("india")){
	        	IndexCategoriesData.updateDocument(client, "dmpcategoriesdata","core2", documentId, "category","News");
	    	           System.out.println(documentId+":"+category);
	        	
	    	           
	        }

	        
	        
	        
	        
	        if(category != null && category.isEmpty()==false && category.equals("indianexpress.com")){
	        	IndexCategoriesData.updateDocument(client, "dmpcategoriesdata","core2", documentId, "category","News");
	    	           System.out.println(documentId+":"+category);
	        	
	        }
	        
	        
	        
	        
	        
	        if(content != null && content.isEmpty()==false && content.contains("voindia")){
	        	 DeleteResponse response4 = client.prepareDelete("dmpcategoriesdata", "core2", documentId).execute().actionGet();
	    	    
	        	System.out.println("TEST: "+content);
	        	
	        	
	        }
	        
	        
	        
	        if(content != null && content.isEmpty()==false && content.contains("timesofindia")){
	        	 DeleteResponse response4 = client.prepareDelete("dmpcategoriesdata", "core2", documentId).execute().actionGet();
	    	    
	        	System.out.println("TEST: "+content);
	        	
	        	
	        }
	        
	        
	        if(content != null && content.isEmpty()==false && content.contains("The Times of India")){
	        	 DeleteResponse response4 = client.prepareDelete("dmpcategoriesdata", "core2", documentId).execute().actionGet();
	    	    
	        	System.out.println("TEST: "+content);
	        	
	        	
	        }
	        
	        
	        if(content != null && content.isEmpty()==false && content.contains("indianexpress.com")){
	        	 DeleteResponse response4 = client.prepareDelete("dmpcategoriesdata", "core2", documentId).execute().actionGet();
	    	    
	        	System.out.println("TEST: "+content);
	        	
	        	
	        }
	        
	        
	        
	        
	        
	        
	        
	        
	        } 
	    
	  
	
	
	
	
	
}


	}
	






