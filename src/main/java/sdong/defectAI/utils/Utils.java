package sdong.defectAI.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {
	public static List<Entry<String, Double>> sortMapValueDesend(Map<String, Double> map) {

		List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				double gap = o2.getValue() - o1.getValue();
				int ret = 0;
				if (gap > 0) {
					ret = 1;
				} else if (gap < 0) {
					ret = -1;
				} else {
					ret = 0;
				}
				return ret;
			}
		});

		return list;
	}
}
