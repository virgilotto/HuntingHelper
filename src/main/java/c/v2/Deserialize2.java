package c.v2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Deserialize2 {
	public static void main(String args[]) throws IOException {
		HashMap<String, List> mapa = new HashMap<>();
		List<String> list = new ArrayList<>();
		System.out.println("Enter animal (if it contains more words, put _ in between): ");
		Scanner scanner = new Scanner(System.in);
		String animal = scanner.nextLine();
		try {
			QueryCreat q1 = new QueryCreat(animal, 100);
			for (Map<String, Object> edge : q1.getList()) {
				if (edge.containsKey("@id")) {
					String surfaceText = (String) edge.get("@id");
					if (surfaceText != null) {
						if (surfaceText.contains("AtLocation")) {
							String locationT = surfaceText.replace("/a/[/r/AtLocation/,/c/en/" + animal + "/,/c/en/",
									"");
							String location = locationT.replace("/]", "");
							list.add(location);
							// System.out.println(location);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("...and maybe more...");
		}
		System.out.println("//////////////////////////////////////////");
		try {
			for (String locations : list) {
				List<String> list2 = new ArrayList<>();
				System.out.println("THIS ANIMAL CAN BE FOUND AT " + locations);
				System.out.print("THE PROBABILITY TO FIND " + animal + " AT " + locations + " IS ");
				Filter f1 = new Filter(animal, locations);
				QueryCreat q2 = new QueryCreat(locations, 100);
				for (Map<String, Object> edge2 : q2.getList()) {
					if (edge2.containsKey("@id")) {
						String surfaceText = (String) edge2.get("@id");
						if (surfaceText != null) {
							if (surfaceText.contains("AtLocation")) {
								String thingsT = surfaceText.replace("/a/[/r/AtLocation/,/c/en/", "");
								String things = thingsT.replace("/,/c/en/" + locations + "/]", "");
								list2.add(things);
								// System.out.println(things);
							}
						}
					}
				}
				mapa.put(locations, list2);
			}
		} catch (Exception e) {
			System.out.println("...and maybe more...");
		}
		System.out.println("//////////////////////////////////////////");
		//printMap(mapa);
		try {
			for (Map.Entry<String, List> entry : mapa.entrySet()) {
				String key = entry.getKey();
				List value = entry.getValue();
				System.out.println("SEARCHING OTHER ANIMALS AT " + key);
				for (int i = 0; i < value.size(); i++) {
					String element = (String) value.get(i);
					QueryCreat q3 = new QueryCreat(element, 100);
					int flag = 1;
					for (Map<String, Object> edge3 : q3.getList()) {
						if (flag == 1) {
							if (edge3.containsKey("@id")) {
								String surfaceText = (String) edge3.get("@id");
								if (surfaceText != null) {
									if (!(surfaceText.contains("extinct"))) {
										if (!(surfaceText.contains("CapableOf"))) {
											if (!(surfaceText.contains("DistinctFrom"))) {
												if (surfaceText.contains("animal/]") || surfaceText.contains("mammal/]") || surfaceText.contains("animals/]") || surfaceText.contains("mammals/]")) {
													if (!(element.contains("animal"))) {
														if (!(element.contains(animal))) {
															System.out.println(element);
															flag = 0;
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("...and maybe more...");
		}
		System.out.println("DONE!");
	}

	public static void printMap(Map mp) {
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}
	
	public static String eraseLast(String str) {
	    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 's') {
	        str = str.substring(0, str.length() - 1);
	    }
	    return str;
	}
}