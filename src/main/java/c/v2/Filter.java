package c.v2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;

public class Filter {
	double value;

	public Filter(String object1, String object2) throws IOException {
		PrintWriter writer = new PrintWriter("bestlocations.txt", "UTF-8");
		String kb = "http://api.conceptnet.io/relatedness?node1=/c/en/" + object1 + "&node2=/c/en/" + object2;
		Document docKb = (Document) Jsoup.connect(kb).get();
		String json = ((org.jsoup.nodes.Document) docKb).body().text();
		Gson gson = new Gson();
		Map<String, Object> asMap = gson.fromJson(json, Map.class);
		value = (double) asMap.get("value");
		if (value > 0.000 && value < 0.500) {
			System.out.println("NORMAL (" + value + ")");
		} else if (value > 0.500) {
			System.out.println("HIGH (" + value + ")");
			writer.println(object2);
		} else if (value < 0.000) {
			System.out.println("LOW (" + value + ")");
		}
		writer.close();
	}
}
