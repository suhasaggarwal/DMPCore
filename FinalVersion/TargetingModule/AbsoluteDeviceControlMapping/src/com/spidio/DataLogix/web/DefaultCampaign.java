package com.spidio.DataLogix.web;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;

import com.spidio.UserSegmenter.IndexCategoriesData;




/**
 * Derive Categories
 * Takes Text as Parameter
 * 
 * 
 * 
 */

public class DefaultCampaign extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String document_id = null;	
		String campaignId = null;
		String combinedCampIds = null;
        String campaign_mapping = null;
		String targetedCampaign = null;
		
		Client client = new TransportClient()
		.addTransportAddress(new InetSocketTransportAddress(
				"localhost", 9300));
				
		if(request.getParameter("fingerprintId")!=null && 
					!request.getParameter("fingerprintId").equals("")){
			
			SearchHit[] results = IndexCategoriesData.searchEntireUserData(client, "dmpdata","core2");
			for (SearchHit hit : results) {
				System.out.println("------------------------------");
				Map<String, Object> result = hit.getSource();
		        
		        document_id = (String) result.get("_id");
			    campaign_mapping = (String) result.get("campaign_mapping");
			}
	
			
				targetedCampaign = "12";
			
			
		
			IndexCategoriesData.updateDocument(client, "dmpdata","core2",document_id, "campaign_mapping", targetedCampaign);
					
	}
	
	}
	
}
