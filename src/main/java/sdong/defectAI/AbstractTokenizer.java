package sdong.defectAI;

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

    public abstract void buildTokenizer(String fileName) throws IOException;

    public abstract String getSampleCode() throws IOException;

    public List<TokenEntry> getTokens() throws IOException {
        Tokens tokens = new Tokens();
        tokenizer.tokenize(sourceCode, tokens);
        List<TokenEntry> entries = tokens.getTokens();
        return entries;
    }

}