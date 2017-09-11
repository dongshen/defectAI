package sdong.defectAI.cluster.evaluation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import sdong.defectAI.cluster.KMeansPlus;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.DatasetUtils;
import sdong.defectAI.utils.RUtils;

public class RandIndexTest {
	// private Logger LOG = Logger.getLogger(this.getClass().getName());

	private Logger LOG = Logger.getLogger(this.getClass().getName());

	List<List<Integer>> dataset = new ArrayList<List<Integer>>();

	@Before
	public void prepare() {

		// case 1
		dataset.add(Arrays.asList(new Integer[] { 1, 1, 1, 1, 1, 2 }));
		dataset.add(Arrays.asList(new Integer[] { 1, 2, 2, 2, 2, 3 }));
		dataset.add(Arrays.asList(new Integer[] { 1, 1, 3, 3, 3 }));
		LOG.debug(dataset.toString());

	}

	@Test
	public void testCalculateIndex() {
		RandIndex index = new RandIndex(dataset);

		assertEquals(40, index.getTPAndFP());

		assertEquals(20, index.getTP());

		assertEquals(20, index.getFP());

		assertEquals(24, index.getFN());

		assertEquals(72, index.getTN());

		assertEquals(new Double(0.6764705882352942), new Double(index.getRI()));

		assertEquals(new Double(0.5), new Double(index.getP()));

		assertEquals(new Double(0.45454545454545453), new Double(index.getR()));

		assertEquals(new Double(0.47619047619047616), new Double(index.getF()));

		assertEquals(new Double(0.7058823529411765), new Double(index.getPurity()));

		int[][] data = new int[][] { { 1, 1, 1, 1, 1, 2, 1, 2, 2, 2, 2, 3, 1, 1, 3, 3, 3 },
				{ 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3 } };

		try {
			double FValue = RUtils.externalIndices(data[0], data[1], "czekanowski_dice");
			LOG.debug("FValue=" + FValue);
			assertEquals(new Double(0.4761904776096344),new Double(FValue));

		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testCalculateNMI() {
		double nmi = RandIndex.calculateNMI(dataset);
		assertEquals(new Double(0.36456177185718985), new Double(nmi));
	}

	@Test
	public void testCalculateIndex_iris() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);

			double FValue = RUtils.evaluateFValue(clusters);
			assertEquals(new Double(0.8206565380096436), new Double(FValue));

			RandIndex index = new RandIndex(DatasetUtils.convertClusterToList(clusters));
			assertEquals(new Double(0.8206565252201762), new Double(index.getF()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
