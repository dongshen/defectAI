package sdong.defectAI.cluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import sdong.defectAI.exception.DefectAIException;

public class DirectClusterTest {
	double a[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1.5, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 3.1, 2.7, 0, 0, 0, 0, 0, 0, 0 }, { 2.1, 1.4, 1.2, 0, 0, 0, 0, 0, 0 },
			{ 5.8, 6.0, 3.6, 4.7, 0, 0, 0, 0, 0 }, { 4.7, 4.4, 1.8, 2.9, 1.7, 0, 0, 0, 0 },
			{ 5.7, 5.5, 2.9, 4.0, 0.8, 1.0, 0, 0, 0 }, { 1.3, 0.9, 2.2, 1.2, 5.1, 3.9, 5.0, 0, 0 },
			{ 2.6, 1.6, 1.2, 0.5, 4.8, 3.0, 3.3, 1.4, 0 } };

	
	@Test
	public void testCompute() {
		String path = "output" + File.separatorChar + "directCluster.txt";

		try {
			DirectCluster cluster = new DirectCluster();
			cluster.setDirectClusterInput(a);
			cluster.compute();
			cluster.printResult(path);

			assertEquals("[8,3]=0.5", cluster.category.get(0));
			assertEquals("[6,4]=0.8", cluster.category.get(1));
			assertEquals("[7,1]=0.9", cluster.category.get(2));
			assertEquals("[3,2]=1.2", cluster.category.get(3));
			assertEquals("[1,0]=1.5", cluster.category.get(4));
			assertEquals("[5,4]=1.7", cluster.category.get(5));
			assertEquals("[2,0]=3.1", cluster.category.get(6));
			assertEquals("[4,0]=5.8", cluster.category.get(7));
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetClusterK() {

		DirectCluster cluster = new DirectCluster();
		cluster.setDirectClusterInput(a);
		cluster.compute();
		int k = cluster.getClusterK(4.0);
		assertEquals(2, k);
		
		k = cluster.getClusterK(5.0);
		assertEquals(2, k);
		
		k = cluster.getClusterK(2.0);
		assertEquals(3, k);
		
		k = cluster.getClusterK(1.0);
		assertEquals(6, k);
		
		k = cluster.getClusterK(0.7);
		assertEquals(8, k);
		
	}

}
