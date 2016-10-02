package com.spidio.DataLogix.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import org.elasticsearch.index.search.MultiMatchQuery.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;

import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.extractors.ArticleExtractor;
import com.kohlschutter.boilerpipe.sax.BoilerpipeSAXInput;
import com.kohlschutter.boilerpipe.sax.HTMLFetcher;
import com.spidio.UserSegmenter.IndexCategoriesData;
import com.spidio.UserSegmenter.ProcessRefurl;
import com.sree.textbytes.jtopia.Configuration;
import com.sree.textbytes.jtopia.TermDocument;
import com.sree.textbytes.jtopia.TermsExtractor;

public class getCategory {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Client client = new TransportClient()
				.addTransportAddress(new InetSocketTransportAddress(
						"10.12.2.61", 9300));

		String keywords = null;
		String description = null;
		String[] searchkeyword = null;
		String[] searchkeyword1 = null;

		// String [] finalsearchkeyword = null;
		String finalsearchkeyword;
		SearchHit[] matchingsegmentrecords = null;
		String category = null;
		String subcategory = null;
		String finalCategory = null;
		String refurl = null;
		String title = null;
		String audienceSegment = null;
		String[] parts = null;
		String[] parts1 = null;
		String keywordcheck = null;
		TreeMap<String, Integer> categoryCount = new TreeMap<String, Integer>();
		TreeMap<String, Integer> keywordcategoryCount = new TreeMap<String, Integer>();
		TreeMap<String, Integer> aggregatecategoryCount = new TreeMap<String, Integer>();
		TreeMap<String, Integer> subcategoryMap = new TreeMap<String, Integer>();

		Integer count = 0;
		Integer flag = 0;
		Integer count1 = 0;
		String id;
		Integer count2 = 0;
		Integer count3 = 0;
		Integer categorycount = 0;

		SearchResponse response1 = client.prepareSearch("userprofilestore")
				.setTypes("core2").setSearchType(SearchType.QUERY_THEN_FETCH)
				.setScroll(new TimeValue(600000))
				.setQuery(QueryBuilders.matchAllQuery()).setSize(10).execute()
				.actionGet();

		// Scroll until no hits are returned

		while (true) {

			for (SearchHit hit : response1.getHits().getHits()) {

				try {

					// System.out.println("------------------------------");
					Map<String, Object> result = hit.getSource();
					refurl = (String) result.get("referrer");
					title = (String) result.get("page_title");
					id = (String) hit.getId();
					// if(refurl.contains("voindia")==true)
					// refurl="http://www.voindia.com/sachin-tendulkar-and-shane-warne-on-a-mission-to-sell-cricket-to-new-yorkers/";

					category = null;
					// System.out.println("URL:" + refurl + "\n");

					audienceSegment = (String) result.get("audience_segment");
					if (audienceSegment != null)
						audienceSegment = audienceSegment.trim();

					if (audienceSegment == null
							|| audienceSegment.isEmpty() == true) {

						// System.out.println("in BLOCK1");

						if (title.contains("Ozone") == true)
							category = "Airline_Travel";

						if (title.contains("KFC") == true)
							category = "Food&Beverages";

						if (title.contains("McDonald") == true)
							category = "Food&Beverages";

						if (title.contains("Barista") == true)
							category = "Food&Beverages";

						if (title.contains("Costa") == true)
							category = "Food&Beverages";

						if (title.contains("HALDIRAM") == true)
							category = "Food&Beverages";

						if (title.contains("Mumbai_T1") == true)
							category = "Airline_Travel";

						if (title.contains("Mumbai_T2") == true)
							category = "Airline_Travel";

						// System.out.println("Category:" + category);

						if (category != null && !category.isEmpty())
							IndexCategoriesData.updateDocument(client,
									"userprofilestore", "core2", id,
									"audience_segment", category);

						audienceSegment = category;

					}

					if (audienceSegment == null
							|| audienceSegment.isEmpty() == true) {

						// System.out.println("in BLOCK2");

						if (refurl.contains("entertainm"))
							category = "entertainment";

						if (refurl.contains("technolo"))
							category = "technology";

						// if (refurl.contains("news"))
						// category = "news";

						if (refurl.contains("business"))
							category = "business";

						if (refurl.contains("lifestyle"))
							category = "lifestyle";

						if (refurl.contains("sport"))
							category = "sports";

						if (refurl.contains("travel"))
							category = "travel";

						if (refurl.contains("astronomy"))
							category = "astronomy";

						if (refurl.contains("religion"))
							category = "religion";

						if (refurl.contains("education"))
							category = "education";

						// System.out.println("Category:" + category);

						if (category != null && !category.isEmpty())
							IndexCategoriesData.updateDocument(client,
									"userprofilestore", "core2", id,
									"audience_segment", category);

						audienceSegment = category;

					}

					

					if (audienceSegment == null
							|| audienceSegment.isEmpty() == true) {

						// System.out.println("in BLOCK3");

						BufferedReader br = new BufferedReader(new FileReader(
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

						// System.out.println("Category:" + category);

						if (category != null && !category.isEmpty())
							IndexCategoriesData.updateDocument(client,
									"userprofilestore", "core2", id,
									"audience_segment", category);

						audienceSegment = category;

					}
					
					
					if (audienceSegment == null
							|| audienceSegment.isEmpty() == true) {

						// System.out.println("in BLOCK4");

						try {
							category = ProcessRefurl.getCategory(refurl);
						} catch (Exception e) {
							e.printStackTrace();

						}

						if (category != null && !category.isEmpty())
							IndexCategoriesData.updateDocument(client,
									"userprofilestore", "core2", id,
									"audience_segment", category);

						audienceSegment = category;

					}
					
					
					
					
					

					if (audienceSegment == null
							|| audienceSegment.isEmpty() == true) {
						try {
							keywords = ProcessRefurl.getKeywords(refurl);

							description = ProcessRefurl.getDescription(refurl);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}

						// System.out.println("Description:" + description);
						if (keywords != null && !keywords.isEmpty()
								&& keywords != "-"
								&& refurl.contains("voindia") == false) {

							searchkeyword = keywords.split(",");

							for (int j = 0; j < searchkeyword.length; j++) {

								finalsearchkeyword = searchkeyword[j].trim();

								// System.out.println("Keyword:" +
								// finalsearchkeyword + "\n");

								try {
									if (finalsearchkeyword != null
											&& !finalsearchkeyword.isEmpty())
										matchingsegmentrecords = IndexCategoriesData
												.searchDocumentContent(client,
														"dmpcategoriesdata",
														"core2", "content",
														finalsearchkeyword);
								} catch (Exception e) {

									e.printStackTrace();

								}
								// System.out.println("Size:" +
								// matchingsegmentrecords.length);

								if (matchingsegmentrecords != null) {
									for (SearchHit hit1 : matchingsegmentrecords) {
										Map<String, Object> result1 = hit1
												.getSource();
										// System.out.println(result1);
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

											if (keywordcategoryCount
													.containsKey(category) == false)
												keywordcategoryCount.put(
														category, 1);
											else {
												count2 = keywordcategoryCount
														.get(category);
												keywordcategoryCount.put(
														category, count2 + 1);
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
											// System.out.println("Category:" +
											// category + "\n");

											// System.out.println("Sub Category:"
											// +
											// subcategory + "\n");

											// Have to add update elasticsearch
											// API

											// if(category != null)
											// break;
										}
									}

									int maxValueInMap2 = (Collections
											.max(keywordcategoryCount.values())); // This
																					// will
																					// return
																					// max
																					// value
																					// in
																					// the
																					// Hashmap
									for (Entry<String, Integer> entry1 : keywordcategoryCount
											.entrySet()) { // Itrate through
															// hashmap
										if (entry1.getValue() == maxValueInMap2) {
											category = entry1.getKey(); // Print
																		// the
																		// key
											// with max

											// value
										}
									}

									keywordcategoryCount.clear();

									String keywordCategory = category;

									if (aggregatecategoryCount
											.containsKey(keywordCategory) == false) {
										aggregatecategoryCount.put(category, 1);

									} else {

										count2 = aggregatecategoryCount
												.get(category);

										aggregatecategoryCount.put(category,
												count2 + 1);
									}

								}

							}

							// System.out.println("Category Map");

							for (Map.Entry<String, Integer> entry : categoryCount
									.entrySet()) {
								System.out.println(entry.getKey() + " : "
										+ entry.getValue());
							}

							// System.out.println("Frequent Category Map");
							for (Map.Entry<String, Integer> entry : aggregatecategoryCount
									.entrySet()) {
								// System.out.println(entry.getKey() + " : " +
								// entry.getValue());
							}

							int maxValueInMap = (Collections.max(categoryCount
									.values())); // This
													// will
													// return
													// max
													// value
													// in
													// the
													// Hashmap
							for (Entry<String, Integer> entry : categoryCount
									.entrySet()) { // Itrate
													// through
													// hashmap
								if (entry.getValue() == maxValueInMap) {
									category = entry.getKey(); // Print the key
									categoryCount.put(category, 0); // with max
									break;
									// value
								}
							}

							String topCategory = category;

							int maxValueInMap1 = (Collections.max(categoryCount
									.values())); // This
													// will
													// return
													// max
													// value
													// in
													// the
													// Hashmap
							for (Entry<String, Integer> entry : categoryCount
									.entrySet()) { // Itrate
													// through
													// hashmap
								if (entry.getValue() == maxValueInMap1) {
									category = entry.getKey(); // Print the key
									categoryCount.put(category, 0); // with max
									break;
									// value
								}
							}

							String penultimateCategory = category;

							int maxValueInMap3 = (Collections
									.max(aggregatecategoryCount.values())); // This
																			// will
																			// return
																			// max
																			// value
																			// in
																			// the
																			// Hashmap
							for (Entry<String, Integer> entry : aggregatecategoryCount
									.entrySet()) { // Itrate through hashmap
								if (entry.getValue() == maxValueInMap3) {
									category = entry.getKey(); // Print the key
									aggregatecategoryCount.put(category, 0); // with
																				// max
									break;
									// value
								}
							}

							String mostfrequentCategory = category;

							if (mostfrequentCategory == topCategory
									|| mostfrequentCategory == penultimateCategory)
								category = mostfrequentCategory;

							// System.out.println("Most Frequent Category:" +
							// mostfrequentCategory);

							// System.out.println("Final Category:" +
							// finalCategory + "\n");

							// subcategory = subcategoryMap.firstKey();
							// parts = subcategory.split(":");
							// subcategory = parts[1];

							if (category != null && !category.isEmpty())
								IndexCategoriesData.updateDocument(client,
										"userprofilestore", "core2", id,
										"audience_segment", category);

							audienceSegment = category;

							categoryCount.clear();
							subcategoryMap.clear();
							keywordcategoryCount.clear();
							aggregatecategoryCount.clear();

						}

					}

					if (audienceSegment == null
							|| audienceSegment.isEmpty() == true) {

						String keyword = null;

						URL url = new URL(refurl);

						final InputSource is = HTMLFetcher.fetch(url)
								.toInputSource();

						final BoilerpipeSAXInput in = new BoilerpipeSAXInput(is);
						final TextDocument doc = in.getTextDocument();

						String text = ArticleExtractor.INSTANCE.getText(doc);

						Configuration.setTaggerType("openNLP");
						// for Stanford POS tagger
						// Configuration.setTaggerType("stanford");
						Configuration.setSingleStrength(3);
						Configuration.setNoLimitStrength(2);
						// if tagger type is "openNLP" then give the openNLP POS
						// tagger path
						Configuration
								.setModelFileLocation("/home/ubuntu/en-pos-maxent.bin");

						TermsExtractor termExtractor = new TermsExtractor();

						TermDocument topiaDoc = new TermDocument();

						topiaDoc = termExtractor.extractTerms(text);

						// / System.out.println("Document:" + topiaDoc);

						Map<String, Integer> keywordFrequency = topiaDoc
								.getExtractedTerms();

						Map<String, ArrayList<Integer>> filteredWords = topiaDoc
								.getFinalFilteredTerms();
						Map<String, Integer> filteredWords1 = new HashMap<String, Integer>();

						for (Entry<String, ArrayList<Integer>> entry : filteredWords
								.entrySet()) { // Itrate through hashmap
							filteredWords1.put(entry.getKey(), entry.getValue()
									.get(0));
						}

						// System.out.println(keywordFrequency);

						// System.out.println(filteredWords);

						int maxValueInMap = Collections.max(filteredWords1
								.values()); // This will return
						// max value in the
						// Hashmap
						for (Entry<String, Integer> entry : filteredWords1
								.entrySet()) { // Itrate through hashmap
							if (entry.getValue() == maxValueInMap) {
								keyword = entry.getKey(); // Print the key

							}

						}

						// System.out.println("Keyword:" + keyword + "\n");

						try {
							if (keyword != null && !keyword.isEmpty()) {
								matchingsegmentrecords = IndexCategoriesData
										.searchDocumentContent(client,
												"dmpcategoriesdata", "core2",
												"content", keyword);
							}
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
						// System.out.println("Size:" +
						// matchingsegmentrecords.length);

						if (matchingsegmentrecords != null) {
							for (SearchHit hit1 : matchingsegmentrecords) {
								Map<String, Object> result1 = hit1.getSource();
								// System.out.println(result1);
								category = (String) result1.get("category");

								if (category != null && !category.isEmpty()) {

									if (categoryCount.containsKey(category) == false) {
										categoryCount.put(category, 1);

									} else {

										count = categoryCount.get(category);

										categoryCount.put(category, count + 1);

									}
								}
							}
						}

						maxValueInMap = (Collections
								.max(categoryCount.values())); // This

						for (Entry<String, Integer> entry : categoryCount
								.entrySet()) { // Itrate

							if (entry.getValue() == maxValueInMap) {
								category = entry.getKey(); // Print the key
								// System.out.println(category);
								// value
							}
						}

						if (category != null && !category.isEmpty())
							IndexCategoriesData.updateDocument(client,
									"userprofilestore", "core2", id,
									"audience_segment", category);

						audienceSegment = category;

						categoryCount.clear();

					}

				} catch (Exception e) {

					e.printStackTrace();
					continue;

				}

			}

			response1 = client.prepareSearchScroll(response1.getScrollId())
					.setScroll(new TimeValue(600000)).execute().actionGet();
			// Break condition: No hits are returned

			count3++;
			// System.out.println("Number of scrolls:"+count3);

			if (response1.getHits().getHits().length == 0) {
				break;
			}

			// System.out.println("ScrollId:" + response1.getScrollId());

		}

	}

}
