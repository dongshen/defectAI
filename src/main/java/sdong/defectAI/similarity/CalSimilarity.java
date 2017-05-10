package sdong.defectAI.similarity;

import java.io.IOException;
import java.math.BigInteger;

public class CalSimilarity {


	public static BigInteger simHash(String tokens) throws IOException {
		int hashbits = 64;
		return simHash(tokens, hashbits);

	}

	public static BigInteger simHash(String tokens, int hashbits) throws IOException {

		// ������������/����
		int[] v = new int[hashbits];

		String word = null;
		String[] words = tokens.split(",");

		for (String splitword : words) {
			word = splitword;
			// System.out.println(word);
			// 2����ÿһ���ִ�hashΪһ��̶����ȵ�����.���� 64bit ��һ������.
			BigInteger t = hash(word, hashbits);
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
		String strSimHash = simHashBuffer.toString();
		System.out.println(strSimHash + " length " + strSimHash.length());
		return fingerprint;
	}

	private static BigInteger hash(String source, int hashbits) {
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

	public static int hammingDistance(BigInteger one, BigInteger other) {

		BigInteger x = one.xor(other);
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

	public static int getDistance(String str1, String str2) {
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


}