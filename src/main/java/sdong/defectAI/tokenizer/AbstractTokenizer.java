package sdong.defectAI.tokenizer;

import java.io.IOException;
import java.util.List;

import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

public abstract class AbstractTokenizer {

	protected String fileName;
	protected Tokenizer tokenizer;
	protected SourceCode sourceCode;
	protected Tokens tokens;

	public abstract void buildTokenizer(String fileName) throws IOException;

	public abstract void buildTokenizer(List<String> strList, String fileName) throws IOException;

	public abstract String getSampleCode() throws IOException;

	public List<TokenEntry> getTokens() throws IOException {
		return tokens.getTokens();
	}

	public void buildTokens() throws IOException {
		tokens = new Tokens();
		tokenizer.tokenize(sourceCode, tokens);
	}

	public String getTokensKind() {
		StringBuffer bf = new StringBuffer();
		List<TokenEntry> entries = tokens.getTokens();
		for (TokenEntry entry : entries) {
			if (entry.getKind() != 0) {
				bf.append(entry.getKind()).append(",");
			}
		}

		return bf.substring(0, bf.length() - 1).toString();
	}

	public String getTokensValue() {
		StringBuffer bf = new StringBuffer();
		List<TokenEntry> entries = tokens.getTokens();
		for (TokenEntry entry : entries) {
			if (entry.getKind() != 0) {
				bf.append(entry.toString()).append(",");
			}
		}

		return bf.substring(0, bf.length() - 1).toString();
	}

}