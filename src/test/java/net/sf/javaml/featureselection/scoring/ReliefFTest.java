package net.sf.javaml.featureselection.scoring;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.MatrixUtil;

public class ReliefFTest {

	@Test
	public void testBuild_iris_all() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			ReliefF ga = new ReliefF();
			// ga.setNumNeigbors(3);

			ga.build(data);
			double[] weight = ga.getWeights();

			MatrixUtil.printMatrix(weight, "output/weight/iris/iris_relieff_all.txt");
			assertEquals(new Double(0.1556618015685394), new Double(weight[0]));
			assertEquals(new Double(0.06470690866180988), new Double(weight[1]));
			assertEquals(new Double(0.3862149270623026), new Double(weight[2]));
			assertEquals(new Double(0.40580525421752667), new Double(weight[3]));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
