package sdong.defectAI.similarity;

/**
 * Function: simHash �ж��ı����ƶȣ���ʾ����֧������<br/>
 * date: 2013-8-6 ����1:11:48 <br/>
 * @author june
 * @version 0.1
 */
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class SimHashRef {

	private String tokens;

	private BigInteger intSimHash;

	private String strSimHash;

	private int hashbits = 64;

	public SimHash(String tokens) throws IOException {
			this.tokens = tokens;
			this.intSimHash = this.simHash();
		}

	public SimHash(String tokens, int hashbits) throws IOException {
			this.tokens = tokens;
			this.hashbits = hashbits;
			this.intSimHash = this.simHash();
		}

	HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

	public BigInteger simHash() throws IOException {
		// ������������/����
		int[] v = new int[this.hashbits];
		// Ӣ�ķִ�
		// StringTokenizer stringTokens = new StringTokenizer(this.tokens);
		// while (stringTokens.hasMoreTokens()) {
		// String temp = stringTokens.nextToken();
		// }
		// 1�����ķִʣ��ִ������� IKAnalyzer3.2.8 ��������ʾʹ�ã��°� API �ѱ仯��
		StringReader reader = new StringReader(this.tokens);
		// ��Ϊtrueʱ���ִ����������ʳ��з�
		IKSegmentation ik = new IKSegmentation(reader, true);
		Lexeme lexeme = null;
		String word = null;
		String temp = null;
		while ((lexeme = ik.next()) != null) {
			word = lexeme.getLexemeText();
			// ע��ͣ�ôʻᱻ�ɵ�
			// System.out.println(word);
			// 2����ÿһ���ִ�hashΪһ��̶����ȵ�����.���� 64bit ��һ������.
			BigInteger t = this.hash(word);
			for (int i = 0; i < this.hashbits; i++) {
				BigInteger bitmask = new BigInteger("1").shiftLeft(i);
				// 3������һ������Ϊ64����������(����Ҫ����64λ������ָ��,Ҳ��������������),
				// ��ÿһ���ִ�hash������н����ж�,�����1000...1,��ô����ĵ�һλ��ĩβһλ��1,
				// �м��62λ��һ,Ҳ����˵,��1��1,��0��1.һֱ�������еķִ�hash����ȫ���ж����.
				if (t.and(bitmask).signum() != 0) {
					// �����Ǽ��������ĵ�������������������
					// ����ʵ��ʹ������Ҫ +- Ȩ�أ������Ƶ�������Ǽ򵥵� +1/-1��
					v[i] += 1;
				} else {
					v[i] -= 1;
				}
			}
		}

		BigInteger fingerprint = new BigInteger("0");
		StringBuffer simHashBuffer = new StringBuffer();
		for (int i = 0; i < this.hashbits; i++) {
			// 4��������������ж�,����0�ļ�Ϊ1,С�ڵ���0�ļ�Ϊ0,�õ�һ�� 64bit ������ָ��/ǩ��.
			if (v[i] >= 0) {
				fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
				simHashBuffer.append("1");
			} else {
				simHashBuffer.append("0");
			}
		}
		this.strSimHash = simHashBuffer.toString();
		System.out.println(this.strSimHash + " length " + this.strSimHash.length());
		return fingerprint;
	}

	private BigInteger hash(String source) {
		if (source == null || source.length() == 0) {
			return new BigInteger("0");
		} else {
			char[] sourceArray = source.toCharArray();
			BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
			BigInteger m = new BigInteger("1000003");
			BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
			for (char item : sourceArray) {
				BigInteger temp = BigInteger.valueOf((long) item);
				x = x.multiply(m).xor(temp).and(mask);
			}
			x = x.xor(new BigInteger(String.valueOf(source.length())));
			if (x.equals(new BigInteger("-1"))) {
				x = new BigInteger("-2");
			}
			return x;
		}
	}

	public int hammingDistance(SimHash other) {

		BigInteger x = this.intSimHash.xor(other.intSimHash);
		int tot = 0;

		// ͳ��x�ж�����λ��Ϊ1�ĸ���
		// �������룬һ������������ȥ1����ô��������Ǹ�1�������Ǹ�1�����������ȫ�����ˣ�
		// �԰ɣ�Ȼ��n&(n-1)���൱�ڰѺ����������0��
		// ���ǿ�n�������ٴ������Ĳ�����OK�ˡ�

		while (x.signum() != 0) {
			tot += 1;
			x = x.and(x.subtract(new BigInteger("1")));
		}
		return tot;
	}

	public int getDistance(String str1, String str2) {
		int distance;
		if (str1.length() != str2.length()) {
			distance = -1;
		} else {
			distance = 0;
			for (int i = 0; i < str1.length(); i++) {
				if (str1.charAt(i) != str2.charAt(i)) {
					distance++;
				}
			}
		}
		return distance;
	}

	public List subByDistance(SimHash simHash, int distance) {
		// �ֳɼ��������
		int numEach = this.hashbits / (distance + 1);
		List characters = new ArrayList();

		StringBuffer buffer = new StringBuffer();

		int k = 0;
		for (int i = 0; i < this.intSimHash.bitLength(); i++) {
			// ���ҽ���������ָ����λʱ������ true
			boolean sr = simHash.intSimHash.testBit(i);

			if (sr) {
				buffer.append("1");
			} else {
				buffer.append("0");
			}

			if ((i + 1) % numEach == 0) {
				// ��������תΪBigInteger
				BigInteger eachValue = new BigInteger(buffer.toString(), 2);
				System.out.println("----" + eachValue);
				buffer.delete(0, buffer.length());
				characters.add(eachValue);
			}
		}

		return characters;
	}

	public static void main(String[] args) throws IOException {
		String s = "��ͳ�� hash �㷨ֻ����ԭʼ���ݾ������������ӳ��Ϊһ��ǩ��ֵ��" + "ԭ�����൱��α����������㷨������������ǩ���������ȣ�˵��ԭʼ������һ���� �� ������ȵģ�"
				+ "�������ȣ�����˵��ԭʼ���ݲ�����⣬�����ṩ�κ���Ϣ����Ϊ��ʹԭʼ����ֻ���һ���ֽڣ�" + "��������ǩ��Ҳ�ܿ��ܲ�𼫴󡣴�������� ���� ˵��Ҫ���һ�� hash �㷨��"
				+ "�����Ƶ����ݲ�����ǩ��Ҳ������Ǹ�Ϊ���ѵ�������Ϊ����ǩ��ֵ�����ṩԭʼ�����Ƿ���ȵ���Ϣ�⣬" + "���ܶ����ṩ����ȵ� ԭʼ���ݵĲ���̶ȵ���Ϣ��";
		SimHash hash1 = new SimHash(s, 64);
		System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
		// ���� �������� �� 3 ���ڵĸ���ǩ���� hash ֵ
		hash1.subByDistance(hash1, 3);

		// ɾ���׾仰���������������Ŵ�
		s = "ԭ�����൱��α����������㷨������������ǩ���������ȣ�˵��ԭʼ������һ���� �� ������ȵģ�" + "�������ȣ�����˵��ԭʼ���ݲ�����⣬�����ṩ�κ���Ϣ����Ϊ��ʹԭʼ����ֻ���һ���ֽڣ�"
				+ "��������ǩ��Ҳ�ܿ��ܲ�𼫴󡣴�������� ���� ˵��Ҫ���һ�� hash �㷨��" + "�����Ƶ����ݲ�����ǩ��Ҳ������Ǹ�Ϊ���ѵ�������Ϊ����ǩ��ֵ�����ṩԭʼ�����Ƿ���ȵ���Ϣ�⣬"
				+ "����1���ܶ����ṩ����ȵ� ԭʼ���ݵĲ���̶ȵ���Ϣ��";
		SimHash hash2 = new SimHash(s, 64);
		System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
		hash1.subByDistance(hash2, 3);

		// �׾�ǰ���һ�仰���������ĸ����Ŵ�
		s = "imhash�㷨��������һ�������������һ�� f λ��ǩ��ֵ��Ϊ�˳������㣬" + "�����������һ���ĵ����������ϣ�ÿ��������һ����Ȩ�ء�"
				+ "��ͳ����4�� hash �㷨ֻ����ԭʼ���ݾ������������ӳ��Ϊһ��ǩ��ֵ��" + "ԭ������β����ж����3�൱��α����������㷨������������ǩ���������ȣ�"
				+ "˵��ԭʼ������һ���� �� ������ȵģ��������ȣ�����˵��ԭʼ���ݲ�����⣬�����ṩ�κ���Ϣ��" + "��Ϊ��ʹԭʼ����ֻ���һ���ֽڣ���������ǩ��Ҳ�ܿ��ܲ�𼫴󡣴�������� ���� ˵��"
				+ "Ҫ���һ�� hash �㷨�������Ƶ����ݲ�����ǩ��Ҳ������Ǹ�Ϊ���ѵ�������Ϊ����ǩ��ֵ�����ṩԭʼ" + "�����Ƿ���ȵ���Ϣ�⣬����1���ܶ����ṩ����ȵ� ԭʼ��������2���ݵĲ���̶ȵ���Ϣ��";
		SimHash hash3 = new SimHash(s, 64);
		System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitCount());
		hash1.subByDistance(hash3, 3);

		System.out.println("============================");

		int dis = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
		System.out.println(hash1.hammingDistance(hash2) + " " + dis);
		// ���ݸ볲ԭ��Ҳ�ɳ���ԭ���������ѧ�����������ǩ���ĺ��������� 3 ���ڣ����Ǳ���һ��ǩ��subByDistance()��ȫ��ͬ��
		int dis2 = hash1.getDistance(hash1.strSimHash, hash3.strSimHash);
		System.out.println(hash1.hammingDistance(hash3) + " " + dis2);
	}
}