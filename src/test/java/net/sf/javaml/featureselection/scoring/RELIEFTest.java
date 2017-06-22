package net.sf.javaml.featureselection.scoring;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import net.sf.javaml.core.AbstractInstance;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.MatrixUtil;

public class RELIEFTest {

	@Before
	public void before() {
		AbstractInstance.nextID = 0;
	}

	@Test
	public void testBuild_iris_N2() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			RELIEF ga = new RELIEF();
			ga.setNumNeigbors(2);

			ga.build(data);
			double[] weight = ga.getWeights();

			MatrixUtil.printMatrix(weight, "output/weight/iris/iris_relief_N2.txt");
			assertEquals(new Double(0.038888888888888855), new Double(weight[0]));
			assertEquals(new Double(0.11972222222222231), new Double(weight[1]));
			assertEquals(new Double(0.14903954802259892), new Double(weight[2]));
			assertEquals(new Double(0.19694444444444498), new Double(weight[3]));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBuild_iris_all() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			RELIEF ga = new RELIEF();
			// ga.setNumNeigbors(3);

			ga.build(data);
			double[] weight = ga.getWeights();

			MatrixUtil.printMatrix(weight, "output/weight/iris/iris_relief_all.txt");
			assertEquals(new Double(0.15566180156853945), new Double(weight[0]));
			assertEquals(new Double(0.06470690866180985), new Double(weight[1]));
			assertEquals(new Double(0.38621492706230254), new Double(weight[2]));
			assertEquals(new Double(0.4058052542175265), new Double(weight[3]));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
