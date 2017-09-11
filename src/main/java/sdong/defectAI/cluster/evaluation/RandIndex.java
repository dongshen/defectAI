package sdong.defectAI.cluster.evaluation;

/**
 * Reference: 
 * https://nlp.stanford.edu/IR-book/html/htmledition/evaluation-of-clustering-1.htm
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.esotericsoftware.minlog.Log;

import net.sf.javaml.core.Dataset;
import sdong.defectAI.utils.DatasetUtils;

public class RandIndex {

	private static final Logger LOG = Logger.getLogger(RandIndex.class);

	int TPAndFP;
	int FP;
	int TP;
	int TN;
	int FN;
	double P;
	double R;
	double F;
	double RI;
	double purity;
	double nmi;

	public RandIndex(Dataset[] clusters){
		List<List<Integer>> lists = DatasetUtils.convertClusterToList(clusters);
		calculate(lists);
	}
	
	public RandIndex(List<List<Integer>> lists) {
		calculate(lists);
	}
	
	public void calculate(List<List<Integer>> lists){
		int[] clusterSizeList = ClusterUtils.computeClusterSize(lists);
		int N = ClusterUtils.computeDataSetSize(clusterSizeList);

		TPAndFP = ClusterUtils.computeTPAndFP(clusterSizeList);
		// 每个聚类中每种分类的数量 the count of each type in cluster
		List<Map<Integer, Integer>> mapList = getCountForEachTypeInCluster(lists);

		// 分类列表 type list
		Set<Integer> set = new HashSet<>();
		for (Map<Integer, Integer> map : mapList) {
			set.addAll(map.keySet());
		}

		TP = ClusterUtils.computeTP(mapList);
		FP = TPAndFP - TP;

		// 每种分类在每个聚类中的数量 The count of each type in each cluster
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
		FN = ClusterUtils.computeFN(lists1);
		TN = ClusterUtils.combination(N, 2) - TPAndFP - FN;

		RI = 1.0 * (TP + TN) / (TP + FP + FN + TN);

		/**
		 * compute Purity
		 */
		int totalMax = 0;
		for (Map<Integer, Integer> map : mapList) {
			totalMax += map.values().stream().reduce(Math::max).get();
		}
		purity = 1.0 * totalMax / N;

		/**
		 * compute F
		 */
		P = 1.0 * TP / (TP + FP);
		R = 1.0 * TP / (TP + FN);

		double beta = 1;
		F = ClusterUtils.computeFValue(P, R, beta);
		
		printIndex();
	}

	public void printIndex() {

		LOG.debug("TP&FP=" + getTPAndFP());

		LOG.debug("TP=" + getTP());

		LOG.debug("FP=" + getFP());

		LOG.debug("FN=" + getFN());

		LOG.debug("TN=" + getTN());

		LOG.debug("RI=" + getRI());

		LOG.debug("P=" + getP());

		LOG.debug("R=" + getR());

		LOG.debug("F=" + getF());

		LOG.debug("Purify=" + getPurity());
	}

	/**
	 * println Normalized Mutual Information
	 */
	public static double calculateNMI(List<List<Integer>> lists) {
		int K = lists.size();

		int[] clusterSizeList = ClusterUtils.computeClusterSize(lists);
		int N = ClusterUtils.computeDataSetSize(clusterSizeList);

		Map<Integer, Integer> map = new HashMap<>();
		for (List<Integer> list : lists) {
			for (Integer integer : list) {
				map.put(integer, map.getOrDefault(integer, 0) + 1);
			}
		}
		double clusterEntropy = 0;
		for (int cluster : clusterSizeList) {
			double tmp = 1.0 * cluster / N;
			clusterEntropy -= (tmp * (Math.log(tmp) / Math.log(2)));
		}
		Log.debug("clusterEntropy = " + clusterEntropy);

		double classEntropy = 0;
		for (Integer integer : map.values()) {
			double tmp = 1.0 * integer / N;
			classEntropy -= (tmp * (Math.log(tmp) / Math.log(2)));
		}
		Log.debug("classEntropy = " + classEntropy);

		double totalEntropy = 0;
		Map<Integer, Integer> tmpMap = new HashMap<>();
		for (int i = 0; i < K; i++) {
			int wk = clusterSizeList[i];
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
		Log.debug("totalEntropy = " + totalEntropy);

		double nmi = 2 * totalEntropy / (clusterEntropy + classEntropy);
		Log.debug(String.format("nmi = %.2f", nmi));

		return nmi;
	}

	private List<Map<Integer, Integer>> getCountForEachTypeInCluster(List<List<Integer>> lists) {
		List<Map<Integer, Integer>> mapList = new ArrayList<>();
		for (List<Integer> list : lists) {
			Map<Integer, Integer> map = new HashMap<>();
			for (Integer integer : list) {
				map.put(integer, map.getOrDefault(integer, 0) + 1);
			}
			mapList.add(map);
		}
		return mapList;
	}

	public int getTPAndFP() {
		return TPAndFP;
	}

	public void setTPAndFP(int tPAndFP) {
		TPAndFP = tPAndFP;
	}

	public int getFP() {
		return FP;
	}

	public void setFP(int fP) {
		FP = fP;
	}

	public int getTP() {
		return TP;
	}

	public void setTP(int tP) {
		TP = tP;
	}

	public int getTN() {
		return TN;
	}

	public void setTN(int tN) {
		TN = tN;
	}

	public int getFN() {
		return FN;
	}

	public void setFN(int fN) {
		FN = fN;
	}

	public double getP() {
		return P;
	}

	public void setP(double p) {
		P = p;
	}

	public double getR() {
		return R;
	}

	public void setR(double r) {
		R = r;
	}

	public double getF() {
		return F;
	}

	public void setF(double f) {
		F = f;
	}

	public double getRI() {
		return RI;
	}

	public void setRI(double rI) {
		RI = rI;
	}

	public double getPurity() {
		return purity;
	}

	public void setPurity(double purity) {
		this.purity = purity;
	}

	public double getNmi() {
		return nmi;
	}

	public void setNmi(double nmi) {
		this.nmi = nmi;
	}

}