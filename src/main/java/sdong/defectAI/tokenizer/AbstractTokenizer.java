package sdong.defectAI.tokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

public abstract class AbstractTokenizer {

	protected String fileName;
	protected Tokenizer tokenizer;
	protected SourceCode sourceCode;

	public abstract Tokens buildTokenizer(String fileName) throws IOException;

	public abstract Tokens buildTokenizer(List<String> strList, String fileName) throws IOException;

	public abstract String getSampleCode() throws IOException;


	public Map<Integer, List<String>> getTokensKind(Tokens tokens) {
		Map<Integer, List<String>> valueList = new HashMap<Integer, List<String>>();
		List<TokenEntry> entries = tokens.getTokens();
		int lineNo;
		List<String> values;
		for (TokenEntry entry : entries) {
			if (entry.getKind() != 0) {
				lineNo = entry.getBeginLine();
				values = valueList.get(lineNo);
				if (values == null) {
					values = new ArrayList<String>();
					valueList.put(lineNo, values);
				}
				values.add(String.valueOf(entry.getKind()));
			}
		}

		return valueList;
	}

	public Map<Integer, List<String>> getTokensValue(Tokens tokens) {
		Map<Integer, List<String>> valueList = new HashMap<Integer, List<String>>();
		List<TokenEntry> entries = tokens.getTokens();
		int lineNo;
		List<String> values;
		for (TokenEntry entry : entries) {
			if (entry.getKind() != 0) {
				lineNo = entry.getBeginLine();
				values = valueList.get(lineNo);
				if (values == null) {
					values = new ArrayList<String>();
					valueList.put(lineNo, values);
				}
				values.add(entry.toString());
			}
		}

		return valueList;
	}

	public void printTokensInfo(Tokens tokens) {
		for (TokenEntry entry : tokens.getTokens()) {
			System.out.println("tokenSrcID=" + entry.getTokenSrcID() + ", index=" + entry.getIndex() + ", identifier="
					+ entry.getIdentifier() + ", beginLine" + entry.getBeginLine() + ", kind=" + entry.getKind()
					+ ", token=" + entry.toString());
		}
	}

}