package sdong.defectAI.utils;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MatrixUtil {
	public static void printMatrix(double[][] matrix, String path) throws FileNotFoundException {
		int dimX = matrix.length;
		int dimY = matrix[0].length;
		PrintStream out = new PrintStream(path);
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
	}
}
