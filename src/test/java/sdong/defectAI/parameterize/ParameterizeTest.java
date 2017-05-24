package sdong.defectAI.parameterize;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ParameterizeTest {

	@Test
	public void testParameterizeProcess() {

		Parameterize parameterize = new Parameterize();

		List<String> tokens = new ArrayList<String>(Arrays.asList("82", "15", "82", "7", "82", "15", "82", "8"));
		List<String> result = parameterize.parameterizeProcess(tokens);
		assertEquals("[120, 7, 82, 8]", result.toString());

		tokens = Arrays.asList("82,15,82,15,82,7,84,14,82,15,82,8".split(","));
		result = parameterize.parameterizeProcess(tokens);
		assertEquals("[120, 7, 84, 14, 82, 8]", result.toString());

	}

}
