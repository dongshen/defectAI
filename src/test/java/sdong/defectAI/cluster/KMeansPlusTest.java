package sdong.defectAI.cluster;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.sf.javaml.core.AbstractInstance;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.tools.InstanceTools;
import net.sf.javaml.tools.data.FileHandler;
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
			String[][] rate = DatasetUtils.checkRate(clusters, data);
			assertEquals("Iris-setosa", rate[0][0]);
			assertEquals("1.0", rate[0][1]);

			assertEquals("Iris-virginica", rate[1][0]);
			assertEquals("0.72", rate[1][1]);

			assertEquals("Iris-versicolor", rate[2][0]);
			assertEquals("0.96", rate[2][1]);

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
			String[][] rate = DatasetUtils.checkRate(clusters, data);
			assertEquals("Iris-setosa", rate[0][0]);
			assertEquals("0.72", rate[0][1]);

			assertEquals("Iris-virginica", rate[1][0]);
			assertEquals("0.54", rate[1][1]);

			assertEquals("Iris-setosa", rate[2][0]);
			assertEquals("0.28", rate[2][1]);

			assertEquals("Iris-versicolor", rate[3][0]);
			assertEquals("0.56", rate[3][1]);

			assertEquals("Iris-versicolor", rate[4][0]);
			assertEquals("0.44", rate[4][1]);

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCluster_iris_weight() {
		try {
			Dataset data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");
			Dataset weights = FileHandler.loadDataset(new File("output/weight/iris/iris_all.txt"), -1, ",");
			Instance inst = weights.instance(0);
			WeightSetting weight = new WeightSetting(InstanceTools.array(inst));
			DatasetUtils.multiplyInstance(data, weight);

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			assertEquals(3, clusters.length);

			DatasetUtils.exportDatasetWithCluster(clusters, "output/kmeansplus/iris/iris_weight.txt");
			String[][] rate = DatasetUtils.checkRate(clusters, data);
			assertEquals("Iris-setosa", rate[0][0]);
			assertEquals("1.0", rate[0][1]);

			assertEquals("Iris-virginica", rate[1][0]);
			assertEquals("0.84", rate[1][1]);

			assertEquals("Iris-versicolor", rate[2][0]);
			assertEquals("0.98", rate[2][1]);

		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
