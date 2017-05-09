package sdong.defectAI;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import net.sourceforge.pmd.cpd.TokenEntry;

public class TokenizerPythonTest {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Test
	public void testGetTokens() {
		String fileName = "sample-python.py";
		TokenizerPython tokenizer = new TokenizerPython();
		try {
			tokenizer.buildTokenizer(fileName);
			List<TokenEntry> tokens = tokenizer.getTokens();

			assertEquals(1218, tokens.size());
			for (TokenEntry entry : tokens) {

				logger.info("Line=" + entry.getBeginLine() + " index=" + entry.getIndex() + " Indentifier="
						+ entry.getIdentifier() + " string=" + entry.toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
