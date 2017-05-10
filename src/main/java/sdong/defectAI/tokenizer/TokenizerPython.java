package sdong.defectAI.tokenizer;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.cpd.PythonTokenizer;
import net.sourceforge.pmd.cpd.SourceCode;

public class TokenizerPython extends AbstractTokenizer {

	
	public TokenizerPython(){
		this.tokenizer = new PythonTokenizer();
	}
	
	@Override
	public void buildTokenizer(String fileName) throws IOException {
		this.fileName= fileName;
		this.sourceCode = new SourceCode(new SourceCode.StringCodeLoader(this.getSampleCode(), fileName));
	}

	@Override
	public void buildTokenizer(List<String> strList, String fileName) throws IOException {
		this.fileName= fileName;
		this.sourceCode = new SourceCode(new SourceCode.StringCodeLoader(this.getSampleCode(strList), fileName));
	}
	
	@Override
	public String getSampleCode() throws IOException {
		return IOUtils.toString(PythonTokenizer.class.getResourceAsStream(fileName));
	}

	public String getSampleCode(List<String> strList ) throws IOException {
		StringBuffer bf = new StringBuffer();
		for(String str:strList){
			bf.append(str).append(PMD.EOL);
		}
		return bf.toString();
	}
	
}
