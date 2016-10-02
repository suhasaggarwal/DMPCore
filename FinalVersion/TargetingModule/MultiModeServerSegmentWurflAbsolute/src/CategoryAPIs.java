import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;

public class CategoryAPIs {

	
//Returns Category that is latest according to timestamp corresponding to fingerprint	
	
	public static HashSet<String> getLatestCategory(SearchHit[] fingerprintDetails) {

		int counter = 0;
		String audienceSegmentValue = "";
		String latestaudienceSegment = null;
		HashSet<String> audienceSegment = new HashSet<String>();

		for (SearchHit hit : fingerprintDetails) {
			System.out.println("------------------------------");
			Map<String, Object> result = hit.getSource();
			audienceSegmentValue = (String) result.get("audience_segment");
			if (audienceSegmentValue != null && audienceSegmentValue != ""
					&& audienceSegment.contains(audienceSegmentValue) == false) {
				audienceSegment.add(audienceSegmentValue);
				if (audienceSegment.size() == 3)
					break;

			}
		}
	 return audienceSegment;
	}
	
	
	
	//Returns Category that is most frequent according to viewing frequency corresponding to fingerprint
	

	public static String getMostFrequentCategory(SearchHit[] fingerprintDetails) {

		// Returns a Map, returns audience segment from the map
		String audienceSegmentValue = "";
		// String audienceSegment = "";
		String latestaudienceSegment = null;
		HashMap<String, Integer> audienceSegmentFrequent = new HashMap<String, Integer>();
		Set<Frequency> set = new TreeSet<Frequency>();

		Integer counter1 = 0;
		Integer count = 1;

		for (SearchHit hit : fingerprintDetails) {
			System.out.println("------------------------------");
			Map<String, Object> result = hit.getSource();
			audienceSegmentValue = (String) result.get("audience_segment");

			if(audienceSegmentValue !=null && audienceSegmentValue !="" ){
			if (audienceSegmentFrequent.containsKey(audienceSegmentValue) == false)
				audienceSegmentFrequent.put(audienceSegmentValue, 1);
			else {
				count = audienceSegmentFrequent.get(audienceSegmentValue);
				audienceSegmentFrequent.put(audienceSegmentValue, count + 1);

			  }
			 }
			}
		// System.out.println(audienceSegment);
		Iterator it = audienceSegmentFrequent.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			set.add(new Frequency((String) pair.getKey(), (Integer) pair
					.getValue()));

		}

		int counter = 0;
		for (Frequency f : set) {
			if (counter == 0) {
				latestaudienceSegment = f.getName();
				break;
			}

		}

		return latestaudienceSegment;
	}

	

}
