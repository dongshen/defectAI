package sdong.defectAI.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.rosuda.JRI.Rengine;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import sdong.defectAI.exception.DefectAIException;

public class RUtils {

	private static final Logger LOG = Logger.getLogger(RUtils.class);

	public static Rengine getREngine() throws DefectAIException {
		Rengine re = new Rengine(new String[] { "--vanilla" }, false, null);
		LOG.debug("Rengine created, waiting for R");

		// the engine creates R is a new thread, so we should wait until it's
		// ready
		if (!re.waitForR()) {
			LOG.error("Cannot load R");
			throw new DefectAIException("Cannot load R");
		}

		re.eval("library(clusterCrit)");

		return re;
	}

	public static double evaluateFValue() throws DefectAIException {
		double FValue = 0d;
		Rengine re = getREngine();

		return FValue;
	}

	public static double evaluateS_dbw(Dataset[] clusters) throws DefectAIException {
		double s_dbw = 0d;
		Rengine re = getREngine();
		List<Double> data = new ArrayList<Double>();
		int cluster = clusters.length;
		for (Dataset dataset : clusters) {
			for (int i = 0; i < dataset.size(); i++) {
				Instance instance = dataset.get(i);
				data.addAll(instance.values());
			}
		}
		
		re.assign("data", data.stream().mapToDouble(Double::doubleValue).toArray());

		re.eval("x<-matrix(data, ncol = 3,nrow=150,byrow = TRUE)");
		re.eval("intIdx <- intCriteria(x,cl$cluster,\"s_dbw\")");
		s_dbw = re.eval("intIdx[[\"s_dbs\"]]").asDouble();
		return s_dbw;
	}
}
