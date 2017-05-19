package sdong.defectAI.cluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class DirectClusterTest {

	@Test
	public void testSetDirectClusterInput() {
		double a[][] = {{0,0,0,0,0,0,0,0,0},
						{1.5,0,0,0,0,0,0,0,0},
						{3.1,2.7,0,0,0,0,0,0,0},
						{2.1,1.4,1.2,0,0,0,0,0,0},
						{5.8,6.0,3.6,4.7,0,0,0,0,0},
						{4.7,4.4,1.8,2.9,1.7,0,0,0,0},
						{5.7,5.5,2.9,4.0,0.8,1.0,0,0,0},
						{1.3,0.9,2.2,1.2,5.1,3.9,5.0,0,0},
						{2.6,1.6,1.2,0.5,4.8,3.0,3.3,1.4,0}} ;
		DirectCluster cluster = new DirectCluster();
		cluster.setDirectClusterInput(a);
		cluster.printResult();
		
		assertEquals("Min[8,3]=0.5",cluster.category.get(0));
		assertEquals("Min[6,4]=0.8",cluster.category.get(1));
		assertEquals("Min[7,1]=0.9",cluster.category.get(2));
		assertEquals("Min[3,2]=1.2",cluster.category.get(3));
		assertEquals("Min[1,0]=1.5",cluster.category.get(4));
		assertEquals("Min[5,4]=1.7",cluster.category.get(5));
		assertEquals("Min[2,0]=3.1",cluster.category.get(6));
		assertEquals("Min[4,0]=5.8",cluster.category.get(7));
	}

	@Test
	public void testPrintResult() {
		fail("Not yet implemented");
	}

}
