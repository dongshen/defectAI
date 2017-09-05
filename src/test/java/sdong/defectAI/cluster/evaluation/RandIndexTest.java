package sdong.defectAI.cluster.evaluation;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

public class RandIndexTest {
	private Logger LOG = Logger.getLogger(this.getClass().getName());
	//private static final Logger LOG = Logger.getLogger(FileUtil.class);

	@Test
	public void testCalculateIndex() {
		List<List<Integer>> dataset = getTestData();
	}

	@Test 
	public void testCalculateNMI() {
		fail("Not yet implemented");
	}

	private List<List<Integer>> getTestData(){
		List<List<Integer>> lists = new ArrayList<List<Integer>>();
		lists.add(Arrays.asList( new Integer[]{1, 1, 1, 1, 1, 2}));
		lists.add(Arrays.asList( new Integer[]{1, 2, 2, 2, 2, 3})); 
		lists.add(Arrays.asList( new Integer[]{1, 1, 3, 3, 3})); 
		LOG.info(lists.toString());
		return lists;
	}

}
