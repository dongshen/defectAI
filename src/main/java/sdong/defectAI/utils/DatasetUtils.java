package sdong.defectAI.utils;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.tokenizer.TokenUtils;

public class DatasetUtils {
	public static List<String> getDatasetCluster(Dataset[] clusters) {

		List<String> clusterlist = new ArrayList<String>();
		String index = "";
		for (int i = 0; i < clusters.length; i++) {
			index = "";
			for (int j = 0; j < clusters[i].size() - 1; j++) {
				index = index + clusters[i].get(j).getID() + ",";
			}
			index = index + clusters[i].get(clusters[i].size() - 1);
			clusterlist.add(index);
		}
		return clusterlist;
	}

	public static void exportDataset(Dataset[] clusters, String path) throws DefectAIException {

		try {
			PrintStream out = new PrintStream(path);

			for (int i = 0; i < clusters.length; i++) {
				out.println("Cluster " + (i + 1) + " : ");
				for (int j = 0; j < clusters[i].size() - 1; j++) {
					out.print(clusters[i].get(j).getID() + ", ");
				}
				out.println(clusters[i].get(clusters[i].size() - 1));
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DefectAIException(e);
		}
	}

	public static void exportDatasetWithValue(Dataset[] clusters, String valuepath, String outpath)
			throws DefectAIException {

		try {
			List<String> valuelist = TokenUtils.getTokensValueFromFile(valuepath);

			PrintStream out = new PrintStream(outpath);
			int index;
			for (int i = 0; i < clusters.length; i++) {
				out.println("Cluster " + (i + 1) + " : ");
				out.println("--------------------------");
				for (int j = 0; j < clusters[i].size() - 1; j++) {
					index = clusters[i].get(j).getID();
					out.println(index + ": " + valuelist.get(index));
				}
				out.println("--------------------------");
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DefectAIException(e);
		}
	}

	public static void exportDatasetWithCluster(Dataset[] clusters, String outpath) throws DefectAIException {

		try {
			PrintStream out = new PrintStream(outpath);
			for (int i = 0; i < clusters.length; i++) {
				for (Instance inst : clusters[i]) {
					out.print(string(inst, ","));
					if (inst.classValue() != null) {
						out.print("," + inst.classValue());
						out.println("," + i);
					} else {
						out.println(i);
					}
				}
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DefectAIException(e);
		}
	}

	private static String string(Instance inst, String sep) {
		StringBuffer out = new StringBuffer();
		if (inst instanceof SparseInstance) {
			for (Integer index : inst.keySet()) {
				if (out.length() != 0)
					out.append(sep + index + ":" + inst.value(index));
				else
					out.append(index + ":" + inst.value(index));

			}
		} else {
			out.append(inst.value(0));
			for (int i = 1; i < inst.noAttributes(); i++)
				out.append(sep + inst.value(i));

		}

		return out.toString();
	}

	public static String[][] checkRate(Dataset[] clusters, Dataset data) {
		String[][] clusterrate = new String[clusters.length][4];
		Map<String, List<String>> expectlist = new HashMap<String, List<String>>();
		String key;
		List<String> clusterlist;
		for (Instance inst : data) {
			key = (String) inst.classValue();
			clusterlist = expectlist.get(key);
			if (clusterlist == null) {
				clusterlist = new ArrayList<String>();
				expectlist.put(key, clusterlist);
			}
			clusterlist.add(inst.getID() + "");
		}

		// rate
		String[] rate;
		Result_verify iris = new Result_verify();
		iris.setResultmap(expectlist);
		int i = 0;
		for (Dataset list : clusters) {
			rate = iris.checkMatchRate(getDatasetIndexList(list));
			System.out.println("Cluster " + i + " match " + rate[0] + " rate: " + rate[1] + ", not match rate: "
					+ rate[2] + " size: " + rate[3]);
			clusterrate[i] = rate;
			i = i + 1;
		}

		return clusterrate;
	}

	public static List<String> getDatasetIndexList(Dataset dataset) {
		List<String> clusterlist = new ArrayList<String>();
		for (Instance inst : dataset) {
			clusterlist.add(inst.getID() + "");
		}
		return clusterlist;
	}

	public static void multiplyInstance(Dataset dataset, Instance instance) {
		for (Instance data : dataset) {
			for (int i = 0; i < data.noAttributes(); i++) {
				data.put(i, data.value(i) * instance.value(i));
			}
		}
	}
}
