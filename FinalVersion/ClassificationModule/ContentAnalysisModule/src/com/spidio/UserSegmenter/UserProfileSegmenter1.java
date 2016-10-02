package com.spidio.UserSegmenter;

import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;

public class UserProfileSegmenter1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	  
		Client client = new TransportClient()
		.addTransportAddress(new InetSocketTransportAddress(
				"52.22.164.163", 9300));
		
		String keywords = null;
		String description = null;
		String [] searchkeyword = null;
		String finalsearchkeyword = null;
		String [] searchkeyword1 = null;
		SearchHit[] matchingsegmentrecords = null;
		String category = null;
		String subcategory = null;
		String document_id = null;	
		String refurl = null;
		
		SearchHit[] results = IndexCategoriesData.searchEntireUserData(client, "dmpuserdatabase","core2");
		for (SearchHit hit : results) {
			System.out.println("------------------------------");
			Map<String, Object> result = hit.getSource();
	        refurl = (String) result.get("referrer");     
	        document_id = (String) result.get("_id");
	        searchkeyword = refurl.split("-");
	    //    description = ProcessRefurl.getDescription(refurl);
	   //     searchkeyword = keywords.split(",");
	        for(int j=0; j<searchkeyword.length; j++)
	        {
	        	      
	        	if(j == 0)
	        	{
	        	finalsearchkeyword = searchkeyword[j];
	            searchkeyword1 = finalsearchkeyword.split("/");
	            finalsearchkeyword = searchkeyword1[3];
	          	}
	        	else
	        	finalsearchkeyword = searchkeyword[j];
	        	
	        	matchingsegmentrecords = IndexCategoriesData.searchDocumentContent(client,"dmpcategoriesdatabasev3","categoriesData","content",finalsearchkeyword);
	            if(matchingsegmentrecords != null){
	            	for(SearchHit hit1 : matchingsegmentrecords)  {
	                Map<String, Object> result1 = hit1.getSource();
	    	        category = (String) result1.get("category");     
	    	        subcategory = (String) result1.get("subcategory");
	            	System.out.println("Category:" + category + "\n");
	    	        System.out.println("Sub Category:" + subcategory + "\n");
	    	        if(category != null)
	            	break;
	            	}
	            }
	        
	        }
	      
		    IndexCategoriesData.updateDocument(client, "dmpuserdatabase","core2",document_id, "Category", category);
		
		}

	}
	
}
