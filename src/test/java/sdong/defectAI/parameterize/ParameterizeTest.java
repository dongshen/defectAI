package sdong.defectAI.parameterize;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.sourceforge.pmd.cpd.Tokens;
import sdong.defectAI.tokenizer.TokenUtils;
import sdong.defectAI.tokenizer.TokenizerPython;

public class ParameterizeTest {

	@Test
	public void testProcess_file() {

		try {

			String fileName = "sample-python.py";
			TokenizerPython tokenizer = new TokenizerPython();
			Tokens tokens = tokenizer.buildTokenizer(fileName);

			Parameterize parameterize = new Parameterize();
			tokens = parameterize.parameterizeTokens(tokens);
			TokenUtils.printTokensInfo(tokens);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testProcess_String() {

		try {
			TokenizerPython tokenizer = new TokenizerPython();

			List<String> strList = new ArrayList<String>();
			strList.add("logger = logging.getLogger('django.request')");
			strList.add("def __init__(self):");
			strList.add("signals.got_request_exception.send(sender=self.__class__, request=request)");
			strList.add("response = self.handle_uncaught_exception(request, resolver, sys.exc_info())");
			Tokens tokens = tokenizer.buildTokenizer(strList, "test");
			TokenUtils.printTokensInfo(tokens);
			
			Parameterize parameterize = new Parameterize();
			tokens = parameterize.parameterizeTokens(tokens);
			Map<Integer, List<Integer>> mapkind = TokenUtils.getTokensKind(tokens);
			assertEquals("[82, 30, 199, 7, 104, 8]", mapkind.get(1).toString());
			assertEquals("[63, 199, 7, 82, 8, 16]", mapkind.get(2).toString());
			assertEquals("[199, 7, 82, 30, 82, 14, 82, 30, 82, 8]", mapkind.get(3).toString());
			assertEquals("[82, 30, 199, 7, 82, 14, 82, 14, 199, 7, 8, 8]", mapkind.get(4).toString());
			Map<Integer, List<String>> mapValue = TokenUtils.getTokensValue(tokens);
			assertEquals("[logger, =, logging.getLogger, (, 'django.request', )]", mapValue.get(1).toString());
			assertEquals("[def, __init__, (, self, ), :]", mapValue.get(2).toString());
			assertEquals(
					"[signals.got_request_exception.send, (, sender, =, self.__class__, ,, request, =, request, )]",
					mapValue.get(3).toString());
			assertEquals(
					"[response, =, self.handle_uncaught_exception, (, request, ,, resolver, ,, sys.exc_info, (, ), )]",
					mapValue.get(4).toString());

			TokenUtils.printTokensInfo(tokens);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testParameterizeProcess() {

		Parameterize parameterize = new Parameterize();

		List<String> tokens = Arrays.asList("82,15,82,7,82,15,82,8".split(","));
		List<String> result = parameterize.parameterizeProcess(tokens);
		assertEquals("[120, 7, 82, 8]", result.toString());

		tokens = Arrays.asList("82,15,82,15,82,7,84,14,82,15,82,8".split(","));
		result = parameterize.parameterizeProcess(tokens);
		assertEquals("[120, 7, 84, 14, 82, 8]", result.toString());

	}

}
