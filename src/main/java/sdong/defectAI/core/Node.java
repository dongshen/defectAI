package sdong.defectAI.core;

public class Node {

	public Node(int dimension) {
		this.dimension = dimension;
		attributes = new double[dimension];
	}

	private int dimension;

	int label;// label ������¼�����ڵڼ��� cluster
	double[] attributes;
	long seq;
	String classify;
	double distance;

	public Node() {
		attributes = new double[dimension];
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

}
