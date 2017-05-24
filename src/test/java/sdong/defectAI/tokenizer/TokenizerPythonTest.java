package sdong.defectAI.tokenizer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Test;

import net.sourceforge.pmd.cpd.TokenEntry;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.utils.FileUtil;

public class TokenizerPythonTest {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	String fileName = "sample-python.py";

	@Test
	public void testGetTokens() {

		TokenizerPython tokenizer = new TokenizerPython();
		try {
			tokenizer.buildTokenizer(fileName);
			tokenizer.buildTokens();
			List<TokenEntry> tokens = tokenizer.getTokens();
			assertEquals(1218, tokens.size());
			
			Map<Integer, List<String>> map = tokenizer.getTokensKind();
			List<List<String>> list = new ArrayList<List<String>>(map.values());
			
			List<String> strlist = new ArrayList<String>();
			for(List<String> tokenlist: list){
				strlist.add(String.join(",",tokenlist));
			}
			String path = "sample-python-token.txt";
			FileUtil.saveFile(strlist, path);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefectAIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTokensFromList() {

		List<String> strList = setCodeList();

		TokenizerPython tokenizer = new TokenizerPython();
		try {
			tokenizer.buildTokenizer(strList, fileName);
			tokenizer.buildTokens();
			Map<Integer,List<String>> values = tokenizer.getTokensValue();
			assertEquals("[from, __future__, import, unicode_literals]", values.get(1).toString());
			assertEquals("[import, logging]", values.get(2).toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testGetTokensKind() {
		List<String> strList = setCodeList();

		TokenizerPython tokenizer = new TokenizerPython();
		try {
			tokenizer.buildTokenizer(strList, fileName);
			tokenizer.buildTokens();
			Map<Integer,List<String>> values = tokenizer.getTokensKind();
			assertEquals("[73, 82, 72, 82]", values.get(1).toString());
			assertEquals("[72, 82]", values.get(2).toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<String> setCodeList() {
		String line1 = "from __future__ import unicode_literals";
		String line2 = "import logging";
		List<String> strList = new ArrayList<String>();
		strList.add(line1);
		strList.add(line2);
		return strList;
	}

}
