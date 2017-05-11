package sdong.defectAI.similarity;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.Test;

public class HammingDistanceTest {

	@Test
	public void testHammingDistance_SimHash() {
		String s1 = "73,82,72,82,72,82";
		String s2 = "73,82,52,82,52,82";
		SimHash hash1, hash2;
		try {
			hash1 = new SimHash(s1);
			assertEquals(new BigInteger("7168736703511516"), hash1.intSimHash);
			assertEquals("0011101111011110110011010010100110110111111011101001100000000000", hash1.strSimHash);

			hash2 = new SimHash(s2);
			assertEquals(new BigInteger("7168045213776604"), hash2.intSimHash);
			assertEquals("0011101101011110110011010010100100110010111011101001100000000000", hash2.strSimHash);

			int distance = HammingDistance.hammingDistance(hash1, hash2);
			assertEquals(4, distance);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHammingDistance_BigInteger() {
		String str1 ="0011101111011110110011010010100110110111111011101001100000000000";
		String str2 ="0011101101011110110011010010100100110010111011101001100000000000";
		int distance = HammingDistance.hammingDistance(str1, str2);
		assertEquals(4, distance);
	}

}
