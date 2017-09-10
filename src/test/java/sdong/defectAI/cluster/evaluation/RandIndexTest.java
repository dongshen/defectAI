package sdong.defectAI.cluster.evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;



public class RandIndexTest {
	//private Logger LOG = Logger.getLogger(this.getClass().getName());

	private Logger LOG = Logger.getLogger(this.getClass().getName());
	
	List<List<Integer>> dataset = new ArrayList<List<Integer>>();
	List<List<Integer>> dataset2 = new ArrayList<List<Integer>>();
	
	@Before
	public void prepare(){
		
		//case 1
		dataset.add(Arrays.asList( new Integer[]{1, 1, 1, 1, 1, 2}));
		dataset.add(Arrays.asList( new Integer[]{1, 2, 2, 2, 2, 3})); 
		dataset.add(Arrays.asList( new Integer[]{1, 1, 3, 3, 3})); 
		LOG.debug(dataset.toString());
		
		//case2
		dataset2.add(Arrays.asList( new Integer[]{1, 1, 1, 1, 1, 2,3}));
		dataset2.add(Arrays.asList( new Integer[]{1, 2, 2, 2, 2, 3}));
		dataset2.add(Arrays.asList( new Integer[]{1, 1, 3, 3, 3,2})); 
		//TP+FP = C(7,2)+C(6,2)+C(6,2)=21+15+15=51
		//TP = C(5,2)+C(4,2)+C(2,2)+C(3,2)=10+6+1+3=20
		//FN = C(5,1)+C(5,2)+C(2,1)+C(4,1)+C(4,1)+C(1,1)+C(1,1)+C(3,1)+C(3,1)=5+10+2+4+4+1+1+3+3=33
		//TN = C(19,2)-51-33=87
		
		LOG.debug(dataset2.toString());
	}
	
	@Test
	public void testCalculateIndex() {
		RandIndex index = new RandIndex(dataset);
		
		assertEquals(40, index.getTPAndFP());
		LOG.debug("TP&FP="+index.getTPAndFP());
		
		assertEquals(20, index.getTP());
		LOG.debug("TP="+index.getTP());
		
		assertEquals(20, index.getFP());
		LOG.debug("FP="+index.getFP());
		
		assertEquals(24, index.getFN());
		LOG.debug("FN="+index.getFN());
		
		assertEquals(72, index.getTN());
		LOG.debug("TN="+index.getTN());
		
		assertEquals(new Double(0.6764705882352942), new Double(index.getRI()));
		LOG.debug("RI="+index.getRI());
		
		assertEquals(new Double(0.5), new Double(index.getP()));
		LOG.debug("P="+index.getP());
		
		assertEquals(new Double(0.45454545454545453), new Double(index.getR()));
		LOG.debug("R="+index.getR());
		
		assertEquals(new Double(0.47619047619047616), new Double(index.getF()));
		LOG.debug("F="+index.getF());
		
		assertEquals(new Double(0.7058823529411765), new Double(index.getPurity()));
		LOG.debug("Purify="+index.getPurity());
		
	}
	
	@Test
	public void testCalculateIndex2() {
		RandIndex index = new RandIndex(dataset2);
		
		//assertEquals(51, index.getTPAndFP());
		LOG.debug("TP&FP="+index.getTPAndFP());
		
		//assertEquals(20, index.getTP());
		LOG.debug("TP="+index.getTP());
		
		//assertEquals(20, index.getFP());
		LOG.debug("FP="+index.getFP());
		
		//assertEquals(24, index.getFN());
		LOG.debug("FN="+index.getFN());
		
		//assertEquals(72, index.getTN());
		LOG.debug("TN="+index.getTN());
		
		//assertEquals(new Double(0.6764705882352942), new Double(index.getRI()));
		LOG.debug("RI="+index.getRI());
		
		//assertEquals(new Double(0.5), new Double(index.getP()));
		LOG.debug("P="+index.getP());
		
		//assertEquals(new Double(0.45454545454545453), new Double(index.getR()));
		LOG.debug("R="+index.getR());
		
		//assertEquals(new Double(0.47619047619047616), new Double(index.getF()));
		LOG.debug("F="+index.getF());
		
		//assertEquals(new Double(0.7058823529411765), new Double(index.getPurity()));
		LOG.debug("Purify="+index.getPurity());
		
	}


	@Test 
	public void testCalculateNMI() {
		double nmi = RandIndex.calculateNMI(dataset);
		assertEquals(new Double(0.36456177185718985), new Double(nmi));
	}

	
}
