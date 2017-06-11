package sdong.defectAI.cluster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class Kmeansdong {

	public Kmeansdong(int dimension) {
		this.dimension = dimension;
	}

	public class Node {
		int label;// label 用来记录点属于第几个 cluster
		double[] attributes;
		long seq;
		String classify;
		double distance;

		public Node() {
			attributes = new double[dimension];
		}

		public void setSeq(long seq) {
			this.seq = seq;
		}
	}

	private class NodeComparator {
		Node nodeOne;
		Node nodeTwo;
		double distance;

		public void compute() {
			double val = 0;
			double gap = 0;
			for (int i = 0; i < dimension; ++i) {
				gap = this.nodeOne.attributes[i] - this.nodeTwo.attributes[i];
				val += gap * gap;
			}
			this.distance = val;
		}
	}

	private ArrayList<Node> arraylist;
	private ArrayList<Node> centroidList;
	private double averageDis;
	private int dimension;

	DecimalFormat df = new DecimalFormat("#.######");// 保留小数点后6位

	private Queue<NodeComparator> FsQueue = new PriorityQueue<NodeComparator>(100000, // 用来排序任意两点之间的距离，从大到小排
			new Comparator<NodeComparator>() {
				public int compare(NodeComparator one, NodeComparator two) {
					if (one.distance < two.distance)
						return 1;
					else if (one.distance > two.distance)
						return -1;
					else
						return 0;
				}
			});

	/**
	 * Initial input, the default data without header and the if the kmeans
	 * attribute size is more than split cloumns, the latest column as class.
	 * 
	 * @param path
	 */
	public void setKmeansInput(String path) {
		setKmeansInput(path, false);
	}

	public void setKmeansInput(String path, boolean includeHeader) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String str;
			String[] strArray;
			arraylist = new ArrayList<Node>();
			long seq = 0;
			if (includeHeader == true) {
				seq = -1;
			}
			while ((str = br.readLine()) != null) {
				if (seq == -1) {
					seq = 0;
					continue;
				}
				strArray = str.split(",");
				Node node = new Node();
				node.setSeq(seq);
				seq = seq + 1;
				if (strArray.length < dimension) {
					continue;
				}
				for (int i = 0; i < dimension; ++i) {
					node.attributes[i] = Double.parseDouble(strArray[i]);
				}

				if (strArray.length > dimension) {
					node.classify = strArray[strArray.length - 1];
				}
				arraylist.add(node);

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void computeTheK() {
		int cntTuple = 0;
		for (int i = 0; i < arraylist.size() - 1; ++i) {
			for (int j = i + 1; j < arraylist.size(); ++j) {
				NodeComparator nodecomp = new NodeComparator();
				nodecomp.nodeOne = new Node();
				nodecomp.nodeTwo = new Node();
				for (int k = 0; k < dimension; ++k) {
					nodecomp.nodeOne.attributes[k] = arraylist.get(i).attributes[k];
					nodecomp.nodeTwo.attributes[k] = arraylist.get(j).attributes[k];
				}
				nodecomp.compute();
				averageDis += nodecomp.distance;
				FsQueue.add(nodecomp);
				cntTuple++;
			}
		}
		averageDis /= cntTuple;// 计算平均距离
		chooseCentroid(FsQueue);
	}

	public double getDistance(Node one, Node two) {// 计算两点间的欧氏距离
		double val = 0;
		for (int i = 0; i < dimension; ++i) {
			val += (one.attributes[i] - two.attributes[i]) * (one.attributes[i] - two.attributes[i]);
		}
		return val;
	}

	public void chooseCentroid(Queue<NodeComparator> queue) {
		centroidList = new ArrayList<Node>();
		boolean flag = false;
		while (!queue.isEmpty()) {
			boolean judgeOne = false;
			boolean judgeTwo = false;
			// FsQueue 已经按距离的由大到小排序，第一个点就是距离最大的两点
			NodeComparator nc = FsQueue.poll();
			if (nc.distance < averageDis)
				break;// 如果接下来的元组，两节点间距离小于平均距离，则不继续迭代
			if (!flag) {
				centroidList.add(nc.nodeOne);// 先加入所有点中距离最远的两个点
				centroidList.add(nc.nodeTwo);
				flag = true;
			} else {// 之后从之前已加入的最远的两个点开始，找离这两个点最远的点，
				// 如果距离大于所有点的平均距离，则认为找到了新的质心，否则不认定为质心
				for (int i = 0; i < centroidList.size(); ++i) {
					Node testnode = centroidList.get(i);
					if (centroidList.contains(nc.nodeOne) || getDistance(testnode, nc.nodeOne) < averageDis) {
						judgeOne = true;
					}
					if (centroidList.contains(nc.nodeTwo) || getDistance(testnode, nc.nodeTwo) < averageDis) {
						judgeTwo = true;
					}
				}
				if (!judgeOne) {
					centroidList.add(nc.nodeOne);
				}
				if (!judgeTwo) {
					centroidList.add(nc.nodeTwo);
				}
			}
		}
	}

	public void doIteration(ArrayList<Node> centroid) {

		int cnt = 1;
		int cntEnd = 0;
		int numLabel = centroid.size();
		long[] center = new long[centroid.size()];
		double gap;

		while (true) {// 迭代，直到所有的质心都不变化为止
			// 根据质心选取距离最近的点为实际质心

			if (getNearestPoint(center) == true) {
				break;
			}

			boolean flag = false;
			// 依据与质心的最小距离，标识每个点
			for (int i = 0; i < arraylist.size(); ++i) {
				double dis = 0x7fffffff;
				cnt = 1;
				for (int j = 0; j < centroid.size(); ++j) {
					Node node = centroid.get(j);
					gap = getDistance(arraylist.get(i), node);
					if (gap < dis) {
						dis = gap;
						arraylist.get(i).label = cnt;
						arraylist.get(i).distance = gap;
					}
					cnt++;
				}
			}
			//
			int j = 0;
			while (j < numLabel) {
				// node保存了属于每个质心的点的每个属性的总值，c保存了属于每个质心的点的个数
				int c = 0;
				Node node = new Node();
				for (int i = 0; i < arraylist.size(); ++i) {
					if (arraylist.get(i).label == j + 1) {
						for (int k = 0; k < dimension; ++k) {
							node.attributes[k] += arraylist.get(i).attributes[k];
						}
						c++;
					}
				}

				// 用每个质心所属点的每个属性的平均值更新质心属性。若质心的属性都不更新，则质心稳定
				double attravg;
				for (int i = 0; i < dimension; ++i) {
					attravg = Double.parseDouble(df.format(node.attributes[i] / c));
					if (attravg != centroid.get(j).attributes[i]) {
						centroid.get(j).attributes[i] = attravg;
						flag = true;
					}
				}
				if (!flag) {
					cntEnd++;
					if (cntEnd == numLabel) {// 若所有的质心都不变，则跳出循环
						break;
					}
				}
				j++;
			}
			if (cntEnd == numLabel) {// 若所有的质心都不变，则 success
				System.out.println("run kmeans successfully.");
				// break;
			}
		}
	}

	public boolean getNearestPoint(long[] center) {
		Node nearestNode = new Node();
		double distance;
		double mindistance = 0x7fffffff;
		boolean update = false;
		boolean stable = true;
		for (int i = 0; i < centroidList.size(); i++) {
			update = false;
			mindistance = 0x7fffffff;
			for (Node node : arraylist) {
				distance = getDistance(centroidList.get(i), node);
				if (mindistance > distance) {
					mindistance = distance;
					nearestNode = node;
					update = true;
				}
			}
			if (update == true) {
				if (nearestNode.seq != center[i]) {
					center[i] = nearestNode.seq;
					stable = false;
					for (int j = 0; j < nearestNode.attributes.length; j++) {
						centroidList.get(i).attributes[j] = nearestNode.attributes[j];
					}
				}
			}

		}
		return stable;
	}

	public void process() {
		computeTheK();
		doIteration(centroidList);
	}

	public Map<Integer, String> getClusterList() {
		Map<Integer, String> map = new TreeMap<Integer, String>();

		String str;
		for (int i = 0; i < arraylist.size(); ++i) {
			str = map.get(arraylist.get(i).label);
			if (str == null) {
				map.put(arraylist.get(i).label, arraylist.get(i).seq + "");
			} else {
				map.put(arraylist.get(i).label, str + "," + arraylist.get(i).seq);
			}
		}

		return map;
	}

	public void printKmeansResults(String path) {
		try {
			PrintStream out = new PrintStream(path);
			out.println("There are " + centroidList.size() + " clusters!");
			// print cluster detail
			Map<Integer, String> map = this.getClusterList();
			String clusterlist;
			int ind;
			String strline;
			for (Integer key : map.keySet()) {
				out.println("cluster " + key + " : ");
				out.println("----------------------");
				clusterlist = map.get(key);
				for (String index : clusterlist.split(",")) {
					ind = Integer.parseInt(index.trim());
					for (int j = 0; j < dimension; ++j) {
						out.print(arraylist.get(ind).attributes[j] + ", ");
					}
					out.println(arraylist.get(ind).classify + ", " + df.format(arraylist.get(ind).distance));
				}
				out.println("----------------------");
			}
			/*
			 * for (int i = 0; i < arraylist.size(); ++i) { out.print("(" +
			 * arraylist.get(i).seq + ") "); out.print("( " +
			 * arraylist.get(i).label + " ) ("); for (int j = 0; j < dimension;
			 * ++j) { out.print(arraylist.get(i).attributes[j] + ", "); }
			 * out.println(arraylist.get(i).classify + ") ");
			 * 
			 * }
			 */
			// print center
			for (int i = 0; i < centroidList.size(); ++i) {
				out.print("(cluster " + (i + 1) + " center) (");
				for (int j = 0; j < dimension - 1; ++j) {
					out.print(centroidList.get(i).attributes[j] + ", ");
				}
				out.println(centroidList.get(i).attributes[dimension - 1] + ") ");

			}

			// print cluster
			for (Integer key : map.keySet()) {
				out.println("cluster " + key + " : " + map.get(key));
			}

			Iris_result_verify iris = new Iris_result_verify();
			for (Integer key : map.keySet()) {
				double[] rate = iris.checkMatchRate(Arrays.asList(map.get(key).split(",")));
				strline = "Cluster " + key + " match rate: " + rate[0] + ", not match rate: " + rate[1] + " size: "
						+ rate[2];
				out.println(strline);
				System.out.println(strline);
			}

			out.close();
			System.out.println("Please check results in: " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Node> getArraylist() {
		return arraylist;
	}

	public ArrayList<Node> getCentroidList() {
		return centroidList;
	}

	public double getAverageDis() {
		return averageDis;
	}

}