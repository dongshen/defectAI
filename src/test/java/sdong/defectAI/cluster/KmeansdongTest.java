package sdong.defectAI.cluster;

import java.io.File;

import org.junit.Test;

public class KmeansdongTest {

	@Test
	public void testProcess() {
		String input = "input" + File.separatorChar + "kmeans" + File.separatorChar + "iris.data";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar + "iris_sdong_result.txt";
		Kmeansdong kmeans = new Kmeansdong(4);
		kmeans.setKmeansInput(input, false);
		kmeans.process();
		// ArrayList<Node> centerlist = kmeans.getCentroidList();
		kmeans.printKmeansResults(outputFile);

	}

}
