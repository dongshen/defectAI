package sdong.defectAI.cluster;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sdong.defectAI.cluster.Kmeans.Node;
import sdong.defectAI.constant.DefectAIConstant;

public class KmeansTest {

	@Test
	public void testSetKmeansInput_with_class() {
		String input = "input" + File.separatorChar + "kmeans" + File.separatorChar + "iris.data";

		Kmeans kmeans = new Kmeans(4);
		kmeans.setKmeansInput(input);
		assertEquals(150, kmeans.getArraylist().size());
		assertEquals("Iris-setosa", kmeans.getArraylist().get(0).classify);
		assertEquals("Iris-versicolor", kmeans.getArraylist().get(50).classify);
		assertEquals("Iris-virginica", kmeans.getArraylist().get(100).classify);

	}

	@Test
	public void testProcess_test() {
		String input = "input" + File.separatorChar + "kmeans" + File.separatorChar + "kmeans_test.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar + "kmeans_result.txt";
		Kmeans kmeans = new Kmeans(2);
		kmeans.setKmeansInput(input, false);
		kmeans.process();
		ArrayList<Node> centerlist = kmeans.getCentroidList();
		// cluster 1 center
		assertEquals(new Double(1.5), (Double) centerlist.get(0).attributes[0]);
		assertEquals(new Double(1.5), (Double) centerlist.get(0).attributes[1]);

		// cluster 2 center
		assertEquals(new Double(6.5), (Double) centerlist.get(1).attributes[0]);
		assertEquals(new Double(5.5), (Double) centerlist.get(1).attributes[1]);

		// cluster 3 center
		assertEquals(new Double(6.5), (Double) centerlist.get(2).attributes[0]);
		assertEquals(new Double(1.5), (Double) centerlist.get(2).attributes[1]);

		// cluster 4 center
		assertEquals(new Double(1.5), (Double) centerlist.get(3).attributes[0]);
		assertEquals(new Double(5.5), (Double) centerlist.get(3).attributes[1]);

		for (Node node : centerlist) {
			for (int i = 0; i < node.attributes.length; i++) {
				System.out.print("att" + i + "=" + node.attributes[i] + " , ");
			}
			System.out.println();
		}

		kmeans.printKmeansResults(outputFile);
	}

	@Test
	public void testProcess_sample_python_matrix_original() {
		String input = "output" + File.separatorChar + "sample-python_matrix_original.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_original_result.txt";

		String valuepath = "output" + File.separatorChar + "sample-python_token_value.txt";
		String outputValue = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_original_result_value.txt";

		Kmeans kmeans = new Kmeans(DefectAIConstant.PYTHON_KIND_SIZE);
		kmeans.setKmeansInput(input, true);
		kmeans.process();

		kmeans.printKmeansResultsWithValue(outputValue, valuepath);
		// verify data;

		kmeans.printKmeansResults(outputFile, getVerifyData());
	}

	@Test
	public void testProcess_sample_python_matrix_weight() {
		String input = "output" + File.separatorChar + "sample-python_matrix_weight.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_weight_result.txt";

		String valuepath = "output" + File.separatorChar + "sample-python_token_value.txt";
		String outputValue = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_weight_result_value.txt";

		Kmeans kmeans = new Kmeans(DefectAIConstant.PYTHON_KIND_SIZE);
		kmeans.setKmeansInput(input, true);
		kmeans.process();

		kmeans.printKmeansResultsWithValue(outputValue, valuepath);
		kmeans.printKmeansResults(outputFile, getVerifyData());
	}

	@Test
	public void testProcess_sample_python_matrix_parameterize() {
		String input = "output" + File.separatorChar + "sample-python_matrix_parameterize.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_parameterize_result.txt";

		String valuepath = "output" + File.separatorChar + "sample-python_parameterize_value.txt";
		String outputValue = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_parameterize_result_value.txt";

		Kmeans kmeans = new Kmeans(DefectAIConstant.PYTHON_KIND_SIZE);
		kmeans.setKmeansInput(input, true);
		kmeans.process();

		kmeans.printKmeansResultsWithValue(outputValue, valuepath);
		kmeans.printKmeansResults(outputFile, getVerifyData());
	}

	@Test
	public void testProcess_sample_python_matrix_parameter_weight() {
		String input = "output" + File.separatorChar + "sample-python_matrix_parameter_weight.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_parameter_weight_result.txt";

		String valuepath = "output" + File.separatorChar + "sample-python_parameterize_value.txt";
		String outputValue = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_parameter_weight_result_value.txt";

		Kmeans kmeans = new Kmeans(DefectAIConstant.PYTHON_KIND_SIZE);
		kmeans.setKmeansInput(input, true);
		kmeans.process();

		kmeans.printKmeansResultsWithValue(outputValue, valuepath);
		kmeans.printKmeansResults(outputFile, getVerifyData());
	}

	@Test
	public void testProcess_iris() {
		String input = "input" + File.separatorChar + "kmeans" + File.separatorChar + "iris.data";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar + "iris_result.txt";
		Kmeans kmeans = new Kmeans(4);
		kmeans.setKmeansInput(input, false);
		kmeans.process();

		kmeans.printKmeansResults(outputFile);

	}

	@Test
	public void testProcess_iris_weight() {
		String input = "input" + File.separatorChar + "kmeans" + File.separatorChar + "iris.data";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar + "iris_weight_result.txt";

		double[] weight = new double[] { 0.0423148148148148, 0.10208333333333335, 0.14717514124293782,
				0.18444444444444485 };

		Kmeans kmeans = new Kmeans(4);
		kmeans.setWeight(weight);
		kmeans.setKmeansInput(input, false);
		kmeans.process();

		kmeans.printKmeansResults(outputFile);

	}

	private Map<String, List<String>> getVerifyData() {

		Map<String, List<String>> resultmap = new HashMap<String, List<String>>() {
			{
				put("try", Arrays.asList("36,64,76,96,164".split(",")));
				put("except", Arrays.asList("38,67,98,121,131,139,147,159,161,173".split(",")));
				put("if",
						Arrays.asList(
								"39,40,45,47,49,51,53,59,80,82,83,92,94,101,103,105,106,112,115,127,156,167,180,189,191"
										.split(",")));
				put("import", Arrays.asList("1,2,3".split(",")));
				put("from", Arrays.asList("0,4,5,6,7,8,9,10,11,12,13,14".split(",")));
			}
		};
		return resultmap;
	}
	
	@Test
	public void testComputeTheK() {
		String input = "input" + File.separatorChar + "kmeans" + File.separatorChar + "iris.data";
		Kmeans kmeans = new Kmeans(4);
		kmeans.setKmeansInput(input, false);
		kmeans.computeTheK();
		List<Node> centers = kmeans.getCentroidList();
		
		assertEquals(3, centers.size());
		assertEquals(13, centers.get(0).seq);
		assertEquals(118, centers.get(1).seq);
		assertEquals(87, centers.get(2).seq);
	}
}
