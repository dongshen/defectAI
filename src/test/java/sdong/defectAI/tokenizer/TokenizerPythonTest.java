package sdong.defectAI.tokenizer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Test;

import net.sourceforge.pmd.cpd.Tokens;

public class TokenizerPythonTest {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	String fileName = "sample-python.py";

	@Test
	public void testBuildTokenizerByFile() {

		TokenizerPython tokenizer = new TokenizerPython();
		try {
			Tokens tokens = tokenizer.buildTokenizer(fileName);
			assertEquals(1218, tokens.size());
			
			List<Tokens> tokenlist = TokenUtils.splitTokensByLine(tokens);
			assertEquals(200, tokenlist.size());
			assertEquals(4, tokenlist.get(0).size());
			assertEquals("from", tokenlist.get(0).getTokens().get(0).toString());
			assertEquals("__future__", tokenlist.get(0).getTokens().get(1).toString());
			assertEquals("import", tokenlist.get(0).getTokens().get(2).toString());
			assertEquals("unicode_literals", tokenlist.get(0).getTokens().get(3).toString());

			String path = "output" + File.separatorChar + "sample-python_token_kind.txt";
			TokenUtils.saveTokensKind(tokens, path);

			path = "output" + File.separatorChar + "sample-python_token_value.txt";
			TokenUtils.saveTokensValue(tokens, path);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testbuildTokenizerByCodeList() {

		String line1 = "from __future__ import unicode_literals";
		String line2 = "import logging";
		List<String> strList = new ArrayList<String>();
		strList.add(line1);
		strList.add(line2);

		TokenizerPython tokenizer = new TokenizerPython();
		try {
			Tokens tokens = tokenizer.buildTokenizer(strList, fileName);

			Map<Integer, List<String>> values = TokenUtils.getTokensValueByLine(tokens);
			assertEquals("[from, __future__, import, unicode_literals]", values.get(1).toString());
			assertEquals("[import, logging]", values.get(2).toString());
			
			Map<Integer, List<Integer>> kinds = TokenUtils.getTokensKindByLine(tokens);
			assertEquals("[73, 82, 72, 82]", kinds.get(1).toString());
			assertEquals("[72, 82]", kinds.get(2).toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
