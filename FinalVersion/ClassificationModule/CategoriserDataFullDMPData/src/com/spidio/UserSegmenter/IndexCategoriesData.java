package com.spidio.UserSegmenter;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptService.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

public class IndexCategoriesData {

	public static void main(String args[]) {
		IndexCategoriesData p = new IndexCategoriesData();
		// p.postElasticSearch();
	}

	public static Map<String, Object> putJsonDocument(String category,
			String subcategory, String content) {

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

	// Use context of time to distinguish mixed categories

	public static void postElasticSearch(Map<String, Object> categoryDocument) {

		Client client = new TransportClient()
				.addTransportAddress(new InetSocketTransportAddress(
						"10.12.2.61", 9300));

		String value = (String) categoryDocument.get("content");

		// SearchHit[] results = searchDocumentContent(client,
		// "dmpcategoriesdata" ,"core2" ,"content" ,value);
		// if(results == null || results.length == 0)
		client.prepareIndex("dmpcategoriesdatabasev3", "categoriesdata")
				.setSource(categoryDocument).execute().actionGet();

	}

	// If content is already there and classified, it is not further analysed

	public static SearchHit[] searchEntireUserDataSorted(Client client,
			String index, String type, String field, String value) {

		SearchResponse response = client
				.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(QueryBuilders.matchAllQuery())
				.setFrom(0)
				.setSize(60)
				.setExplain(true)
				.addSort("postDate",
						org.elasticsearch.search.sort.SortOrder.DESC).execute()
				.actionGet();

		SearchHit[] results = response.getHits().getHits();
		System.out.println("Current results: " + results.length);

		return results;
	}

	// Will scan entire User profile database and return records one by one

	public static SearchHit[] searchEntireUserDataDateWise(Client client,
			String index, String type) {

		// Sort in order of ascending time, old data get categorized first

		SearchResponse response = client
				.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setPostFilter(
						FilterBuilders.rangeFilter("request_time")
								.from("now-20h").to("now"))
				.setQuery(QueryBuilders.matchAllQuery())
				.addSort(
						SortBuilders.fieldSort("request_time").order(
								SortOrder.ASC)).setFrom(0).setSize(10)
				.setExplain(true).execute().actionGet();

		SearchHit[] results = response.getHits().getHits();
		System.out.println("Current results: " + results.length);

		return results;
	}

	public static SearchHit[] searchEntireUserData(Client client, String index,
			String type) {

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();

		SearchHit[] results = response.getHits().getHits();
		System.out.println("Current results: " + results.length);

		return results;
	}

	public static SearchHit[] searchDocumentContent(Client client,
			String index, String type, String field, String value) {

		SearchResponse response = client
				.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(QueryBuilders.matchQuery(field, value))
				.addSort(
						SortBuilders.fieldSort("postDate")
								.order(SortOrder.DESC)).setSize(10).execute()
				.actionGet();

		SearchHit[] results = response.getHits().getHits();
		System.out.println("Current results: " + results.length);

		return results;
	}

	public static IndexResponse doIndex(Client client, String index,
			String type, String id, Map<String, Object> data) {

		return client.prepareIndex(index, type, id).setSource(data).execute()
				.actionGet();
	}

	public static SearchHit[] searchDocumentsimilarContent(Client client,
			String index, String type, String field, String value) {

		QueryBuilder qb = QueryBuilders.moreLikeThisQuery("content")
				.likeText(value).minTermFreq(1).maxQueryTerms(12);

		SearchResponse response = client
				.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(qb)
				.addSort(
						SortBuilders.fieldSort("postDate")
								.order(SortOrder.DESC)).setSize(10).execute()
				.actionGet();

		SearchHit[] results = response.getHits().getHits();
		System.out.println("Current results: " + results.length);

		return results;
	}

	public static void updateDocument(Client client, String index, String type,

	String id, String field, String newValue) {

		System.out.println("Categorisation Data Updated" + "\n");

		Map<String, Object> updateObject = new HashMap<String, Object>();

		updateObject.put(field, newValue);

		client.prepareUpdate(index, type, id)
				.setScript("ctx._source." + field + "=" + field,
						ScriptType.INLINE).setScriptParams(updateObject)
				.execute().actionGet();

	}

	public static String removeStopWords(String textFile) throws Exception {
		CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
		TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_48,
				new StringReader(textFile.trim()));

		tokenStream = new StopFilter(Version.LUCENE_48, tokenStream, stopWords);
		StringBuilder sb = new StringBuilder();
		CharTermAttribute charTermAttribute = tokenStream
				.addAttribute(CharTermAttribute.class);
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			String term = charTermAttribute.toString();
			sb.append(term + " ");
		}
		return sb.toString();
	}

}