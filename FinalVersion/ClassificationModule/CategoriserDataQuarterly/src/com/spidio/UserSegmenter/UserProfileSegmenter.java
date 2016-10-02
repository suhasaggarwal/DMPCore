package com.spidio.UserSegmenter;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.xml.sax.InputSource;

public class UserProfileSegmenter {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Client client = new TransportClient()
				.addTransportAddress(new InetSocketTransportAddress(
						"localhost", 9300));

		String keywords = null;
		String description = null;
		String[] searchkeyword = null;
		String[] searchkeyword1 = null;

		// String [] finalsearchkeyword = null;
		String finalsearchkeyword;
		SearchHit[] matchingsegmentrecords = null;
		String category = null;
		String subcategory = null;
		String refurl = null;
		String audienceSegment = null;
		String[] parts = null;
		TreeMap<String, Integer> categoryCount = new TreeMap<String, Integer>();
		TreeMap<String, Integer> subcategoryMap = new TreeMap<String, Integer>();
		Integer count = 0;
		Integer flag = 0;
		Integer count1 = 0;
		String id;
		Integer count2 = 0;

	

		// refurl =
		// "http://timesofindia.indiatimes.com/entertainment/hindi/bollywood/Kareena-Kapoor-defends-Aamir-Khans-comments-on-intolerance/photostory/51341109.cms";
		// refurl =
		// "http://timesofindia.indiatimes.com/sports/tennis/top-stories/Real-Madrid-backs-Nadal-after-doping-accusations-from-France/articleshow/51378934.cms";
		// refurl =
		// "http://timesofindia.indiatimes.com/sports/tennis/top-stories/McEnroe-doubts-Sharapova-was-unaware-of-meldonium-ban/articleshow/51379140.cms";

		// refurl =
		// "http://timesofindia.indiatimes.com/entertainment/hindi/bollywood/news/Deepika-Padukone-goes-house-hunting-with-Ruby-Rose/articleshow/51373194.cms";

		// refurl =
		// "http://www.cricbuzz.com/live-cricket-scores/15783/pak-vs-ban-14th-match-super-10-group-2-icc-world-t20-2016";

		// refurl =
		// "http://timesofindia.indiatimes.com/sports/tennis/top-stories/Nadal-topples-Verdasco-to-advance-at-Indian-Wells/articleshow/51420612.cms";

		// refurl =
		// "http://timesofindia.indiatimes.com/sports/hockey/top-stories/Hockey-India-announces-nominations-for-second-annual-awards/articleshow/51343566.cms";

		// refurl =
		// "http://timesofindia.indiatimes.com/sports/badminton/Chen-Long-crashes-out-at-All-England/articleshow/51354882.cms";

		refurl = "http://timesofindia.indiatimes.com/entertainment/hindi/bollywood/news/Director-Nishikant-Kamat-goes-bald-for-Rocky-Handsome/articleshow/51395373.cms";

		refurl = "http://timesofindia.indiatimes.com/entertainment/english/hollywood/news/Chinese-remake-of-Devil-Wears-Prada-in-works/articleshow/51406159.cms";

		//
		refurl = "http://indianexpress.com/article/technology/tech-reviews/samsung-galaxy-s7-edge-review-daily-live-blog-specs-price/";

		refurl = "http://indianexpress.com/article/technology/gadgets/sony-playstation-vr-headset-price-specs-sale-october/";

		// refurl =
		// "http://indianexpress.com/article/technology/social/instagram-news-feed-change-algorithm-latest-post-vs-relevant-post/";

	

		// refurl =
		// "http://indianexpress.com/article/technology/social/instagram-news-feed-change-algorithm-latest-post-vs-relevant-post/";

		// refurl =
		// "http://indianexpress.com/article/technology/tech-reviews/infocus-bingo-50-review-price-specs-features/";

		// refurl =
		// "http://thenortheasttoday.com/bamboos-a-symbol-of-diversity-and-unity-in-northeast/";
		// refurl =
		// "http://thenortheasttoday.com/assam-will-no-longer-be-underdeveloped-after-polls-sonowal/";
		// refurl =
		// "http://thenortheasttoday.com/5-things-that-music-does-to-you/";

		// refurl =
		// "http://thenortheasttoday.com/in-pictures-daniel-syiems-designs-at-fashion-show-in-london/";

		// refurl =
		// "http://thenortheasttoday.com/nongkhnum-festival-2016-will-kick-off-on-march-18/";

		// refurl =
		// "http://thenortheasttoday.com/5-facts-you-must-know-about-andrea-tariang/";

		
		int counter = 0;
		keywords = ProcessRefurl.getKeywords(refurl);
		// keywords= IndexCategoriesData.removeStopWords(keywords);
		System.out.println("Keywords:" + keywords);

		description = ProcessRefurl.getDescription(refurl);
		System.out.println("Description:" + description);
		
		if (keywords != null || keywords != "" || keywords != "-"
				|| refurl.contains("voindia") == false) {

			searchkeyword = keywords.split(",");
		}

		if (audienceSegment != null || audienceSegment != "") {
			
			finalsearchkeyword = keywords;
			System.out.println(finalsearchkeyword + "\n");
			matchingsegmentrecords = IndexCategoriesData
					.searchDocumentsimilarContent(client, "dmpcategoriesdata",
							"core2", "content", finalsearchkeyword);
			// matchingsegmentrecords =
			// IndexCategoriesData.searchDocumentContent(client,"dmpcategoriesdata","core2","content",finalsearchkeyword);

			System.out.println("Size:" + matchingsegmentrecords.length);

			if (matchingsegmentrecords != null) {
				for (SearchHit hit1 : matchingsegmentrecords) {
					Map<String, Object> result1 = hit1.getSource();
					System.out.println(result1);
					category = (String) result1.get("category");
					subcategory = (String) result1.get("subcategory");

					if (category != null && !category.isEmpty()) {
						if (categoryCount.containsKey(category) == false) {
							categoryCount.put(category, 1);

						} else {

							count = categoryCount.get(category);
							categoryCount.put(category, count + 1);

						}

						if (subcategoryMap.containsKey(category + ":"
								+ subcategory) == false)
							subcategoryMap.put(category + ":" + subcategory, 1);
						else {
							count1 = subcategoryMap.get(category + ":"
									+ subcategory);
							subcategoryMap.put(category + ":" + subcategory,
									count1 + 1);
						}

						// subcategory = (String) result1.get("subcategory");
						System.out.println("Category:" + category + "\n");
						// System.out.println("Sub Category:" + subcategory +
						// "\n");

						// Have to add update elasticsearch API

						// if(category != null)
						// break;
					}
				}

			}

		}

		for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

		int maxValueInMap = (Collections.max(categoryCount.values())); // This
																		// will
																		// return
																		// max
																		// value
																		// in
																		// the
																		// Hashmap
		for (Entry<String, Integer> entry : categoryCount.entrySet()) { // Itrate
																		// through
																		// hashmap
			if (entry.getValue() == maxValueInMap) {
				category = entry.getKey(); // Print the key with max value
			}
		}

		System.out.println("Final Category:" + category);

		
	}

}
