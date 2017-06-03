package sdong.defectAI.preProcess;

import java.io.IOException;
import java.util.List;

import net.sourceforge.pmd.cpd.Tokens;
import sdong.defectAI.parameterize.Parameterize;
import sdong.defectAI.tokenizer.AbstractTokenizer;
import sdong.defectAI.tokenizer.TokenUtils;
import sdong.defectAI.tokenizer.TokenWeight;
import sdong.defectAI.tokenizer.TokenizerPython;

public class PreProcess {

	AbstractTokenizer tokenizer;
	boolean needparameterize = true;
	Parameterize paramaterize = new Parameterize();
	boolean needWeight = true;

	public PreProcess() {
		tokenizer = new TokenizerPython();
	}

	public double[][] process(String fileName) throws IOException {
		return convertTokensListToMatrix(getTokensFromFile(fileName));
	}

	public double[][] process(List<List<String>> list, String fileName) throws IOException {
		return convertTokensListToMatrix(getTokensFromStrList(list, fileName));
	}

	private List<Tokens> getTokensFromFile(String fileName) throws IOException {
		// get tokens
		return tokenizer.buildTokenizerList(fileName);
	}

	private List<Tokens> getTokensFromStrList(List<List<String>> list, String fileName) throws IOException {
		// get tokens
		return tokenizer.buildTokenizerList(list, fileName);
	}

	public double[][] convertTokensListToMatrix(List<Tokens> tokenslist) {

		double[][] matrix = new double[tokenslist.size()][tokenizer.getMaxTokenKindSize()];

		for (int i = 0; i < tokenslist.size(); i++) {
			matrix[i] = converTokensToMatrix(tokenslist.get(i));
		}

		return matrix;
	}

	public double[] converTokensToMatrix(Tokens tokens) {

		double[] matrix = new double[tokenizer.getMaxTokenKindSize()];

		// parameterize
		if (needparameterize == true) {
			tokens = paramaterize.parameterizeTokens(tokens);
		}

		List<Integer> kinds = TokenUtils.getTokensKind(tokens);
		for (Integer kind : kinds) {
			if (kind != 0) {
				matrix[kind] = matrix[kind] + 1;
			}
		}

		if (needWeight == true) {
			for (int i = 0; i < tokenizer.getMaxTokenKindSize(); i++) {
				if (matrix[i] != 0.0) {
					matrix[i] = matrix[i] * TokenWeight.getWeight(i);
				}
			}
		}

		return matrix;
	}

	public boolean isNeedWeight() {
		return needWeight;
	}

	public void setNeedWeight(boolean needWeight) {
		this.needWeight = needWeight;
	}

	public boolean isNeedparameterize() {
		return needparameterize;
	}

	public void setNeedparameterize(boolean needparameterize) {
		this.needparameterize = needparameterize;
	}

}
