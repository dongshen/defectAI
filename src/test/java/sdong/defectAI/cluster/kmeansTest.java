package sdong.defectAI.cluster;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import sdong.defectAI.cluster.Kmeans.Node;
import sdong.defectAI.constant.DefectAIConstant;

public class kmeansTest {

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
		Kmeans kmeans = new Kmeans(DefectAIConstant.PYTHON_KIND_SIZE);
		kmeans.setKmeansInput(input, true);
		kmeans.process();
		ArrayList<Node> centerlist = kmeans.getCentroidList();
		kmeans.printKmeansResults(outputFile);
	}
	
	@Test
	public void testProcess_sample_python_matrix_weight() {
		String input = "output" + File.separatorChar + "sample-python_matrix_weight.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_weight_result.txt";
		Kmeans kmeans = new Kmeans(DefectAIConstant.PYTHON_KIND_SIZE);
		kmeans.setKmeansInput(input, true);
		kmeans.process();
		ArrayList<Node> centerlist = kmeans.getCentroidList();
		kmeans.printKmeansResults(outputFile);
	}
	
	@Test
	public void testProcess_sample_python_matrix_parameterize() {
		String input = "output" + File.separatorChar + "sample-python_matrix_parameterize.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_parameterize_result.txt";
		Kmeans kmeans = new Kmeans(DefectAIConstant.PYTHON_KIND_SIZE);
		kmeans.setKmeansInput(input, true);
		kmeans.process();
		ArrayList<Node> centerlist = kmeans.getCentroidList();
		kmeans.printKmeansResults(outputFile);
	}

	@Test
	public void testProcess_sample_python_matrix_parameter_weight() {
		String input = "output" + File.separatorChar + "sample-python_matrix_parameter_weight.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "kmeans_sample-python_matrix_parameter_weight_result.txt";
		Kmeans kmeans = new Kmeans(DefectAIConstant.PYTHON_KIND_SIZE);
		kmeans.setKmeansInput(input, true);
		kmeans.process();
		ArrayList<Node> centerlist = kmeans.getCentroidList();
		kmeans.printKmeansResults(outputFile);
	}
	
	@Test
	public void testProcess_iris() {
		String input = "input" + File.separatorChar+"kmeans"+File.separatorChar + "iris.data";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar
				+ "iris_result.txt";
		Kmeans kmeans = new Kmeans(4);
		kmeans.setKmeansInput(input, false);
		kmeans.process();
		ArrayList<Node> centerlist = kmeans.getCentroidList();
		kmeans.printKmeansResults(outputFile);
		
	}
}
