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

	@Test
	public void testBuild_iris_N2() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			ReliefF ga = new ReliefF();
			ga.setNumNeigbors(2);

			ga.build(data);
			double[] weight = ga.getWeights();

			MatrixUtil.printMatrix(weight, "output/weight/iris/iris_relieff_N2.txt");
			assertEquals(new Double(0.27906666666666685), new Double(weight[0]));
			assertEquals(new Double(0.17982222222222258), new Double(weight[1]));
			assertEquals(new Double(0.43017627118644125), new Double(weight[2]));
			assertEquals(new Double(0.46714444444444425), new Double(weight[3]));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBuild_iris_N3() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			ReliefF ga = new ReliefF();
			ga.setNumNeigbors(3);

			ga.build(data);
			double[] weight = ga.getWeights();

			MatrixUtil.printMatrix(weight, "output/weight/iris/iris_relieff_N3.txt");
			assertEquals(new Double(0.27585679012345693), new Double(weight[0]));
			assertEquals(new Double(0.1752851851851854), new Double(weight[1]));
			assertEquals(new Double(0.42834952919020797), new Double(weight[2]));
			assertEquals(new Double(0.4611259259259266), new Double(weight[3]));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBuild_iris_N5() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			ReliefF ga = new ReliefF();
			ga.setNumNeigbors(5);

			ga.build(data);
			double[] weight = ga.getWeights();

			MatrixUtil.printMatrix(weight, "output/weight/iris/iris_relieff_N5.txt");
			assertEquals(new Double(0.2662888888888889), new Double(weight[0]));
			assertEquals(new Double(0.16932222222222243), new Double(weight[1]));
			assertEquals(new Double(0.4263683615819214), new Double(weight[2]));
			assertEquals(new Double(0.45531111111111094), new Double(weight[3]));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBuild_iris_N10() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			ReliefF ga = new ReliefF();
			ga.setNumNeigbors(10);

			ga.build(data);
			double[] weight = ga.getWeights();

			MatrixUtil.printMatrix(weight, "output/weight/iris/iris_relieff_N10.txt");
			assertEquals(new Double(0.24951111111111127), new Double(weight[0]));
			assertEquals(new Double(0.15479444444444443), new Double(weight[1]));
			assertEquals(new Double(0.4221536723163846), new Double(weight[2]));
			assertEquals(new Double(0.44897777777777764), new Double(weight[3]));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBuild_iris_N() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			ReliefF ga = new ReliefF();
			for (int i = 1; i < 50; i++) {
				ga.setNumNeigbors(i);

				ga.build(data);
				double[] weight = ga.getWeights();

				MatrixUtil.printMatrix(weight, "output/weight/iris/iris_relieff_N" + i + ".txt");
			}
			/*
			assertEquals(new Double(0.24951111111111127), new Double(weight[0]));
			assertEquals(new Double(0.15479444444444443), new Double(weight[1]));
			assertEquals(new Double(0.4221536723163846), new Double(weight[2]));
			assertEquals(new Double(0.44897777777777764), new Double(weight[3]));
	*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
