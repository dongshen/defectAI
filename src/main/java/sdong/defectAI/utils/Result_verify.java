package sdong.defectAI.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

public class Result_verify {

	public Map<String, List<String>> resultmap = new HashMap<String, List<String>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1534204619678068638L;

		{
			put("Iris-setosa",
					Arrays.asList(
							"0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49"
									.split(",")));
			put("Iris-versicolor",
					Arrays.asList(
							"50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99"
									.split(",")));
			put("Iris-virginica",
					Arrays.asList(
							"100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149"
									.split(",")));
		}
	};

	public String[] checkMatchRate(List<String> result) {
		String[] maxRate = new String[4];
		double curRate = 0;
		double maxvalue = 0;
		for (Map.Entry<String, List<String>> entry : resultmap.entrySet()) {
			List<String> intersectionlist = getIntersectionOfList(entry.getValue(), result);
			curRate = (double) intersectionlist.size() / entry.getValue().size();
			if (curRate > maxvalue) {
				maxvalue = curRate;
				maxRate[0] = entry.getKey();
				maxRate[1] = String.valueOf(curRate);
				maxRate[2] = String.valueOf((double) (result.size() - intersectionlist.size()) / result.size());
				maxRate[3] = String.valueOf(result.size());
			}
		}

		return maxRate;
	}

	List<String> getIntersectionOfList(List<String> expectlist, List<String> result) {
		return new ArrayList<String>(CollectionUtils.intersection(expectlist, result));
	}

	public Map<String, List<String>> getResultmap() {
		return resultmap;
	}

	public void setResultmap(Map<String, List<String>> resultmap) {
		this.resultmap = resultmap;
	}

}
