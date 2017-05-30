package sdong.defectAI.tokenizer;

public class TokenWeight {
	public static final double WEIGHT_KEYWORD = 20.0;
	public static final double WEIGHT_DEFINE_FUNCTION = 20.0;
	public static final int DEFINE_FUNCTION = 199;
	public static double getWeight(int kind){
		double weight = 1;
		if(kind >= 56 && kind <= 81){
			weight = WEIGHT_KEYWORD;
		}else if(kind == DEFINE_FUNCTION){
			weight = WEIGHT_DEFINE_FUNCTION;
		}
		return weight;
	}
}
