
package com.spidio.ClassificationDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class IndexCategoriesData {

	
private static Client client;
    
    

    /**
     * This method returns ESclient instance.
     *
     * @return the es client
     * ESClient instance
     */
	public static Client getEsClient() {

		if (client == null) {
			client = getES();
		}
		return client;
	}

	/**
	 * Sets the es client.
	 *
	 * @param esClient the new es client
	 */
	public  void setEsClient(final Client esClient) {

		IndexCategoriesData.client = esClient;
	}

	/**
	 * Gets the es.
	 *
	 * @return the es
	 */
	private static Client getES() {
		try {
			
			final Builder settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch");
			
			final TransportClient transportClient = new TransportClient(settings);
			transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
			
			return transportClient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
	
	
	
    public static void main (String args[]){
        IndexCategoriesData p = new IndexCategoriesData();
      //  p.postElasticSearch();
    }


    public static Map<String, Object> putJsonDocument(String category, String subcategory, String content){

            Map<String, Object> jsonDocument = new HashMap<String, Object>();
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String postDate = dateFormat.format(date);
            jsonDocument.put("category", category);
            jsonDocument.put("subcategory", subcategory);
            jsonDocument.put("postDate", postDate);
            jsonDocument.put("content", content);

            return jsonDocument;
    }

    //Use context of time to distinguish mixed categories

    public static void postElasticSearch(Map<String, Object> categoryDocument){

    	getEsClient();
    	
    	String value = (String)categoryDocument.get("content");
    	
   
  //  	SearchHit[] results = searchDocumentContent(client, "dmpcategoriesdata" ,"core2" ,"content" ,value);
  //  	if(results == null || results.length == 0)
    	client.prepareIndex("dmpcategoriesdata", "core2").setSource(categoryDocument).execute().actionGet();

    }
    //If content is already there and classified, it is not further analysed

public static SearchHit[] searchDocumentContent(Client client, String index, String type,
            String field, String value){

	
	
	
	SearchResponse response = client.prepareSearch(index)
              .setTypes(type)
              .setSearchType(SearchType.QUERY_AND_FETCH)
              .setQuery(QueryBuilders.matchQuery(field,value))   
              .setFrom(0).setSize(60).setExplain(true)
              .addSort("postDate",org.elasticsearch.search.sort.SortOrder.DESC)
              .execute()
              .actionGet();

SearchHit[] results = response.getHits().getHits();
System.out.println("Current results: " + results.length);

return results;
}
    
    
    
    
    
    
    
    
    
}