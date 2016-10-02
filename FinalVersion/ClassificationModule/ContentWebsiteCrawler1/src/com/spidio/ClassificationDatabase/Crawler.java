package com.spidio.ClassificationDatabase;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {


/*	
	Three websites are being crawled for preparing database for classification.
	Crawls full websites and generate records corresponding to different categories and subcategories.
	This can be used to map categories, subcategories corresponding to different keywords and can result in record classification.
*/
	
	
		
	public static List<String> extractLinks(String url) throws Exception {
		final ArrayList<String> result = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(1000 * 1000).get(); // Configure
																		// socket
																		// read
																		// timeout
																		// to
																		// prevent
																		// timeouts
																		// on
																		// load
																		// intensive
																		// websites
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			System.out.println(link.toString());
			result.add(link.attr("abs:href"));
		}
		return result;
	}

	public final static void main(String[] args) throws Exception {
		// String site1 = "http://timesofindia.indiatimes.com/";

		// String sports =
		// "http://timesofindia.indiatimes.com/topic/home/sports";
		String sub_sports1 = "http://timesofindia.indiatimes.com/sports/football";
		String sub_sports2 = "http://timesofindia.indiatimes.com/sports/tennis";
		String sub_sports3 = "http://timesofindia.indiatimes.com/sports/hockey";
		String sub_sports4 = "http://timesofindia.indiatimes.com/sports/golf";
		String sub_sports5 = "http://timesofindia.indiatimes.com/sports/racing";
		String sub_sports6 = "http://timesofindia.indiatimes.com/sports/nba";
		String sub_sports7 = "http://timesofindia.indiatimes.com/sports/chess";
		String sub_sports8 = "http://timesofindia.indiatimes.com/sports/badminton";
		String sub_sports9 = "http://timesofindia.indiatimes.com/sports/boxing";
		String sub_sports10 = "http://timesofindia.indiatimes.com/sports/ipl";
		String entertainment = "http://timesofindia.indiatimes.com/topic/home/entertainment";
		String sub_entertainment1 = "http://timesofindia.indiatimes.com/entertainment/hindi/bollywood/";
		String sub_entertainment2 = "http://timesofindia.indiatimes.com/entertainment/english/hollywood/";
		String sub_entertainment3 = "http://timesofindia.indiatimes.com/entertainment/hindi/music/";
		String sub_entertainment4 = "http://timesofindia.indiatimes.com/entertainment/english/music";
		String sub_entertainment5 = "http://timesofindia.indiatimes.com/entertainment/tamil/movies";
		String sub_entertainment6 = "http://timesofindia.indiatimes.com/entertainment/malayalam/movies";
		String business = "http://timesofindia.indiatimes.com/topic/home/business";
		String sub_business1 = "http://timesofindia.indiatimes.com/business/india-business";
		String sub_business2 = "http://timesofindia.indiatimes.com/business/international-business";
		String sub_business3 = "http://economictimes.indiatimes.com/markets";
		String sub_business4 = "http://economictimes.indiatimes.com/personalfinance";
		String sub_business5 = "http://timesofindia.indiatimes.com/business/mf-simplified";
		String tech = "http://timesofindia.indiatimes.com/tech";
		String sub_tech1 = "http://timesofindia.indiatimes.com/tech/mobiles";
		String sub_tech2 = "http://timesofindia.indiatimes.com/tech/PCs";
		String sub_tech3 = "http://timesofindia.indiatimes.com/tech/apps";
		String sub_tech4 = "http://timesofindia.indiatimes.com/tech/gaming";
		String sub_tech5 = "http://timesofindia.indiatimes.com/tech/computing";
		String nation_news = "http://indianexpress.com/article/india/";
		String sub_life_style1 = "http://timesofindia.indiatimes.com/life-style/health-fitness";
		String sub_life_style2 = "http://timesofindia.indiatimes.com/life-style/fashion";
		String sub_life_style3 = "http://timesofindia.indiatimes.com/life-style/beauty";
		String sub_life_style4 = "http://timesofindia.indiatimes.com/life-style/home-garden";
		String sub_life_style5 = "http://timesofindia.indiatimes.com/life-style/food";
		String travel_wildlife = "http://www.lonelyplanet.com/india/things-to-do/wildlife-in-india";
		String travel_topeats = "http://www.lonelyplanet.com/india/things-to-do/india-s-top-eats";
		String travel_bazaars = "http://www.lonelyplanet.com/india/things-to-do/india-s-best-bazaars";
		String travel_hotels = "http://www.lonelyplanet.com/india/hotels/heritage-hotels-in-india";
		String travel_temples = "http://www.lonelyplanet.com/india/things-to-do/temples-and-monuments-in-india";
		String travel_treks = "http://www.lonelyplanet.com/india/things-to-do/trekking-in-india";
		String travel_best_places = "http://www.lonelyplanet.com/india/hotels/best-places-to-stay-in-india";
		String travel_forts = "http://www.lonelyplanet.com/india/things-to-do/indian-history-forts-and-palaces";
		String travel_tea_plantations = "http://www.lonelyplanet.com/india/places/discover-india-s-amazing-tea-plantations";
		String travel_meditations = "http://www.lonelyplanet.com/india/things-to-do/meditation-and-yoga-in-india";
		String travel_places = "http://www.lonelyplanet.com/india/places";
		String travel_places1 = "http://www.lonelyplanet.com/india/rajasthan/jaipur";
		String travel_places2 = "http://www.lonelyplanet.com/india/rajasthan/things-to-do/top-things-to-do-in-rajasthan";
		String travel_places3 = "http://www.lonelyplanet.com/india/rajasthan/hotels/";
		String travel_places4 = "http://www.lonelyplanet.com/india/rajasthan/activities/";
		String travel_places4a = "http://www.lonelyplanet.com/india/rajasthan/shopping";
		String travel_places5 = "http://www.lonelyplanet.com/india/goa-mumbai";
		String travel_places6 = "http://www.lonelyplanet.com/india/goa-mumbai/hotels";
		String travel_places7 = "http://www.lonelyplanet.com/india/goa-mumbai/activities/";
		String travel_places8 = "http://www.lonelyplanet.com/india/goa-mumbai/things-to-do/";
		String travel_places8a = "http://www.lonelyplanet.com/india/goa-mumbai/restaurants/";
		String travel_places8b = "http://www.lonelyplanet.com/india/goa-mumbai/shopping/";
		String travel_places9 = "http://www.lonelyplanet.com/india/delhi-around";
		String travel_places10 = "http://www.lonelyplanet.com/india/delhi-around/hotels";
		String travel_places11 = "http://www.lonelyplanet.com/india/delhi-around/activities";
		String travel_places12 = "http://www.lonelyplanet.com/india/delhi-around/shopping/";
		String travel_places13 = "http://www.lonelyplanet.com/india/delhi-around/restaurants";
		String travel_places14 = "http://www.lonelyplanet.com/india/south-india-kerala";
		String travel_places15 = "http://www.lonelyplanet.com/india/south-india-kerala/hotels";
		String travel_places16 = "http://www.lonelyplanet.com/india/south-india-kerala/activities";
		String travel_places17 = "http://www.lonelyplanet.com/india/south-india-kerala/things_to-do";
		String travel_places18 = "http://www.lonelyplanet.com/india/south-india-kerala/restaurants";
		String travel_places19 = "http://www.lonelyplanet.com/india/south-india-kerala/shopping/";
		String travel_places20 = "http://www.lonelyplanet.com/india/uttar-pradesh/agra/hotels";
		String travel_places21 = "http://www.lonelyplanet.com/india/uttar-pradesh/agra/activities";
		String travel_places22 = "http://www.lonelyplanet.com/india/uttar-pradesh/agra/things-to-do";
		String travel_places23 = "http://www.lonelyplanet.com/india/uttar-pradesh/agra/restaurants";
		String travel_places24 = "http://www.lonelyplanet.com/india/uttar-pradesh/agra/shopping";
		String travel_places25 = "http://www.lonelyplanet.com/india/madhya-pradesh-and-chhattisgarh/hotels";
		String travel_places26 = "http://www.lonelyplanet.com/india/madhya-pradesh-and-chhattisgarh/activities";
		String travel_places27 = "http://www.lonelyplanet.com/india/madhya-pradesh-and-chhattisgarh/things-to-do";
		String travel_places28 = "http://www.lonelyplanet.com/india/madhya-pradesh-and-chhattisgarh/restaurants";
		String travel_places29 = "http://www.lonelyplanet.com/india/madhya-pradesh-and-chhattisgarh/shopping";

		String category = null;
		String subcategory = null;
		String keywords = null;
		String description = null;
		String content = null;
		String domain = null;
		Document doc = null;
		List<String> links = null;

		List<String> sportsUrls = new ArrayList<String>();

		sportsUrls.add(sub_sports1);
		sportsUrls.add(sub_sports2);
		sportsUrls.add(sub_sports3);
		sportsUrls.add(sub_sports4);
		sportsUrls.add(sub_sports5);
		sportsUrls.add(sub_sports6);
		sportsUrls.add(sub_sports7);
		sportsUrls.add(sub_sports8);


		sportsUrls.add(sub_sports9);

		sportsUrls.add(sub_sports10);

		List<String> entertainmentUrls = new ArrayList<String>();

		entertainmentUrls.add(sub_entertainment1);
		entertainmentUrls.add(sub_entertainment2);
		entertainmentUrls.add(sub_entertainment3);
		entertainmentUrls.add(sub_entertainment4);
		entertainmentUrls.add(sub_entertainment5);
		entertainmentUrls.add(sub_entertainment6);

		List<String> businessUrls = new ArrayList<String>();

		businessUrls.add(sub_business1);
		businessUrls.add(sub_business2);
		businessUrls.add(sub_business3);
		businessUrls.add(sub_business4);  
		businessUrls.add(sub_business5);

		/*
		 * List<String> travelUrls = new ArrayList<String>();
		 * travelUrls.add(travel_wildlife); travelUrls.add(travel_topeats);
		 * travelUrls.add(travel_bazaars); travelUrls.add(travel_hotels);
		 * travelUrls.add(travel_temples); travelUrls.add(travel_treks);
		 * travelUrls.add(travel_best_places); travelUrls.add(travel_forts);
		 * travelUrls.add(travel_places); travelUrls.add(travel_places3);
		 * 
		 * 
		 * 
		 * travelUrls.add(travel_places9); travelUrls.add(travel_places10);
		 * travelUrls.add(travel_places11); travelUrls.add(travel_places12);
		 * travelUrls.add(travel_places13); travelUrls.add(travel_places14);
		 * travelUrls.add(travel_places15); travelUrls.add(travel_places16);
		 * travelUrls.add(travel_places17); travelUrls.add(travel_places18);
		 * travelUrls.add(travel_places19); travelUrls.add(travel_places20);
		 * travelUrls.add(travel_places21); travelUrls.add(travel_places22);
		 * travelUrls.add(travel_places23); travelUrls.add(travel_places24);
		 * travelUrls.add(travel_places25); travelUrls.add(travel_places26);
		 * travelUrls.add(travel_places27); travelUrls.add(travel_places28);
		 * travelUrls.add(travel_places29);
		 */

		List<String> techUrls = new ArrayList<String>();

		techUrls.add(sub_tech1);
		techUrls.add(sub_tech2);
		techUrls.add(sub_tech3);
		techUrls.add(sub_tech4);
		techUrls.add(sub_tech5);

		List<String> lifestyleUrls = new ArrayList<String>();

		lifestyleUrls.add(sub_life_style1);
		lifestyleUrls.add(sub_life_style2);
		lifestyleUrls.add(sub_life_style3);
		lifestyleUrls.add(sub_life_style4);
		lifestyleUrls.add(sub_life_style5);

		for (int i = 0; i < sportsUrls.size(); i++) {
			domain = sportsUrls.get(i);
			try{
			LoadCategoryData.LoadSportsData(category, subcategory, keywords,
					content, domain, doc, links);
			}
			catch(Exception e){
				e.printStackTrace();
			    continue;
			}
			
			
			}

		
		for (int j = 0; j < entertainmentUrls.size(); j++) {
			domain = entertainmentUrls.get(j);
			try{
			LoadCategoryData.LoadEntertainmentData(category, subcategory,
					keywords, content, domain, doc, links);
			}
			catch(Exception e){
			   e.printStackTrace();
			   continue;
			}
		}
		
			
			
			for (int k = 0; k < businessUrls.size(); k++) {
			domain = businessUrls.get(k);
			try{
			
			LoadCategoryData.LoadBusinessData(category, subcategory, keywords,
					content, domain, doc, links);
			}
			catch(Exception e){
				e.printStackTrace();
				continue;
			}
			 
			
			
		}


		
		for (int l = 0; l < techUrls.size(); l++) {
			domain = techUrls.get(l);
			System.out.println("Domain:" + domain);
			
			try{
			LoadCategoryData.LoadTechData(category, subcategory, keywords,
					content, domain, doc, links);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				continue;
			}
			
			
			
			}




		for (int m = 0; m < lifestyleUrls.size(); m++) {
			domain = lifestyleUrls.get(m);
			try{
			LoadCategoryData.LoadLifestyleData(category, subcategory, keywords,
	         content, domain, doc, links);
			}
			catch(Exception e)
			{
			  e.printStackTrace();	
		  		continue;
			}
		
			
			}


		
		/*
		 * domain = nation_news;
		 * LoadCategoryData.LoadCurrentAffairsData(category, subcategory,
		 * keywords, content, domain, doc, links);
		 */

		/*
		 * for (int k = 0; k < travelUrls.size(); k++) { domain =
		 * travelUrls.get(k); LoadCategoryData.LoadTravelData(category,
		 * subcategory, keywords, content, domain, doc, links);
		 * 
		 * }
		 */

	}

}
