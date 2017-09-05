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
		int ncol = clusters[0].get(0).noAttributes();
		int nrow = 0;
		for (Dataset dataset : clusters) {
			nrow = nrow + dataset.size();
		}

		int[] clusterTag = new int[nrow];

		List<Double> data = new ArrayList<Double>();

		int size = 0;
		for (int j = 0; j < clusters.length; j++) {
			Dataset dataset = clusters[j];
			for (int i = 0; i < dataset.size(); i++) {
				Instance instance = dataset.get(i);
				data.addAll(instance.values());
				clusterTag[size] = j;
				size = size + 1;
			}
		}

		re.assign("data", data.stream().mapToDouble(Double::doubleValue).toArray());
		re.assign("c", clusterTag);
		re.eval("x<-matrix(data, ncol = " + ncol + ",nrow=" + nrow + ",byrow = TRUE)");
		re.eval("intIdx <- intCriteria(x,c,\"s_dbw\")");
		s_dbw = re.eval("intIdx[[\"s_dbw\"]]").asDouble();
		LOG.debug("s_dwb=" + s_dbw);
		return s_dbw;
	}
}
