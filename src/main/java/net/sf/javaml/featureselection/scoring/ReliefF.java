/**
 * %SVN.HEADER%
 */
package net.sf.javaml.featureselection.scoring;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.ManhattanDistance;
import net.sf.javaml.featureselection.FeatureScoring;
import net.sf.javaml.filter.normalize.NormalizeMidrange;

/**
 * Implementation of the RELIEF attribute evaluation algorithm.
 * 
 * This implementation is extended to include more neighbors in calculating the
 * weights of the features.
 * 
 * 
 * 
 * @version %SVN.VERSION%
 * 
 * @author Thomas Abeel
 * 
 */
public class ReliefF implements FeatureScoring {

	private double[] weights = null;

	private int numNeighbors;

	private Random rg;

	public ReliefF() {
		this(2, new Random(System.currentTimeMillis()));
	}

	public ReliefF(int numNeighbors, Random rg) {
		this.numNeighbors = numNeighbors;
		this.rg = rg;
	}

	public void setNumNeigbors(int num) {
		numNeighbors = num;
	}

	public void build(Dataset data) {
		weights = new double[data.noAttributes()];

		/* Normalize the data to [0,1] */

		NormalizeMidrange dnm = new NormalizeMidrange(0.5, 1);
		dnm.filter(data);

		/* Number of iterations */
		int m = data.size();

		for (int i = 0; i < m; i++) {
			Instance random = data.instance(rg.nextInt(data.size()));
			findNearest(data, random);
			for (int j = 0; j < weights.length; j++)
				weights[j] = weights[j] - diff(j, random, nearestHit) / m + diff(j, random, nearestMissList) / m;

		}
	}

	private Vector<Instance> nearestHit;

	private Map<String, Vector<Instance>> nearestMissList;

	private double diff(int index, Instance a, Map<String, Vector<Instance>> map) {
		double sum = 0;
		for (Map.Entry<String, Vector<Instance>> entry : map.entrySet()) {
			sum += diff(index, a, entry.getValue());
		}
		return sum / map.size();
	}

	private double diff(int index, Instance a, Vector<Instance> vector) {
		double sum = 0;
		for (Instance b : vector) {
			sum += Math.abs(a.value(index) - b.value(index));
		}
		return sum / vector.size();
	}

	/*
	 * Distance measure to find nearest neighbors
	 */
	private ManhattanDistance dist = new ManhattanDistance();

	/*
	 * Find nearest neighbors that have the same class and that have another
	 * class value. The results are stored in the vectors nearestHit and
	 * nearestMiss.
	 */
	private void findNearest(Dataset data, Instance random) {

		nearestMissList = new HashMap<String, Vector<Instance>>();
		nearestHit = new Vector<Instance>();
		for (Instance i : data) {
			if (!i.equals(random)) {
				String key = (String) i.classValue();
				if (i.classValue().equals(random.classValue())) {
					nearestHit.add(i);
					if (nearestHit.size() > numNeighbors)
						removeFarthest(nearestHit, random);
				} else {
					Vector<Instance> nearestMiss = nearestMissList.get(i.classValue());
					if (nearestMiss == null) {
						nearestMiss = new Vector<Instance>();
					}
					nearestMiss.add(i);
					if (nearestMissList.size() > numNeighbors)
						removeFarthest(nearestMiss, random);
					nearestMissList.put(key, nearestMiss);
				}

			}
		}

	}

	/*
	 * Removes the element from the vector that is farthest from the supplied
	 * element.
	 */
	private void removeFarthest(Vector<Instance> vector, Instance supplied) {
		Instance tmp = null;
		double max = 0;
		for (Instance inst : vector) {
			double tmpDist = dist.measure(inst, supplied);
			if (tmpDist > max) {
				max = tmpDist;
				tmp = inst;
			}
		}
		vector.remove(tmp);
	}

	public double score(int attribute) {
		return weights[attribute];
	}

	public int noAttributes() {
		return weights.length;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public int getNumNeighbors() {
		return numNeighbors;
	}

	public void setNumNeighbors(int numNeighbors) {
		this.numNeighbors = numNeighbors;
	}

	public ManhattanDistance getDist() {
		return dist;
	}

	public void setDist(ManhattanDistance dist) {
		this.dist = dist;
	}

}
