package net.sf.javaml.clustering;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.tools.data.FileHandler;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.DatasetUtils;

public class TestKMeans {

	/**
	 * Tests the k-means algorithm with default parameter settings.
	 */
	@Test
	public void testKMean() {
		try {
			/* Load a dataset */
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			/*
			 * Create a new instance of the KMeans algorithm, with no options
			 * specified. By default this will generate 4 clusters.
			 */
			Clusterer km = new KMeans();
			/*
			 * Cluster the data, it will be returned as an array of data sets,
			 * with each dataset representing a cluster
			 */
			Dataset[] clusters = km.cluster(data);
			System.out.println("Cluster count: " + clusters.length);
			DatasetUtils.exportDatasetWithValue(clusters, "input/iris.data",
					"output/kmeans/javaml_iris_cluster_4k.txt");

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Tests the k-means algorithm with user-specified parameter settings.
	 */
	@Test
	public void testKMeanWithParameters() {
		try {
			/* Load a dataset */
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			/*
			 * Create a new instance of the KMeans algorithm, with all options
			 * specified. This instance of the k-means algorithm will generate 3
			 * clusters, will run for 100 iteration and will use the euclidean
			 * distance.
			 */
			Clusterer km = new KMeans(3, 10000, new EuclideanDistance());
			/*
			 * Cluster the data, it will be returned as an array of data sets,
			 * with each data set representing a cluster
			 */
			Dataset[] clusters = km.cluster(data);
			System.out.println("Cluster count: " + clusters.length);
			
			DatasetUtils.checkRate(clusters, data);
			
			DatasetUtils.exportDatasetWithValue(clusters, "input/iris.data",
					"output/kmeans/javaml_iris_cluster_3k.txt");
			DatasetUtils.exportDatasetWithCluster(clusters, "output/kmeans/javaml_iris_cluster_3k_data.txt");


		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
