package sdong.defectAI.parameterize;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import net.sourceforge.pmd.cpd.TokenEntry;
import sdong.defectAI.exception.DefectAIException;

public class ParameterizeTest {

	@Test
	public void testGetTokenEntry() {

		String fileName = "sample-python.py";

		Parameterize parameterize = new Parameterize();

		List<TokenEntry> tokens;
		try {
			tokens = parameterize.getTokenEntry(fileName);
			assertEquals(1218, tokens.size());
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
