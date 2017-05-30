package sdong.defectAI.cluster;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import sdong.defectAI.cluster.Kmeans.Node;

public class kmeansTest {

	@Test
	public void testGetDistance() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintKmeansResults() {
		String input = "input" + File.separatorChar + "kmeans" + File.separatorChar + "kmeans_test.txt";
		String outputFile = "output" + File.separatorChar + "kmeans" + File.separatorChar + "kmeans_result.txt";
		Kmeans kmeans = new Kmeans(2);
		kmeans.setKmeansInput(input, false);
		kmeans.process();
		ArrayList<Node> centerlist = kmeans.getCentroidList();
		for (Node node : centerlist) {
			for (int i = 0; i < node.attributes.length; i++) {
				System.out.print("att" + i + "=" + node.attributes[i] + " , ");
			}
			System.out.println();
		}
		kmeans.printKmeansResults(outputFile);
	}

}
