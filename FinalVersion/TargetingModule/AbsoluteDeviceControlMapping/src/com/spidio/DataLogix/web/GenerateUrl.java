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

public class GenerateUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String document_id = null;	
		String campaignId = null;
		String combinedCampIds = null;
        String campaign_mapping = null;
		String targetedCampaign = null;
		String fingerprintId =null;
		String request_time=null;
		
	     Client client = new TransportClient()
		.addTransportAddress(new InetSocketTransportAddress(
				"localhost", 9300));
		
		
	       SearchHit[] results = IndexCategoriesData.searchEntireUserData(client,"dmpdata","core2");
			
	       for(SearchHit hit1:results)  {
            Map<String, Object> result1 = hit1.getSource();
   	          
   	         fingerprintId = (String) result1.get("fingerprint_id");  
   	  
   	   //         System.out.println(fingerprintId);
			    String [] fingerprintSegments = fingerprintId.split("\\.");
			    if(fingerprintSegments.length > 1)
				fingerprintId = fingerprintSegments[0]+"."+fingerprintSegments[1]+"."+fingerprintSegments[2];
			    String generatedUrl = "http://54.175.49.247:8080/d_control2/MapFingerprint?fingerprintId="+fingerprintId+"&campaignId=<Put campaign mapping here>";
			    request_time = (String) result1.get("request_time");  
			    PrintWriter output = response.getWriter();

			    output.println("Time of Entry:"+request_time+" Url for Mapping: "+ generatedUrl+"\n");

			    
			
			}			
	
	
	}

}
