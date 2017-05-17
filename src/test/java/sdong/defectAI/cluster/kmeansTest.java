package sdong.defectAI.cluster;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class kmeansTest {

	@Test
	public void testGetDistance() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintKmeansResults() {
		String input = "input"+File.separatorChar+"kmeans"+File.separatorChar+"kmeans_test.txt";
		String outputFile = "output"+File.separatorChar+"kmeans"+File.separatorChar+"kmeans_result.txt";
		Kmeans kmeans = new Kmeans();
		kmeans.setKmeansInput(input);
		kmeans.printKmeansResults(outputFile);
	}

}
