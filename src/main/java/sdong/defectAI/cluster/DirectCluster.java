package sdong.defectAI.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.FileUtil;

public class DirectCluster {
	double[][] dist;
	List<String> category = new ArrayList<String>();
	List<Double> sort = new ArrayList<Double>();
	List<List<NodeDistance>> trees = new ArrayList<List<NodeDistance>>();
	int dimension;
	int minX = 0;
	int minY = 0;

	// drop lines
	String drop = ",";
	int loop = 1;

	private class NodeDistance {
		int nodeOne;
		int nodeTwo;
		double distance;

		public NodeDistance(int nodeOne, int nodeTwo, double distance) {
			this.nodeOne = nodeOne;
			this.nodeTwo = nodeTwo;
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
			drop = drop + minX + ",";

			loop = loop + 1;
		}
	}

	private void buildTree(int minX, int minY, double distance) {
		if (trees.isEmpty()) {
			NodeDistance node = new NodeDistance(minX, minY, distance);
			List<NodeDistance> branch = new ArrayList<NodeDistance>();
			branch.add(node);
			trees.add(branch);
			return;
		}
		boolean find = false;

		for (List<NodeDistance> branch : trees) {
			for (NodeDistance nodes : branch) {
				if (nodes.nodeOne == minX || nodes.nodeOne == minY || nodes.nodeTwo == minX || nodes.nodeTwo == minY) {
					NodeDistance node = new NodeDistance(minX, minY, distance);
					branch.add(node);
					find = true;
					break;
				}
			}
		}
		if (find == false) {
			NodeDistance node = new NodeDistance(minX, minY, distance);
			List<NodeDistance> branch = new ArrayList<NodeDistance>();
			branch.add(node);
			trees.add(branch);
		}

	}

	public int getClusterK(double threshold) {
		int k = 0;
		NodeDistance last = null;
		Map<String, List<NodeDistance>> seq = new HashMap<String, List<NodeDistance>>();

		for (List<NodeDistance> branch : trees) {
			last = null;
			List<NodeDistance> list = new ArrayList<NodeDistance>();
			for (NodeDistance nodes : branch) {
				if (nodes.distance <= threshold) {
					list.add(nodes);
				} else {
					if (list.size() == 0) {
						// list.add(last);

					} else {
						last = list.get(list.size() - 1);
						String lastKey = last.nodeOne + "," + last.nodeTwo;
						List<NodeDistance> listnodes = seq.get(lastKey);
						if (listnodes == null) {
							seq.put(lastKey, list);
						} else {
							listnodes.addAll(list);
						}
					}
					break;
				}
			}
		}

		k = seq.size();
		int includeNode = 0;
		for (Map.Entry<String, List<NodeDistance>> entry : seq.entrySet()) {
			List<NodeDistance> list = entry.getValue();
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			for (NodeDistance node : list) {
				map.put(node.nodeOne, node.nodeTwo);
				map.put(node.nodeTwo, node.nodeOne);
			}
			List<Integer> mapKeyList = new ArrayList<Integer>(map.keySet());
			includeNode = includeNode + mapKeyList.size();
		}

		k = k + (dimension - includeNode);
		return k;
	}

	public void printResult(String path) throws DefectAIException {
		List<String> strList = new ArrayList<String>();
		String strline = "";

		// print header
		for (int i = 0; i < dimension; i++) {
			if (i == 0) {
				strline = String.format("%4d", i);
			} else {
				strline = strline + "," + String.format("%4d", i);
			}
		}
		strList.add(strline);

		for (int i = 0; i < dist.length; i++) {

			strline = "";
			for (int j = 0; j < dist[i].length; j++) {
				if (j == 0) {
					strline = String.format("%10.5f", dist[i][j]);
				} else {
					strline = strline + String.format("%10.5f", dist[i][j]) + ",";
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
		for (List<NodeDistance> branch : trees) {
			strline = "";
			for (NodeDistance nodes : branch) {
				strline = strline + "[" + String.format("%3d", nodes.nodeOne) + ","
						+ String.format("%3d", nodes.nodeTwo) + "," + String.format("%10.5f", nodes.distance) + "] ";
			}
			strList.add(strline);
		}

		FileUtil.saveFile(strList, path);

	}
}
