package sdong.defectAI.utils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import org.junit.Test;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import sdong.defectAI.cluster.KMeansPlus;
import sdong.defectAI.exception.DefectAIException;

public class RUtilsTest {
	@Test
	public void testR() {
		// Start Rengine.
		Rengine re;
		try {
			re = RUtils.getREngine();

			re.eval("data(iris)", false);
			REXP x = re.eval("iris");

			RVector v = x.asVector();
			System.out.println("has names:");
			for (Enumeration e = v.getNames().elements(); e.hasMoreElements();) {
				System.out.println(e.nextElement());
			}

			// assertEquals("aaa", list.get(sort).getKey());
			// assertEquals(new Double(1.0), list.get(sort).getValue());
		} catch (DefectAIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void testEvaluateS_dbw() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			RUtils.evaluateS_dbw(clusters);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
