package sdong.defectAI.preProcess;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokens;
import sdong.defectAI.exception.DefectAIException;
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

	public double[][] process(String fileName) throws DefectAIException {
		double[][] matrix;
		try {
			// get tokens
			Tokens tokens = tokenizer.buildTokenizer(fileName);

			// parameterize
			if (needparameterize == true) {
				tokens = paramaterize.parameterizeTokens(tokens);
			}

			// convert to matrix
			matrix = convertTokensToMatrix(tokens);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new DefectAIException(e.getMessage());
		}
		return matrix;
	}


	public double[][] convertTokensToMatrix(Tokens tokens) {

		Map<Integer, List<TokenEntry>> entrylist = TokenUtils.getTokensEntry(tokens);
		double[][] matrix = new double[entrylist.size()][tokenizer.getMaxTokenKindSize()];

		int line = 0;
		for (Map.Entry<Integer, List<TokenEntry>> entry : entrylist.entrySet()) {
			matrix[line] = this.convertKindToMatrix(TokenUtils.getTokenKind(entry.getValue()));
			line = line + 1;
		}

		return matrix;
	}

	public double[] convertKindToMatrix(List<Integer> kinds) {
		double[] matrix = new double[tokenizer.getMaxTokenKindSize()];

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
	
	
}
