package sdong.defectAI.cluster;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import sdong.defectAI.exception.DefectAIException;

public class DirectClusterTest {
	double a[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1.5, 0, 0, 0, 0, 0, 0, 0, 0 }, { 3.1, 2.7, 0, 0, 0, 0, 0, 0, 0 },
			{ 2.1, 1.4, 1.2, 0, 0, 0, 0, 0, 0 }, { 5.8, 6.0, 3.6, 4.7, 0, 0, 0, 0, 0 },
			{ 4.7, 4.4, 1.8, 2.9, 1.7, 0, 0, 0, 0 }, { 5.7, 5.5, 2.9, 4.0, 0.8, 1.0, 0, 0, 0 },
			{ 1.3, 0.9, 2.2, 1.2, 5.1, 3.9, 5.0, 0, 0 }, { 2.6, 1.6, 1.2, 0.5, 4.8, 3.0, 3.3, 1.4, 0 } };

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

		assertEquals("{1.7=3, 1.5=4, 0.8=7, 1.2=5, 0.9=6, 0.5=8, 3.1=2, 5.8=1}", cluster.kindMap.toString());
		
		List<List<Integer>> list = cluster.seqList.get(1);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8]", list.get(0).toString());
		
		list = cluster.seqList.get(2);
		assertEquals("[0, 1, 2, 3, 7, 8]", list.get(0).toString());
		assertEquals("[4, 5, 6]", list.get(1).toString());
		
		list = cluster.seqList.get(3);
		assertEquals("[0, 1, 7]", list.get(0).toString());
		assertEquals("[2, 3, 8]", list.get(1).toString());
		assertEquals("[4, 5, 6]", list.get(2).toString());
		
		list = cluster.seqList.get(4);
		assertEquals("[4, 6]", list.get(0).toString());
		assertEquals("[0, 1, 7]", list.get(1).toString());
		assertEquals("[2, 3, 8]", list.get(2).toString());
		assertEquals("[5]", list.get(3).toString());
		
		list = cluster.seqList.get(5);
		assertEquals("[4, 6]", list.get(0).toString());
		assertEquals("[2, 3, 8]", list.get(1).toString());
		assertEquals("[1, 7]", list.get(2).toString());
		assertEquals("[0]", list.get(3).toString());
		assertEquals("[5]", list.get(4).toString());

		list = cluster.seqList.get(6);
		assertEquals("[4, 6]", list.get(0).toString());
		assertEquals("[3, 8]", list.get(1).toString());
		assertEquals("[1, 7]", list.get(2).toString());
		assertEquals("[0]", list.get(3).toString());
		assertEquals("[2]", list.get(4).toString());
		assertEquals("[5]", list.get(5).toString());

		list = cluster.seqList.get(7);
		assertEquals("[4, 6]", list.get(0).toString());
		assertEquals("[3, 8]", list.get(1).toString());
		assertEquals("[0]", list.get(2).toString());
		assertEquals("[1]", list.get(3).toString());
		assertEquals("[2]", list.get(4).toString());
		assertEquals("[5]", list.get(5).toString());
		assertEquals("[7]", list.get(6).toString());
	
		list = cluster.seqList.get(8);
		assertEquals("[3, 8]", list.get(0).toString());
		assertEquals("[0]", list.get(1).toString());
		assertEquals("[1]", list.get(2).toString());
		assertEquals("[2]", list.get(3).toString());
		assertEquals("[4]", list.get(4).toString());
		assertEquals("[5]", list.get(5).toString());
		assertEquals("[6]", list.get(6).toString());
		assertEquals("[7]", list.get(7).toString());
		
	}

	@Test
	public void testPrintResult() {
		String path = "output" + File.separatorChar + "directCluster.txt";
		try {
			DirectCluster cluster = new DirectCluster();
			cluster.setDirectClusterInput(a);
			cluster.compute();

			cluster.printResult(path);
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
