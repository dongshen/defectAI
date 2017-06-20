package sdong.defectAI.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testSortMapValueDesend() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("aaa", 1.0);
		map.put("ddd", 10.0);
		map.put("ccc", 5.0);
		map.put("bbb", 3.0);

		List<Entry<String, Double>> list = Utils.sortMapValueDesend(map);
		int sort = 0;
		assertEquals("ddd", list.get(sort).getKey());
		assertEquals(new Double(10.0), list.get(sort).getValue());

		sort++;
		assertEquals("ccc", list.get(sort).getKey());
		assertEquals(new Double(5.0), list.get(sort).getValue());

		sort++;
		assertEquals("bbb", list.get(sort).getKey());
		assertEquals(new Double(3.0), list.get(sort).getValue());

		sort++;
		assertEquals("aaa", list.get(sort).getKey());
		assertEquals(new Double(1.0), list.get(sort).getValue());

	}

}
