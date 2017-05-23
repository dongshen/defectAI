package sdong.defectAI.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.FileUtil;

public class DirectCluster {
	double[][] dist;
	List<String> category = new ArrayList<String>();
	List<Double> sort = new ArrayList<Double>();
	List<List<NodeCompare>> trees = new ArrayList<List<NodeCompare>>();

	// save kind and sequence list
	Map<Integer, List<List<Integer>>> seqList = new HashMap<Integer, List<List<Integer>>>();

	// save kind with distance
	Map<Double, Integer> kindMap = new HashMap<Double, Integer>();
	int dimension;
	int minX = 0;
	int minY = 0;

	// drop lines
	String drop = ",";
	int loop = 1;

	private class NodeCompare {
		int node1;
		int node2;
		double distance;

		public NodeCompare(int nodeOne, int nodeTwo, double distance) {
			this.node1 = nodeOne;
			this.node2 = nodeTwo;
			this.distance = distance;
		}
	}

	public void setDirectClusterInput(double[][] dist) {
		this.dist = dist;
		dimension = dist.length;

	}

	public void compute() {

		while (loop < dimension) {
			double min = 0x7fffffff;
			for (int i = 1; i < dimension; i++) {
				if (drop.indexOf("," + i + ",") >= 0) {
					continue;
				}
				for (int j = 0; j < i; j++) {
					if (drop.indexOf("," + j + ",") >= 0) {
						continue;
					}
					if (dist[i][j] < min) {
						min = dist[i][j];
						minX = i;
						minY = j;
					}
				}
			}
			// System.out.println("Min[" + minX + "," + minY + "]=" + min);
			category.add("[" + minX + "," + minY + "]=" + min);
			buildTree(minX, minY, min);
			sort.add(min);
			drop = drop + minX + ",";

			loop = loop + 1;
		}

		sort = new ArrayList<Double>(new HashSet<Double>(sort));
		Collections.sort(sort);

		// get cluster
		getClusterK();
	}

	private void buildTree(int minX, int minY, double distance) {
		if (trees.isEmpty()) {
			NodeCompare node = new NodeCompare(minX, minY, distance);
			List<NodeCompare> branch = new ArrayList<NodeCompare>();
			branch.add(node);
			trees.add(branch);
			return;
		}
		boolean find = false;

		for (List<NodeCompare> branch : trees) {
			for (NodeCompare nodes : branch) {
				if (nodes.node1 == minX || nodes.node1 == minY || nodes.node2 == minX || nodes.node2 == minY) {
					NodeCompare node = new NodeCompare(minX, minY, distance);
					branch.add(node);
					find = true;
					break;
				}
			}
		}
		if (find == false) {
			NodeCompare node = new NodeCompare(minX, minY, distance);
			List<NodeCompare> branch = new ArrayList<NodeCompare>();
			branch.add(node);
			trees.add(branch);
		}

	}

	public Map<Double, Integer> getClusterK() {

		int k;
		for (Double dist : sort) {
			k = getClusterK(dist);
			kindMap.put(dist, k);

		}
		return kindMap;
	}

	public int getClusterK(double threshold) {
		int k = 0;
		NodeCompare last = null;
		Map<String, List<NodeCompare>> seq = new HashMap<String, List<NodeCompare>>();
		for (List<NodeCompare> branch : trees) {
			last = null;
			List<NodeCompare> list = new ArrayList<NodeCompare>();
			boolean push = false;
			for (NodeCompare nodes : branch) {
				if (nodes.distance <= threshold) {
					list.add(nodes);
				} else {
					if (list.size() > 0) {
						last = list.get(list.size() - 1);
						String lastKey = last.node1 + "," + last.node2;
						List<NodeCompare> listnodes = seq.get(lastKey);
						if (listnodes == null) {
							seq.put(lastKey, list);
						} else {
							listnodes.addAll(list);
						}
						push = true;
					}
					break;
				}
			}
			if (push == false && list.size() > 0) {
				last = list.get(list.size() - 1);
				String lastKey = last.node1 + "," + last.node2;
				List<NodeCompare> listnodes = seq.get(lastKey);
				if (listnodes == null) {
					seq.put(lastKey, list);
				} else {
					listnodes.addAll(list);
				}
			}
		}

		List<List<Integer>> kindlist = new ArrayList<List<Integer>>();

		// all list
		List<Integer> alllist = new ArrayList<Integer>();
		for (int i = 0; i < dimension; i++) {
			alllist.add(i);
		}

		// Get all include nodes
		Map<Integer, Integer> includeMap = new HashMap<Integer, Integer>();
		for (Map.Entry<String, List<NodeCompare>> entry : seq.entrySet()) {
			List<NodeCompare> list = entry.getValue();
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			for (NodeCompare node : list) {
				map.put(node.node1, node.node2);
				map.put(node.node2, node.node1);
				includeMap.put(node.node1, node.node1);
				includeMap.put(node.node2, node.node2);
			}
			kindlist.add(new ArrayList<Integer>(map.keySet()));
		}

		// add different
		alllist.removeAll(new ArrayList<Integer>(includeMap.keySet()));
		for (Integer gap : alllist) {
			List<Integer> gaplist = new ArrayList<Integer>();
			gaplist.add(gap);
			kindlist.add(gaplist);
		}

		k = kindlist.size();
		seqList.put(k, kindlist);

		return k;
	}

	public void printResult(String path) throws DefectAIException {
		List<String> strList = new ArrayList<String>();
		String strline = "";

		// print header
		for (int i = 0; i < dimension; i++) {
			if (i == 0) {
				strline = String.format("%10d", i);
			} else {
				strline = strline + "," + String.format("%10d", i);
			}
		}
		strList.add(strline);

		for (int i = 0; i < dist.length; i++) {

			strline = "";
			for (int j = 0; j < dist[i].length; j++) {
				if (j == 0) {
					strline = String.format("%10.5f", dist[i][j]);
				} else {
					strline = strline + "," + String.format("%10.5f", dist[i][j]);
				}
			}
			strList.add(strline);
		}

		/*
		 * System.out.println("Xlength=" + dimension); Collections.sort(sort);
		 * for (Double val : sort) { System.out.print(val + ","); }
		 * System.out.println("");
		 */
		strline = "";
		for (String str : category) {
			strList.add(str);
		}

		// print tree
		for (List<NodeCompare> branch : trees) {
			strline = "";
			for (NodeCompare nodes : branch) {
				strline = strline + "[" + String.format("%3d", nodes.node1) + "," + String.format("%3d", nodes.node2)
						+ "," + String.format("%10.5f", nodes.distance) + "] ";
			}
			strList.add(strline);
		}

		// print seq;
		for (Map.Entry<Integer, List<List<Integer>>> entry : seqList.entrySet()) {
			List<List<Integer>> kindlist = entry.getValue();

			for (List<Integer> list : kindlist) {
				strList.add("kind=" + entry.getKey() + ":" + list.toString());
			}

		}

		FileUtil.saveFile(strList, path);

	}
}
