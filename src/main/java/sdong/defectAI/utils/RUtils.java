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

	public static double externalIndices(int[] part1, int[] part2, String indices) throws DefectAIException {
		double extIdx = 0d;

		Rengine re = getREngine();
		re.assign("part1", part1);
		re.assign("part2", part2);
		//re.eval("part1 <- as.integer(part1)");
		//re.eval("part2 <- as.integer(part2)");
		re.eval("extIdx <- extCriteria(part1,part2,\"" + indices + "\")");

		extIdx = re.eval("extIdx[[\""+indices+"\"]]").asDouble();
		//LOG.debug(indices + "=" + extIdx);
		return extIdx;
	}

	public static double evaluateFValue(Dataset[] clusters) throws DefectAIException {
		double FValue = 0d;

		int[][] data = DatasetUtils.convertClusterToArray(clusters);

		FValue = externalIndices(data[0], data[1], "czekanowski_dice");
		LOG.debug("FValue=" + FValue);
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
