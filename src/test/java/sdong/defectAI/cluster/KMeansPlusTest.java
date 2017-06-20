package sdong.defectAI.cluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

public class KMeansPlusTest {

	@Test
	public void testCluster() {
		fail("Not yet implemented");
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

}
