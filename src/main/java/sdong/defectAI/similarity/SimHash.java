package sdong.defectAI.similarity;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SimHash {

	private String tokens;

	public BigInteger intSimHash;

	public String strSimHash;

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

	public BigInteger simHash() throws IOException {

		// ������������/����
		int[] v = new int[hashbits];

		String word = null;
		String[] words = tokens.split(",");

		for (String splitword : words) {
			word = splitword;
			// System.out.println(word);
			// 2����ÿһ���ִ�hashΪһ��̶����ȵ�����.���� 64bit ��һ������.
			BigInteger t = hash(word);
			for (int i = 0; i < hashbits; i++) {
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
		for (int i = 0; i < hashbits; i++) {
			// 4��������������ж�,����0�ļ�Ϊ1,С�ڵ���0�ļ�Ϊ0,�õ�һ�� 64bit ������ָ��/ǩ��.
			if (v[i] >= 0) {
				fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
				simHashBuffer.append("1");
			} else {
				simHashBuffer.append("0");
			}
		}

		strSimHash = simHashBuffer.toString();
		// System.out.println(strSimHash + " length " + strSimHash.length());
		return fingerprint;
	}

	private BigInteger hash(String source) {
		if (source == null || source.length() == 0) {
			return new BigInteger("0");
		} else {
			char[] sourceArray = source.toCharArray();
			BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
			BigInteger m = new BigInteger("1000003");
			BigInteger mask = new BigInteger("2").pow(hashbits).subtract(new BigInteger("1"));
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

	public List<BigInteger> subByDistance(SimHash simHash, int distance) {
		// �ֳɼ��������
		int numEach = this.hashbits / (distance + 1);
		List<BigInteger> characters = new ArrayList<BigInteger>();

		StringBuffer buffer = new StringBuffer();

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
				//System.out.println("----" + eachValue);
				buffer.delete(0, buffer.length());
				characters.add(eachValue);
			}
		}

		return characters;
	}

}