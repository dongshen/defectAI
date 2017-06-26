package sdong.defectAI.cluster.evaluation;

import org.junit.Test;

public class ClusterEvaluationTest {

	@Test
	public void test() {
		int[] A = { 1, 3, 3, 3, 3, 3, 3, 2, 1, 0, 2, 0, 2, 0, 2, 1, 1, 0, 1, 1 };
		int[] B = { 2, 2, 0, 0, 0, 3, 2, 2, 3, 1, 3, 1, 0, 1, 2, 1, 0, 1, 3, 3 };

		double purity = ClusterEvaluation.Purity(A, B);
		System.out.println("purity\t\t" + purity);
		System.out.println("Pre\t\t" + ClusterEvaluation.Precision(A, B));
		System.out.println("Recall\t\t" + ClusterEvaluation.Recall(A, B));
		System.out.println("RI(Accuracy)\t\t" + ClusterEvaluation.RI(A, B));
		System.out.println("Fvalue\t\t" + ClusterEvaluation.F_score(A, B));
		System.out.println("NMI\t\t" + ClusterEvaluation.NMI(A, B));
	}

}
