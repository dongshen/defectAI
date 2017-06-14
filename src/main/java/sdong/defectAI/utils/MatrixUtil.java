package sdong.defectAI.utils;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.tokenizer.TokenizerPython;

public class MatrixUtil {
	public static void printMatrix(double[][] matrix, String path) throws DefectAIException {
		int dimX = matrix.length;
		int dimY = matrix[0].length;
		PrintStream out;
		try {
			out = new PrintStream(path);

			for (int j = 0; j < dimX - 1; j++) {
				out.print(String.format("%10d", j) + " , ");
			}
			out.println(String.format("%10d", dimX - 1));

			for (int i = 0; i < dimY; i++) {
				for (int j = 0; j < dimX - 1; j++) {
					out.print(String.format("%10.6f", matrix[i][j]) + " , ");
				}
				out.println(String.format("%10.6f", matrix[i][dimX - 1]));
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DefectAIException(e);
		}
	}

	public static double[] convertKindToMatrix(List<Integer> kindlist)  {

		double[] matrix = new double[TokenizerPython.maxKindSize];
		for (Integer kind : kindlist) {
			matrix[kind] = matrix[kind] + 1;
		}

		return matrix;
	}

	public static double[][] convertKindListToMatrix(List<List<Integer>> kindlist)  {

		double[][] matrix = new double[TokenizerPython.maxKindSize][kindlist.size()];
		int i = 0;
		for (List<Integer> kinds : kindlist) {
			matrix[i] = convertKindToMatrix(kinds);
			i++;
		}

		return matrix;
	}
}
