package sdong.defectAI;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

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
	public String getSampleCode() throws IOException {
		return IOUtils.toString(PythonTokenizer.class.getResourceAsStream(fileName));
	}

}
