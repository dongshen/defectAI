package sdong.defectAI.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectCluster {
	double[][] dist;
	List<String> category = new ArrayList<String>();
	List<Double> sort = new ArrayList<Double>();
	int dimension;
	int minX = 0;
	int minY = 0;

	// drop lines
	String drop = ",";
	int loop = 1;

	public void setDirectClusterInput(double[][] dist) {
		this.dist = dist;
		dimension = dist.length;

	}

	public void computeDC() {

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
			System.out.println("Min[" + minX + "," + minY + "]=" + min);
			category.add("Min[" + minX + "," + minY + "]=" + min);
			drop = drop + minX + ",";

			loop = loop + 1;
		}
	}

	public void printResult() {
		String strline = "";
		for (int i = 0; i < dist.length; i++) {

			strline = "";
			for (int j = 0; j < dist[i].length; j++) {
				strline = strline + "[" + i + "," + j + "]=" + dist[i][j] + " ";
				if (j < i && i >= 1) {
					sort.add(dist[i][j]);
				}
			}
			System.out.println(strline);
		}
		
		System.out.println("Xlength=" + dimension);
		Collections.sort(sort);
		for (Double val : sort) {
			System.out.print(val + ",");
		}
		System.out.println("");

		computeDC();
		for (String str : category) {
			System.out.println(str);
		}

	}
}
