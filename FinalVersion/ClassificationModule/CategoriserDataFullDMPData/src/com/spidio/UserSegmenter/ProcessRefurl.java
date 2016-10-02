package com.spidio.UserSegmenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProcessRefurl {

	public static String getKeywords(String url) throws IOException {
		final ArrayList<String> result = new ArrayList<String>();

		Document doc = null;
		String keywords = null;
		String description = null;
		System.out.println("refurl:" + url);

		doc = Jsoup.connect(url).timeout(20 * 1000).get(); // Configure
															// socket
															// read

		// timeout
		// to
		// prevent
		// timeouts
		// on
		// load
		// intensive
		// website
		
		if (doc != null) {

			if (url.contains("northeasttoday"))
				keywords = doc.select("meta[name=description]").first()
						.attr("content");
			else
				keywords = doc.select("meta[name=keywords]").first()
						.attr("content");
			System.out.println("Meta keyword : " + keywords);
			description = doc.select("meta[name=description]").get(0)
					.attr("content");
			System.out.println("Meta description : " + description);
		}

		return keywords;
	}

	public static String getDescription(String url) throws IOException {

		Document doc = null;
		String keywords = null;
		String description = null;

		doc = Jsoup.connect(url).timeout(20 * 1000).get(); // Configure

		// read
		// timeout
		// to
		// prevent
		// timeouts
		// on
		// load
		// intensive
		// websites

		if (doc != null) {

			description = doc.select("meta[name=description]").get(0)
					.attr("content");
			System.out.println("Meta description : " + description);
		}

		return description;
	}

	public static String getCategory(String url) throws Exception {
		final ArrayList<String> result = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(20 * 1000).get(); // Configure
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
		String category = null;
		String text1 = null;
		String text2 = null;
		String text3 = null;
		String description = null;

		if (doc != null) {

			if (url.contains("northeasttoday") == true) {
				text1 = doc.select("meta[property=article:tag]").first()
						.attr("content");
				text2 = doc.select("meta[property=article:tag]").get(1)
						.attr("content");
				text3 = doc.select("meta[property=article:tag]").get(2)
						.attr("content");

				if (text1.contains("culture") || text2.contains("culture")
						|| text3.contains("culture"))
					category = "culture";

				if (text1.contains("Life") || text2.contains("Life")
						|| text3.contains("Life"))
					category = "Life style";

				if (text1.contains("State") || text2.contains("State")
						|| text3.contains("State"))
					category = "states";

				if (text1.contains("Opinion") || text2.contains("Opinion")
						|| text3.contains("Opinion"))
					category = "opinions";

				if (text1.contains("Politic") || text2.contains("Politic")
						|| text3.contains("Politic"))
					category = "Politics";

				System.out.println("Category:" + category);

			}

			if (url.contains("voindia") == true) {
				// socket
				// read
				// timeout
				// to
				// prevent
				// timeouts
				// on load
				// intensity // websites
				Elements article = doc.select("article");
				for (Element link : article) {
					String articleText = link.attr("class");
					System.out.println(articleText + "\n");

					articleText = articleText.toLowerCase();

					if (articleText.contains("enterta"))
						category = "entertainment";

					if (articleText.contains("sport"))
						category = "sports";

					if (articleText.contains("business"))
						category = "business";

					if (articleText.contains("life"))
						category = "lifestyle";

					if (articleText.contains("tech"))
						category = "technology";

					if (articleText.contains("health"))
						category = "lifestyle";

					if (articleText.contains("nation-news"))
						category = "nation-news";

					if (articleText.contains("world-news"))
						category = "world-news";

					if (articleText.contains("news"))
						category = "news";

					if (articleText.contains("auto"))
						category = "auto";

					if (articleText.contains("funny"))
						category = "funny";

				}

				System.out.println("Category:" + category);

			}

		}

		return category;
	}

}
