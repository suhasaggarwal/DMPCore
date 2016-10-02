package com.spidio.UserSegmenter;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class RefurlAnalyserUserData {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	Client client = new TransportClient()
	.addTransportAddress(new InetSocketTransportAddress(
			"52.22.164.163", 9300));
	
	String keywords = null;
	String description = null;
	String [] searchkeyword = null;
	String [] finalsearchkeyword = null;
	SearchHit[] matchingsegmentrecords = null;
	String category = null;
	String subcategory = null;
	
		
//	SearchHit[] results = IndexCategoriesData.searchEntireUserData(client, "dmpuserdatabase","core2");

	
	SearchResponse response = client.prepareSearch("dmpuserdatabase")
	          .setTypes("core2")
	          .setSearchType(SearchType.QUERY_AND_FETCH)
	          .setQuery(QueryBuilders.matchAllQuery())   
	          .setFrom(0).setSize(60).setExplain(true)
	          .execute()
	          .actionGet();
	//Scroll until no hits are returned
	while (true) {

	    for (SearchHit hit : response.getHits().getHits()) {
	        
	    	System.out.println("------------------------------");
			Map<String, Object> result = hit.getSource();
	        String refurl = (String) result.get("referrer");     
	        String documentId = (String)result.get("_id");
	        keywords = ProcessRefurl.getKeywords(refurl);
	        description = ProcessRefurl.getDescription(refurl);
	        Map<String,String> data = new HashMap<String,String>();
	   
	        data.put("keywords",keywords);
	        data.put("description",description);
	         
	        client.prepareUpdate("dmpuserdatabase","core2",documentId).setDoc(data).setRefresh(true).execute().actionGet();
	    
	    }
	     response = client.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
	    //Break condition: No hits are returned
	    if (response.getHits().getHits().length == 0) {
	        break;
	    }
	}
	
	}
	
	
	
}








