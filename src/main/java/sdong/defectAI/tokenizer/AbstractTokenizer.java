package sdong.defectAI.tokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

public abstract class AbstractTokenizer {

	protected int maxTokenKindSize;

	protected String fileName;
	protected Tokenizer tokenizer;
	protected SourceCode sourceCode;

	public abstract Tokens buildTokenizer(String fileName) throws IOException;

	public abstract Tokens buildTokenizer(List<String> strList, String fileName) throws IOException;

	public abstract String getSampleCode() throws IOException;


	public int getMaxTokenKindSize() {
		return maxTokenKindSize;
	}

	public void setMaxTokenKindSize(int maxTokenKindSize) {
		this.maxTokenKindSize = maxTokenKindSize;
	}
	
	public List<Tokens> buildTokenizer(List<List<String>> list) throws IOException{
		List<Tokens> tokenlist = new ArrayList<Tokens>();
		for(List<String> caseCode: list ){
			tokenlist.add(buildTokenizer(caseCode,""));
		}
		return tokenlist;
	}
	
}