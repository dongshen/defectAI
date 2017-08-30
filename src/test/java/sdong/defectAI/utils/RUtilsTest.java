package sdong.defectAI.utils;

import java.util.Enumeration;

import org.junit.Test;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

public class RUtilsTest {
	@Test
	public void testR() {
		// Start Rengine.
		Rengine re = new Rengine(new String[] { "--vanilla" }, false, null);
		System.out.println("Rengine created, waiting for R");

		// the engine creates R is a new thread, so we should wait until it's
		// ready
		if (!re.waitForR()) {
			System.out.println("Cannot load R");
			return;
		}
		
		re.eval("library(clusterCrit)");

		re.eval("data(iris)", false);
		REXP x = re.eval("iris");

		RVector v = x.asVector();
		System.out.println("has names:");
		for (Enumeration e = v.getNames().elements(); e.hasMoreElements();) {
			System.out.println(e.nextElement());
		}

		// assertEquals("aaa", list.get(sort).getKey());
		// assertEquals(new Double(1.0), list.get(sort).getValue());

	}
}
