package com.spidio.DataLogix.web;





import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.get.GetResponse;
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

public class MapFingerprint extends HttpServlet {
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
			
			//    SearchHit[] results = IndexCategoriesData.searchEntireUserData(client, "dmpdata","core2");
		    //    GetResponse getResponse = client.prepareGet("dmpdata", "core2", "1").execute().actionGet();     
		    	SearchHit[] results = com.spidio.UserSegmenter.ElasticSearchAPIs.searchDocumentFingerprintIds(client, "dmpdata", "core2",
								"fingerprint_id", request.getParameter("fingerprintId"));
			
			for (SearchHit hit : results) {
				System.out.println("------------------------------");
				Map<String, Object> result = hit.getSource();
		        System.out.println(result);
		       
	             document_id = hit.getId();
			    
		  //      document_id = (String) getResponse.getId();
		        System.out.println("Id:"+document_id);
		        campaign_mapping = (String) result.get("campaign_mapping");
		        System.out.println("mapping:" +campaign_mapping);
			
			
			
	
			targetedCampaign = request.getParameter("campaignId");
					
				
			System.out.println(targetedCampaign);
		/*	
			
			if(campaign_mapping != null && campaign_mapping !="")
			{
				
				combinedCampIds = campaign_mapping + ',' + targetedCampaign;
				System.out.println("comb:"+combinedCampIds);
				
			
			}
			
			else
			combinedCampIds = targetedCampaign;
		*/	
			
			if(targetedCampaign!=null && !targetedCampaign.equals(""))
			IndexCategoriesData.updateDocument(client, "dmpdata","core2",document_id, "campaign_mapping", targetedCampaign);
	  }			
	}
	
	}
	
}
