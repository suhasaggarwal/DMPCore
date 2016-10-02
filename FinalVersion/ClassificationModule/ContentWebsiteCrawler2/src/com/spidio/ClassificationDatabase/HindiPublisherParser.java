package com.spidio.ClassificationDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HindiPublisherParser {

	public String HindiContentCategoriser(String refUrl) {

		
		// "http://pratahkal.com/2016/01/17/%E0%A4%B0%E0%A4%BE%E0%A4%B9%E0%A5%81%E0%A4%B2-%E0%A4%95%E0%A5%8B-%E0%A4%B6%E0%A4%B0%E0%A5%8D%E0%A4%AE%E0%A4%BF%E0%A4%82%E0%A4%A6%E0%A4%97%E0%A5%80-%E0%A4%B8%E0%A5%87-%E0%A4%AC%E0%A4%9A%E0%A4%BE/";
		String languageAPI = "http://mymemory.translated.net/api/get?q="
				+ refUrl + "&langpair=hi|en";

		String jsonString = null;
		try {
			jsonString = HttpUtil.sendGet(languageAPI);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String categorisationText = null;
		try {
			categorisationText = jsonObject.getString("translation");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(categorisationText);

		return categorisationText;

	}


   //Useful if Title Does not contain Sufficient Information

	public String HindiPageHeadingsExtractor(String refUrl) {

		String escapedStr = "\u0930\u093e\u0939\u0941\u0932-\u0915\u094b-\u0936\u0930\u094d\u092e\u093f\u0902\u0926\u0917\u0940-\u0938\u0947-\u092c\u091a\u093e";
		

		String hindiStr = StringEscapeUtils.unescapeJava( escapedStr );

		System.out.println(hindiStr);
		
		Document doc1 = null;
		try {
			doc1 = Jsoup.parse(new URL(refUrl).openStream(), "UTF-8", refUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//	doc = Jsoup.connect(link).get(); 
			
		Elements sectionheadings = doc1.select("h1");
		Elements sectionheadings1 = doc1.select("h2");
		
		String heading1 = sectionheadings.text();
		String heading2 = sectionheadings1.text();
		
		// "http://pratahkal.com/2016/01/17/%E0%A4%B0%E0%A4%BE%E0%A4%B9%E0%A5%81%E0%A4%B2-%E0%A4%95%E0%A5%8B-%E0%A4%B6%E0%A4%B0%E0%A5%8D%E0%A4%AE%E0%A4%BF%E0%A4%82%E0%A4%A6%E0%A4%97%E0%A5%80-%E0%A4%B8%E0%A5%87-%E0%A4%AC%E0%A4%9A%E0%A4%BE/";
		String languageAPI = "http://mymemory.translated.net/api/get?q="
				+ heading1 + "&langpair=hi|en";

		
		

		String languageAPI1 = "http://mymemory.translated.net/api/get?q="
				+ heading2 + "&langpair=hi|en";


		try {
			heading2 = HttpUtil.sendGet(languageAPI1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	  return heading1+":"+heading2;
	
	
	
	}

	
	
	


























}
