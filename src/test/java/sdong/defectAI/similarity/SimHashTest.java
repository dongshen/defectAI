package sdong.defectAI.similarity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

public class SimHashTest {

	@Test
	public void testSimHashString() {
		String s1 = "73,82,72,82,72,82";
		String s2 = "73,82,53,82,53,82";
		SimHash hash1, hash2;
		try {
			hash1 = new SimHash(s1);
			assertEquals(new BigInteger("7168736703511516"), hash1.intSimHash);
			assertEquals("0011101111011110110011010010100110110111111011101001100000000000", hash1.strSimHash);

			hash2 = new SimHash(s2);
			assertEquals(new BigInteger("7168045213776604"), hash2.intSimHash);
			assertEquals("0011101111011110110011010010100110110111111011101001100000000000", hash2.strSimHash);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSubByDistance() {
		String s1 = "73,82,72,82,72,82";
		String s2 = "73,82,53,82,53,82";
		SimHash hash1,hash2;
		try {
			hash1 = new SimHash(s1);
			assertEquals(new BigInteger("7168736703511516"), hash1.intSimHash);
			List<BigInteger> list1 = hash1.subByDistance(hash1, 3);
			assertEquals(new BigInteger("15326"), list1.get(0));
			assertEquals(new BigInteger("52521"), list1.get(1));
			assertEquals(new BigInteger("47086"), list1.get(2));
			
			hash2 = new SimHash(s2);
			assertEquals(new BigInteger("7168045213776604"), hash2.intSimHash);
			List<BigInteger> list2 = hash1.subByDistance(hash2, 3);
			assertEquals(new BigInteger("15198"), list2.get(0));
			assertEquals(new BigInteger("52521"), list2.get(1));
			assertEquals(new BigInteger("13038"), list2.get(2));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
