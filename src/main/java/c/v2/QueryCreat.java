package c.v2;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;

public class QueryCreat {
	List<Map<String, Object>> edges;

	public QueryCreat(String str, int limit) throws IOException {
		String kb = "http://api.conceptnet.io/c/en/" + str + "?offset=0&limit=" + limit;
		Document docKb = (Document) Jsoup.connect(kb).get();
		String json = ((org.jsoup.nodes.Document) docKb).body().text();
		Gson gson = new Gson();
		Map<String, Object> asMap = gson.fromJson(json, Map.class);
		edges = (List) asMap.get("edges");
	}

	public List<Map<String, Object>> getList() {
		return edges;
	}
}
