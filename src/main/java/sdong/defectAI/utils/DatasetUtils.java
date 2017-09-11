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

	public static Map<String, Integer> getDatasetTypeAsInt(Dataset dataset) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer typeId = 0;
		for (Instance inst : dataset) {
			if (!map.containsKey(inst.classValue())) {
				map.put((String) inst.classValue(), typeId);
				typeId = typeId + 1;
			}
		}
		return map;
	}

	public static Map<String, Integer> getClusterTypeAsInt(Dataset[] clusters) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer typeId = 0;
		for (Dataset dataset : clusters) {
			for (Instance inst : dataset) {
				if (!map.containsKey(inst.classValue())) {
					map.put((String) inst.classValue(), typeId);
					typeId = typeId + 1;
				}
			}
		}
		return map;
	}

	public static int[] convertDatasetToArray(Dataset dataset) {

		int[] array = new int[dataset.size()];
		Map<String, Integer> map = getDatasetTypeAsInt(dataset);
		for (int i = 0; i < dataset.size(); i++) {
			array[i] = map.get(dataset.get(i).classValue());
		}
		return array;
	}

	public static int getClusterSize(Dataset[] clusters) {
		int size = 0;
		for (Dataset dataset : clusters) {
			size = size + dataset.size();
		}
		return size;
	}

	public static int[][] convertClusterToArray(Dataset[] clusters) {
		int size = getClusterSize(clusters);
		int[][] array = new int[2][size];
		Map<String, Integer> map = getClusterTypeAsInt(clusters);
		int i = 0;
		int type = 0;
		for (Dataset dataset : clusters) {
			for (Instance inst : dataset) {
				array[0][i] = map.get(inst.classValue()).intValue();
				array[1][i] = type;
				i = i + 1;
			}
			type = type + 1;
		}
		return array;
	}

	public static List<List<Integer>> convertClusterToList(Dataset[] clusters) {
		List<List<Integer>> lists = new ArrayList<List<Integer>>();

		Map<String, Integer> map = getClusterTypeAsInt(clusters);

		for (Dataset dataset : clusters) {
			List<Integer> list = new ArrayList<Integer>();
			for (Instance inst : dataset) {
				list.add(map.get(inst.classValue()).intValue());
			}
			lists.add(list);
		}
		return lists;
	}
}
