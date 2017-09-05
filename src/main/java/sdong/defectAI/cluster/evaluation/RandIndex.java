package sdong.defectAI.cluster.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sdong.defectAI.cluster.utils.ClusterUtils;

public class RandIndex {

	public static void calculateIndex(List<List<Integer>> lists) {

		int[] clusters =  ClusterUtils.computeClusterSize(lists);
		int N = ClusterUtils.computeDataSetSize(clusters);

		int TPAndFP = ClusterUtils.computeTPAndFP(clusters);
		List<Map<Integer, Integer>> mapList = new ArrayList<>();
		for (List<Integer> list : lists) {
			Map<Integer, Integer> map = new HashMap<>();
			for (Integer integer : list) {
				map.put(integer, map.getOrDefault(integer, 0) + 1);
			}
			mapList.add(map);
		}
		Set<Integer> set = new HashSet<>();
		for (Map<Integer, Integer> map : mapList) {
			set.addAll(map.keySet());
		}
		int FP = ClusterUtils.computeFP(mapList);
		int TP = TPAndFP - FP;
		List<List<Integer>> lists1 = new ArrayList<>();
		for (Integer integer : set) {
			List<Integer> list = new ArrayList<>();
			for (Map<Integer, Integer> map : mapList) {
				if (map.containsKey(integer)) {
					list.add(map.get(integer));
				}
			}
			lists1.add(list);
		}
		int FN = ClusterUtils.computeFN(lists1);
		int TN = ClusterUtils.combination(N, 2) - TPAndFP - FN;
		// System.out.println("TP = " + TP);
		// System.out.println("FP = " + FP);
		// System.out.println("FN = " + FN);
		// System.out.println("TN = " + TN);
		double RI = 1.0 * (TP + TN) / (TP + FP + FN + TN);
		/**
		 * compute Purity
		 */
		int totalMax = 0;
		for (Map<Integer, Integer> map : mapList) {
			totalMax += map.values().stream().reduce(Math::max).get();
		}
		double purity = 1.0 * totalMax / N;
		System.out.println(String.format("purity = %.2f", purity));
		/**
		 * println Normalized Mutual Information
		 */
		calculateNMI(lists);
		System.out.println(String.format("RI = %.2f", RI));
		/**
		 * compute F5
		 */
		double P = 1.0 * TP / (TP + FP);
		double R = 1.0 * TP / (TP + FN);
		double beta = 1;
		System.out.println(String.format("P = %.2f", P));
		System.out.printf("R = %.3f\n", R);
		System.out.println(String.format("beta = 1, F = %.2f", ClusterUtils.computeFValue(P, R, beta)));
		beta = 5;
		System.out.println(String.format("beta = 5, F = %.3f", ClusterUtils.computeFValue(P, R, beta)));
	}
	
	public static void calculateNMI(List<List<Integer>> lists) {   
        int K = lists.size();  
        int N = 0;  
        int[] clusters = new int[K];  
        for (int i = 0; i < K; i++) {  
            clusters[i] = lists.get(i).size();  
            N += clusters[i];  
        }  
        Map<Integer, Integer> map = new HashMap<>();  
        for (List<Integer> list : lists) {  
            for (Integer integer : list) {  
                map.put(integer, map.getOrDefault(integer, 0) + 1);  
            }  
        }  
        double clusterEntropy = 0;  
        for (int cluster : clusters) {  
            double tmp = 1.0 * cluster / N;  
            clusterEntropy -= (tmp * (Math.log(tmp) / Math.log(2)));  
        }  
//        System.out.println("clusterEntropy = " + clusterEntropy);  
        double classEntropy = 0;  
        for (Integer integer : map.values()) {  
            double tmp = 1.0 * integer / N;  
            classEntropy -= (tmp * (Math.log(tmp) / Math.log(2)));  
        }  
//        System.out.println("classEntropy = " + classEntropy);  
        double totalEntropy = 0;  
        Map<Integer, Integer> tmpMap = new HashMap<>();  
        for (int i = 0; i < K; i++) {  
            int wk = clusters[i];  
            tmpMap.clear();  
            for (Integer integer : lists.get(i)) {  
                tmpMap.put(integer, tmpMap.getOrDefault(integer, 0) + 1);  
            }  
            for (Map.Entry<Integer, Integer> entry : tmpMap.entrySet()) {  
                int cj = map.get(entry.getKey());  
                int value = entry.getValue();  
                totalEntropy += (1.0 * value / N * (Math.log(1.0 * N * value / (wk * cj)) / Math.log(2)));  
            }  
        }  
//        System.out.println("totalEntropy = " + totalEntropy);  
        double nmi = 2 * totalEntropy / (clusterEntropy + classEntropy);  
        System.out.println(String.format("nmi = %.2f", nmi));  
    }  
}