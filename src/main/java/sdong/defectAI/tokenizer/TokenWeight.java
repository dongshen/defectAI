package sdong.defectAI.tokenizer;

public class TokenWeight {
	public static final double WEIGHT_KEYWORD = 20.0;
	public static double getWeight(int kind){
		double weight = 1;
		if(kind >= 56 && kind <= 81){
			weight = WEIGHT_KEYWORD;
		}
		return weight;
	}
}
