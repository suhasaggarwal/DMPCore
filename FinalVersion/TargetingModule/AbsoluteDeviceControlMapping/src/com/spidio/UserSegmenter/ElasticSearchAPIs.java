package com.spidio.UserSegmenter;
import javax.swing.SortOrder;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;



public class ElasticSearchAPIs {

public static SearchHit[] searchDocumentFingerprintIds(Client client, String index, String type,
            String field, String value){

	
	
	
	SearchResponse response = client.prepareSearch(index)
              .setTypes(type)
              .setSearchType(SearchType.QUERY_AND_FETCH)
              .setQuery(QueryBuilders.matchQuery(field,value))   
              .setFrom(0).setSize(60).setExplain(true)
              .addSort("request_time",org.elasticsearch.search.sort.SortOrder.DESC)
              .execute()
              .actionGet();

SearchHit[] results = response.getHits().getHits();
System.out.println("Current results: " + results.length);
/*
for (SearchHit hit : results) {
System.out.println("------------------------------");
Map<String,Object> result = hit.getSource();   

System.out.println(result);
return result;
*/
return results;
}

public static SearchHit[] searchDocumentCampaignIds(Client client, String index, String type,
        String field, String value){




SearchResponse response = client.prepareSearch(index)
          .setTypes(type)
          .setSearchType(SearchType.QUERY_AND_FETCH)
          .setQuery(QueryBuilders.matchQuery(field,value))   
          .setFrom(0).setSize(60).setExplain(true)
          .addSort("created_date_time",org.elasticsearch.search.sort.SortOrder.DESC)
          .execute()
          .actionGet();

SearchHit[] results = response.getHits().getHits();
//System.out.println("Current results: " + results.length);
/*
for (SearchHit hit : results) {
System.out.println("------------------------------");
Map<String,Object> result = hit.getSource();   

System.out.println(result);
return result;
*/
return results;
}

}





