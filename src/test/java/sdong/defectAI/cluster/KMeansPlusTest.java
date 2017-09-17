package sdong.defectAI.cluster;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.log.Log;

import net.sf.javaml.core.AbstractInstance;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.tools.InstanceTools;
import net.sf.javaml.tools.data.FileHandler;
import sdong.defectAI.cluster.evaluation.RandIndex;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.DatasetUtils;
import sdong.defectAI.weight.WeightSetting;

public class KMeansPlusTest {
	@Before
	public void before() {
		AbstractInstance.nextID = 0;
	}

	@Test
	public void testComputeTheK() {
		try {
			/* Load a dataset */
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			/*
			 * Create a new instance of the KMeans algorithm, with all options
			 * specified. This instance of the k-means algorithm will generate 3
			 * clusters, will run for 100 iteration and will use the euclidean
			 * distance.
			 */
			KMeansPlus km = new KMeansPlus();
			int numofcluster = km.computeTheK(data);

			assertEquals(3, numofcluster);

			List<Instance> center = km.getCentroids();
			assertEquals(13, center.get(0).getID());
			assertEquals(118, center.get(1).getID());
			assertEquals(114, center.get(2).getID());

		} catch (IOException e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testCluster_iris() {
		try {
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			assertEquals(3, clusters.length);

			DatasetUtils.exportDatasetWithCluster(clusters, "output/kmeansplus/iris/iris_result.txt");

			// evaluation
			RandIndex index = new RandIndex(clusters);
			assertEquals(new Double(0.8206565252201762), new Double(index.getF()));

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCluster_iris_normalize() {
		try {
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			/* Normalize the data to [0,1] */

			NormalizeMidrange dnm = new NormalizeMidrange(0.5, 1);
			dnm.filter(data);

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			assertEquals(5, clusters.length);

			DatasetUtils.exportDatasetWithCluster(clusters, "output/kmeansplus/iris/iris_normalize_result.txt");

			// evaluation
			RandIndex index = new RandIndex(clusters);
			assertEquals(new Double(0.6218570254724732), new Double(index.getF()));

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCluster_iris_relief_weight() {
		try {
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			Dataset weights = FileHandler.loadDataset(new File("output/weight/iris/iris_relief_all.txt"), -1, ",");
			Instance inst = weights.instance(0);
			WeightSetting weight = new WeightSetting(InstanceTools.array(inst));
			DatasetUtils.multiplyInstance(data, weight);

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			assertEquals(3, clusters.length);

			DatasetUtils.exportDatasetWithCluster(clusters, "output/kmeansplus/iris/iris_relief_weight.txt");

			// evaluation
			RandIndex index = new RandIndex(clusters);
			assertEquals(new Double(0.8893093661305582), new Double(index.getF()));

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCluster_iris_relieff_weight() {
		try {
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			Dataset weights = FileHandler.loadDataset(new File("output/weight/iris/iris_relieff_all.txt"), -1, ",");
			Instance inst = weights.instance(0);
			WeightSetting weight = new WeightSetting(InstanceTools.array(inst));
			DatasetUtils.multiplyInstance(data, weight);

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			assertEquals(3, clusters.length);

			DatasetUtils.exportDatasetWithCluster(clusters, "output/kmeansplus/iris/iris_relieff_weight.txt");

			// evaluation
			RandIndex index = new RandIndex(clusters);
			assertEquals(new Double(0.8893093661305582), new Double(index.getF()));

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCluster_iris_relieff_weight_N2() {
		try {
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			Dataset weights = FileHandler.loadDataset(new File("output/weight/iris/iris_relieff_N2.txt"), -1, ",");
			Instance inst = weights.instance(0);
			WeightSetting weight = new WeightSetting(InstanceTools.array(inst));
			DatasetUtils.multiplyInstance(data, weight);

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			assertEquals(3, clusters.length);

			DatasetUtils.exportDatasetWithCluster(clusters, "output/kmeansplus/iris/iris_relieff_weight_N2.txt");

			// evaluation
			RandIndex index = new RandIndex(clusters);
			assertEquals(new Double(0.8495810613113446), new Double(index.getF()));

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCluster_iris_relieff_weight_N10() {
		try {
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			Dataset weights = FileHandler.loadDataset(new File("output/weight/iris/iris_relieff_N10.txt"), -1, ",");
			Instance inst = weights.instance(0);
			WeightSetting weight = new WeightSetting(InstanceTools.array(inst));
			DatasetUtils.multiplyInstance(data, weight);

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			assertEquals(3, clusters.length);

			DatasetUtils.exportDatasetWithCluster(clusters, "output/kmeansplus/iris/iris_relieff_weight_N10.txt");

			// evaluation
			RandIndex index = new RandIndex(clusters);
			assertEquals(new Double(0.8495810613113446), new Double(index.getF()));

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCluster_iris_relieff_weight_N() {
		try {
			double maxF = 0d;
			int maxN = 0;
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			for (int i = 1; i < 50; i++) {
				Dataset weights = FileHandler.loadDataset(new File("output/weight/iris/iris_relieff_N" + i + ".txt"),
						-1, ",");
				Instance inst = weights.instance(0);
				WeightSetting weight = new WeightSetting(InstanceTools.array(inst));
				DatasetUtils.multiplyInstance(data, weight);

				KMeansPlus km = new KMeansPlus();
				Dataset[] clusters = km.cluster(data);
				assertEquals(3, clusters.length);

				DatasetUtils.exportDatasetWithCluster(clusters,
						"output/kmeansplus/iris/iris_relieff_weight_N" + i + ".txt");

				// evaluation
				RandIndex index = new RandIndex(clusters);
				Log.debug("N=" + i + ",FValue=" + index.getF());
				if (index.getF() > maxF) {
					maxF = index.getF();
					maxN = i;
				}
			}

			Log.info("maxN=" + maxN);
			Log.info("maxF=" + maxF);
			assertEquals(new Double(0.923265306122449), new Double(maxF));
			assertEquals(6, maxN);
		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCluster_density_iris() {
		try {
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			KMeansPlus km = new KMeansPlus();
			int K = km.computeKByDensity(data);
			// assertEquals(new Double(0.8206565252201762), new
			// Double(index.getF()));

		} catch (IOException e) {
			Assert.assertTrue(false);
		}
	}

}
