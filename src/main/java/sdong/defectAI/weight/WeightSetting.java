package sdong.defectAI.weight;

import java.util.List;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.InstanceTools;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.FileUtil;

public class WeightSetting extends DenseInstance {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3769091931057072704L;

	
	public WeightSetting(double[] att) {
		super(att);
		// TODO Auto-generated constructor stub
	}
	

	public void weightSettingByFile(String file) {
		List<String> lines;
		try {
			lines = FileUtil.readFileByLine(file);

			String[] str = lines.get(0).split(",");
			for (int i = 0; i < str.length; i++) {
				super.put(i, Double.parseDouble(str[i].trim()));
			}
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static final double WEIGHT_KEYWORD = 20.0;
	public static final double WEIGHT_DEFINE_FUNCTION = 20.0;
	public static final int DEFINE_FUNCTION = 199;

	public static double getWeight(int kind) {
		double weight = 1;
		if (kind >= 56 && kind <= 81) {
			weight = WEIGHT_KEYWORD;
		} else if (kind == DEFINE_FUNCTION) {
			weight = WEIGHT_DEFINE_FUNCTION;
		}
		return weight;
	}

}
