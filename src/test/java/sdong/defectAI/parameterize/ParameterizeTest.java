package sdong.defectAI.parameterize;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.sourceforge.pmd.cpd.Tokens;

public class ParameterizeTest {

	@Test
	public void testProcess_file() {

		try {
			Parameterize parameterize = new Parameterize(true);
			String fileName = "sample-python.py";

			Tokens tokens = parameterize.process(fileName);
			parameterize.tokenizer.printTokensInfo(tokens);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testProcess_String() {

		try {
			Parameterize parameterize = new Parameterize(true);
			List<String> strList = new ArrayList<String>();
			strList.add("logger = logging.getLogger('django.request')");
			strList.add("def __init__(self):");
			strList.add("signals.got_request_exception.send(sender=self.__class__, request=request)");
			strList.add("response = self.handle_uncaught_exception(request, resolver, sys.exc_info())");

			Tokens tokens = parameterize.process(strList,"filename");
			
			Map<Integer, List<String>> mapkind = parameterize.tokenizer.getTokensKind(tokens);
			assertEquals("[82, 30, 200, 7, 104, 8]", mapkind.get(1).toString());
			assertEquals("[63, 200, 7, 82, 8, 16]", mapkind.get(2).toString());
			assertEquals("[200, 7, 82, 30, 82, 14, 82, 30, 82, 8]", mapkind.get(3).toString());
			assertEquals("[82, 30, 200, 7, 82, 14, 82, 14, 200, 7, 8, 8]", mapkind.get(4).toString());
			Map<Integer, List<String>> mapValue = parameterize.tokenizer.getTokensValue(tokens);
			assertEquals("[logger, =, logging.getLogger, (, 'django.request', )]", mapValue.get(1).toString());
			assertEquals("[def, __init__, (, self, ), :]", mapValue.get(2).toString());
			assertEquals("[signals.got_request_exception.send, (, sender, =, self.__class__, ,, request, =, request, )]", mapValue.get(3).toString());
			assertEquals("[response, =, self.handle_uncaught_exception, (, request, ,, resolver, ,, sys.exc_info, (, ), )]", mapValue.get(4).toString());
			
			
			parameterize.tokenizer.printTokensInfo(tokens);
			
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
