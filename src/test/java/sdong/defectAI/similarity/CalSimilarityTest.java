package sdong.defectAI.similarity;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.Test;

public class CalSimilarityTest {

	@Test
	public void testSimHashString() {
		String s = "73,82,72,82,72,82";
		String s1 = "73,82,53,82,53,82"; 
		BigInteger hash1;
		try {
			hash1 = CalSimilarity.simHash(s);
			System.out.println(hash1 + "  " + hash1.bitLength());	

			hash1 = CalSimilarity.simHash(s1);
			System.out.println(hash1 + "  " + hash1.bitLength());	

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	@Test
	public void testHammingDistance() {
		fail("Not yet implemented");
	}

}
