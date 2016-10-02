package com.spidio.UserSegmenter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProcessRefurl {

	public static String getKeywords(String url) throws Exception {
		final ArrayList<String> result = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(1000*1000).get(); //Configure socket read timeout to prevent timeouts on load intensive websites
		String keywords = null;
		String description = null;
		if (doc != null) {
			keywords = doc.select("meta[name=keywords]").first()
					.attr("content");
			System.out.println("Meta keyword : " + keywords);
			description = doc.select("meta[name=description]")
					.get(0).attr("content");
			System.out.println("Meta description : " + description);
		}
		
		return keywords;
	}

	
	public static String getDescription(String url) throws Exception {
		
		Document doc = Jsoup.connect(url).timeout(1000*1000).get(); //Configure socket read timeout to prevent timeouts on load intensive websites
		String keywords = null;
		String description = null;
		if (doc != null) {
			
			description = doc.select("meta[name=description]")
					.get(0).attr("content");
			System.out.println("Meta description : " + description);
		}
		
		return description;
	}
	
	
	
	
	
	
}
