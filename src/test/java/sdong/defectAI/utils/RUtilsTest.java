package sdong.defectAI.utils;

import static org.junit.Assert.assertEquals;
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
		// Start R engine.
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
			double s_dbw = RUtils.evaluateS_dbw(clusters);
			assertEquals(new Double(0.1666394273608567), new Double(s_dbw));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEvaluateFValue() {
		Dataset data;
		try {
			data = FileHandler.loadDataset(new File("input/iris.data"), 4, ",");

			KMeansPlus km = new KMeansPlus();
			Dataset[] clusters = km.cluster(data);
			double FValue = RUtils.evaluateFValue(clusters);
			assertEquals(new Double(0.8206565380096436), new Double(FValue));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
