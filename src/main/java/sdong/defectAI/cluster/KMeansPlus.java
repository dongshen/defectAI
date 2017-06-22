/**
 * %SVN.HEADER%
 */
package sdong.defectAI.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import sdong.defectAI.utils.Utils;

/**
 * Implements the K-means algorithms as described by Mac Queen in 1967.
 * 
 * <bibtex> J. B. MacQueen (1967): "Some Methods for classification and Analysis
 * of Multivariate Observations, Proceedings of 5-th Berkeley Symposium on
 * Mathematical Statistics and Probability", Berkeley, University of California
 * Press, 1:281-297 </bibtex>
 * 
 * 
 * @author Thomas Abeel
 * 
 */
public class KMeansPlus implements Clusterer {
	/**
	 * The number of clusters.
	 */
	private int numberOfClusters = -1;

	/**
	 * The number of iterations the algorithm should make. If this value is
	 * Integer.INFINITY, then the algorithm runs until the centroids no longer
	 * change.
	 * 
	 */
	private int numberOfIterations = -1;

	/**
	 * The distance measure used in the algorithm, defaults to Euclidean
	 * distance.
	 */
	private DistanceMeasure dm;

	/**
	 * The centroids of the different clusters.
	 */
	private List<Instance> centroids = new ArrayList<Instance>();;

	/**
	 * Constuct a default K-means clusterer with 100 iterations, 4 clusters, a
	 * default random generator and using the Euclidean distance.
	 */
	public KMeansPlus() {
		this(100);
	}

	/**
	 * Create a new Simple K-means clusterer with the given number of clusters
	 * and iterations. The internal random generator is a new one based upon the
	 * current system time. For the distance we use the Euclidean n-space
	 * distance.
	 * 
	 * @param clusters
	 *            the number of clusters
	 * @param iterations
	 *            the number of iterations
	 */
	public KMeansPlus(int iterations) {
		this(iterations, new EuclideanDistance());
	}

	/**
	 * Create a new K-means clusterer with the given number of clusters and
	 * iterations. Also the Random Generator for the clusterer is given as
	 * parameter.
	 * 
	 * @param clusters
	 *            the number of clustesr
	 * @param iterations
	 *            the number of iterations
	 * 
	 * @param dm
	 *            the distance measure to use
	 */
	public KMeansPlus(int iterations, DistanceMeasure dm) {
		this.numberOfIterations = iterations;
		this.dm = dm;
	}

	/**
	 * Execute the KMeans clustering algorithm on the data set that is provided.
	 * 
	 * @param data
	 *            data set to cluster
	 * @param clusters
	 *            as an array of Datasets. Each Dataset represents a cluster.
	 */
	public Dataset[] cluster(Dataset data) {
		if (data.size() == 0)
			throw new RuntimeException("The dataset should not be empty");

		numberOfClusters = computeTheK(data);

		if (numberOfClusters == 0)
			throw new RuntimeException("There should be at least one cluster");
		// Place K points into the space represented by the objects that are
		// being clustered. These points represent the initial group of
		// centroids.
		// DatasetTools.

		int instanceLength = data.instance(0).noAttributes();

		int iterationCount = 0;
		boolean centroidsChanged = true;
		while (iterationCount < this.numberOfIterations && centroidsChanged) {
			iterationCount++;
			// Assign each object to the group that has the closest centroid.
			int[] assignment = new int[data.size()];
			for (int i = 0; i < data.size(); i++) {
				int tmpCluster = 0;
				double minDistance = dm.measure(centroids.get(0), data.instance(i));
				for (int j = 1; j < centroids.size(); j++) {
					double dist = dm.measure(centroids.get(j), data.instance(i));
					if (dm.compare(dist, minDistance)) {
						minDistance = dist;
						tmpCluster = j;
					}
				}
				assignment[i] = tmpCluster;

			}
			// When all objects have been assigned, recalculate the positions of
			// the K centroids and start over.
			// The new position of the centroid is the weighted center of the
			// current cluster.
			double[][] sumPosition = new double[this.numberOfClusters][instanceLength];
			int[] countPosition = new int[this.numberOfClusters];
			for (int i = 0; i < data.size(); i++) {
				Instance in = data.instance(i);
				for (int j = 0; j < instanceLength; j++) {

					sumPosition[assignment[i]][j] += in.value(j);

				}
				countPosition[assignment[i]]++;
			}
			centroidsChanged = false;
			for (int i = 0; i < this.numberOfClusters; i++) {

				double[] tmp = new double[instanceLength];
				for (int j = 0; j < instanceLength; j++) {
					tmp[j] = (float) sumPosition[i][j] / countPosition[i];
				}
				Instance newCentroid = new DenseInstance(tmp);
				if (dm.measure(newCentroid, centroids.get(i)) > 0.0001) {
					centroidsChanged = true;
					centroids.set(i, newCentroid);
				}
			}

		}
		Dataset[] output = new Dataset[centroids.size()];
		for (int i = 0; i < centroids.size(); i++)
			output[i] = new DefaultDataset();
		for (int i = 0; i < data.size(); i++) {
			int tmpCluster = 0;
			double minDistance = dm.measure(centroids.get(0), data.instance(i));
			for (int j = 0; j < centroids.size(); j++) {
				double dist = dm.measure(centroids.get(j), data.instance(i));
				if (dm.compare(dist, minDistance)) {
					minDistance = dist;
					tmpCluster = j;
				}
			}
			output[tmpCluster].add(data.instance(i));

		}
		return output;
	}

	public int computeTheK(Dataset data) {
		int NumOfData = data.size();
		int cntTuple = 0;
		double distance = 0.0;
		double averageDis = 0.0;
		Map<String, Double> distanceMatrix = new HashMap<String, Double>();

		for (int i = 0; i < NumOfData - 1; ++i) {
			for (int j = i + 1; j < NumOfData; ++j) {
				distance = dm.measure(data.get(i), data.get(j));
				distanceMatrix.put(i + "," + j, distance);
				averageDis += distance;
				cntTuple++;
			}
		}
		averageDis /= cntTuple;// 计算平均距离

		List<Entry<String, Double>> list = Utils.sortMapValueDesend(distanceMatrix);

		boolean flag = false;

		for (Entry<String, Double> entry : list) {
			boolean judgeOne = false;
			boolean judgeTwo = false;
			// 已经按距离的由大到小排序，第一个点就是距离最大的两点
			distance = entry.getValue();
			String[] node = entry.getKey().split(",");
			Instance nodeOne = data.get(Integer.parseInt(node[0]));
			Instance nodeTwo = data.get(Integer.parseInt(node[1]));
			
			//System.out.println(nodeOne.getID() + "," + nodeTwo.getID());
			
			if (distance < averageDis)
				break;// 如果接下来的元组，两节点间距离小于平均距离，则不继续迭代
			if (!flag) {
				centroids.add(nodeOne);// 先加入所有点中距离最远的两个点
				centroids.add(nodeTwo);
				flag = true;
			} else {// 之后从之前已加入的最远的两个点开始，找离这两个点最远的点，
				// 如果距离大于所有点的平均距离，则认为找到了新的质心，否则不认定为质心
				for (int i = 0; i < centroids.size(); ++i) {
					Instance testnode = centroids.get(i);
					if (centroids.contains(nodeOne) || getDistance(distanceMatrix, testnode, nodeOne) < averageDis) {
						judgeOne = true;
					}
					if (centroids.contains(nodeTwo) || getDistance(distanceMatrix, testnode, nodeTwo) < averageDis) {
						judgeTwo = true;
					}
				}
				if (!judgeOne) {
					centroids.add(nodeOne);
				}
				if (!judgeTwo) {
					centroids.add(nodeTwo);
				}
			}
		}

		return centroids.size();
	}

	private double getDistance(Map<String, Double> distanceMatrix, Instance nodeOne, Instance nodeTwo) {
		Double distance = distanceMatrix.get(nodeOne.getID() + "," + nodeTwo.getID());
		if (distance == null) {
			distance = distanceMatrix.get(nodeTwo.getID() + "," + nodeOne.getID());
		} else {
			// Todo get error
		}

		return distance;
	}

	public List<Instance> getCentroids() {
		return centroids;
	}

	public void setCentroids(List<Instance> centroids) {
		this.centroids = centroids;
	}

}
