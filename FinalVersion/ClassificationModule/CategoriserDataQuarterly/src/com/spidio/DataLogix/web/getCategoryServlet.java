package com.spidio.DataLogix.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.spidio.UserSegmenter.IndexCategoriesData;
import com.spidio.UserSegmenter.ProcessRefurl;

public class getCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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
		String title = null;
		String audienceSegment = null;
		String[] parts = null;
		String[] parts1 = null;
		String keywordcheck = null;
		TreeMap<String, Integer> categoryCount = new TreeMap<String, Integer>();
		TreeMap<String, Integer> subcategoryMap = new TreeMap<String, Integer>();
		Integer count = 0;
		Integer flag = 0;
		Integer count1 = 0;
		String id;
		Integer count2 = 0;
		Integer categorycount = 0;

		SearchResponse response1 = client.prepareSearch("userprofilestore")
				.setTypes("core2").setSearchType(SearchType.QUERY_THEN_FETCH)
				.setScroll(new TimeValue(60000))
				.setQuery(QueryBuilders.matchAllQuery()).setSize(100).execute()
				.actionGet();

		// Scroll until no hits are returned

		while (true) {

			for (SearchHit hit : response1.getHits().getHits()) {

				try {

					System.out.println("------------------------------");
					Map<String, Object> result = hit.getSource();
					refurl = (String) result.get("referrer");
					title = (String) result.get("page_title");
					id = (String) hit.getId();
					System.out.println(refurl + "\n");

					if (refurl.contains("voindia") == false
							&& refurl.contains("cornetto") == false
							&& refurl.contains("testurl") == false) {
						audienceSegment = (String) result
								.get("audience_segment");
						if (audienceSegment != null)
							audienceSegment = audienceSegment.trim();

						if (audienceSegment == null || audienceSegment == "") {
							if (title.contains("Ozone") != false)
								category = "Airline_Travel";

							if (title.contains("KFC") != false)
								category = "Food&Beverages";

							if (title.contains("McDonald") != false)
								category = "Food&Beverages";

							if (title.contains("Barista") != false)
								category = "Food&Beverages";

							if (title.contains("Costa") != false)
								category = "Food&Beverages";

							if (title.contains("Mumbai_T1") != false)
								category = "Airline_Travel";

							if (title.contains("Mumbai_T2") != false)
								category = "Airline_Travel";

							if (category != null && category != "")
								IndexCategoriesData.updateDocument(client,
										"userprofilestore", "core2", id,
										"audience_segment", category);

							System.out.println("Category:" + category);

						}

						if (audienceSegment == null || audienceSegment == "") {

							BufferedReader br = new BufferedReader(
									new FileReader(
											"/home/ubuntu/classifiedmasterdatabase.txt"));
							String line;
							while ((line = br.readLine()) != null) {

								if (line != null || !line.isEmpty()) {
									if (!(line.trim()).equals(":")) {
										parts1 = line.split(":");
										if (parts1.length == 2)
											keywordcheck = parts1[0].trim();
									}
								}

								if (keywordcheck != null) {
									if (refurl.toLowerCase().contains(
											keywordcheck.toLowerCase()) == true) {
										if (parts1.length == 2)
											category = parts1[1].trim();
									}
								}

							}

							System.out.println("Category:" + category);

							if (category != null && category != "")
								IndexCategoriesData.updateDocument(client,
										"userprofilestore", "core2", id,
										"audience_segment", category);

						}
						/*
						 * refurl =
						 * "http://www.voindia.com/india-call-up-harbhajan-as-cover-for-injured-ashwin"
						 * ; URL url; url = new URL(refurl); final InputSource
						 * is = HTMLFetcher.fetch(url).toInputSource();
						 * 
						 * final BoilerpipeSAXInput in = new
						 * BoilerpipeSAXInput(is); final TextDocument doc =
						 * in.getTextDocument();
						 * 
						 * TermDocument topiaDoc = new TermDocument();
						 * TermsExtractor termExtractor = new TermsExtractor();
						 * String text = ArticleExtractor.INSTANCE.getText(doc);
						 * topiaDoc = termExtractor.extractTerms(text);
						 */// Document doc;

						// need http protocol
						// doc =
						// Jsoup.connect("http://www.voindia.com/india-call-up-harbhajan-as-cover-for-injured-ashwin").get();
						// get page title

						// doc =
						// Jsoup.connect("http://www.voindia.com/amaal-mallik-on-the-changing-trends-of-music-industry/").get();

						// doc =
						// doc =
						// Jsoup.connect("http://timesofindia.indiatimes.com/entertainment/english/hollywood/news/Hellboy-3-probably-will-never-happen/articleshow/51304881.cms").get();
						// System.out.println(doc.body());

						// topiaDoc = termExtractor.extractTerms(doc.text());

						/*
						 * System.out.println("Extracted terms : "+topiaDoc.
						 * getExtractedTerms());
						 * System.out.println("Final Filtered Terms : "
						 * +topiaDoc.getFinalFilteredTerms());
						 */

						// You have the choice between different Extractors

						// System.out.println(DefaultExtractor.INSTANCE.getText(doc));
						// System.out.println(ArticleExtractor.INSTANCE.getText(doc));

						// refurl =
						// "http://timesofindia.indiatimes.com/entertainment/hindi/bollywood/Kareena-Kapoor-defends-Aamir-Khans-comments-on-intolerance/photostory/51341109.cms";
						// refurl =
						// "http://timesofindia.indiatimes.com/sports/tennis/top-stories/Real-Madrid-backs-Nadal-after-doping-accusations-from-France/articleshow/51378934.cms";
						// refurl =
						// "http://timesofindia.indiatimes.com/sports/tennis/top-stories/McEnroe-doubts-Sharapova-was-unaware-of-meldonium-ban/articleshow/51379140.cms";

						// refurl =
						// "http://timesofindia.indiatimes.com/entertainment/hindi/bollywood/news/Deepika-Padukone-goes-house-hunting-with-Ruby-Rose/articleshow/51373194.cms";
						try {
							keywords = ProcessRefurl.getKeywords(refurl);

							description = ProcessRefurl.getDescription(refurl);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("Description:" + description);
						if (keywords != null || keywords != ""
								|| keywords != "-"
								|| refurl.contains("voindia") == false) {

							searchkeyword = keywords.split(",");
						}

						if (audienceSegment == null || audienceSegment == "") {
							for (int j = 0; j < searchkeyword.length; j++) {
								/*
								 * if(count2 == 0)
								 * 
								 * { finalsearchkeyword = searchkeyword[j];
								 * System.out.println(finalsearchkeyword);
								 * searchkeyword1 =
								 * finalsearchkeyword.split("/");
								 * finalsearchkeyword = searchkeyword1[3];
								 * 
								 * }
								 */
								finalsearchkeyword = searchkeyword[j].trim();
								// finalsearchkeyword = refurl;
								System.out.println(finalsearchkeyword + "\n");
								if (finalsearchkeyword != null
										&& !finalsearchkeyword.isEmpty())
									matchingsegmentrecords = IndexCategoriesData
											.searchDocumentContent(client,
													"dmpcategoriesdata",
													"core2", "content",
													finalsearchkeyword);
								System.out.println("Size:"
										+ matchingsegmentrecords.length);

								if (matchingsegmentrecords != null) {
									for (SearchHit hit1 : matchingsegmentrecords) {
										Map<String, Object> result1 = hit1
												.getSource();
										System.out.println(result1);
										category = (String) result1
												.get("category");
										subcategory = (String) result1
												.get("subcategory");

										if (category != null
												&& !category.isEmpty()) {
											if (categoryCount
													.containsKey(category) == false) {
												categoryCount.put(category, 1);

											} else {

												count = categoryCount
														.get(category);
												categoryCount.put(category,
														count + 1);

											}

											if (subcategoryMap
													.containsKey(category + ":"
															+ subcategory) == false)
												subcategoryMap.put(category
														+ ":" + subcategory, 1);
											else {
												count1 = subcategoryMap
														.get(category + ":"
																+ subcategory);
												subcategoryMap.put(category
														+ ":" + subcategory,
														count1 + 1);
											}

											// subcategory = (String)
											// result1.get("subcategory");
											System.out.println("Category:"
													+ category + "\n");
											// System.out.println("Sub Category:"
											// +
											// subcategory + "\n");

											// Have to add update elasticsearch
											// API

											// if(category != null)
											// break;
										}
									}

								}

							}

							int maxValueInMap = (Collections.max(categoryCount
									.values())); // This will return
													// max value in the
													// Hashmap
							for (Entry<String, Integer> entry : categoryCount
									.entrySet()) { // Itrate through hashmap
								if (entry.getValue() == maxValueInMap) {
									category = entry.getKey(); // Print the key
																// with max
																// value
								}
							}

							System.out.println("Final Category:" + category
									+ "\n");

							// subcategory = subcategoryMap.firstKey();
							// parts = subcategory.split(":");
							// subcategory = parts[1];
							IndexCategoriesData.updateDocument(client,
									"userprofilestore", "core2", id,
									"audience_segment", category);
						}

					}

					count++;
				} catch (Exception e) {

					e.printStackTrace();
					continue;

				}

			}

			response1 = client.prepareSearchScroll(response1.getScrollId())
					.setScroll(new TimeValue(60000)).execute().actionGet();
			// Break condition: No hits are returned
			if (response1.getHits().getHits().length == 0) {
				break;
			}

		}

		System.out.println("Count:" + count);

	}
}
