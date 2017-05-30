package sdong.defectAI.utils;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MatrixUtil {
	public static void printMatrix(double[][] matrix, String path) throws FileNotFoundException {
		int dimX = matrix.length;
		int dimY = matrix[0].length;
		PrintStream out = new PrintStream(path);
		for (int j = 0; j < dimX; j++) {
			out.print(String.format("%10d", j));
			if (j < dimX - 1) {
				out.print(" , ");
			}
		}
		out.println();

		for (int i = 0; i < dimY; i++) {
			for (int j = 0; j < dimX; j++) {
				out.print(String.format("%10.6f", matrix[i][j]));
				if (j < dimX - 1) {
					out.print(" , ");
				}
			}
			out.println();
		}
		out.close();
	}
}
