package sdong.defectAI.parameterize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokens;
import net.sourceforge.pmd.lang.python.ast.PythonParserConstants;
import sdong.defectAI.tokenizer.TokenizerPython;

public class Parameterize {

	TokenizerPython tokenizer;
	boolean needparameterize = false;

	public Parameterize() {
		this.tokenizer = new TokenizerPython();
	}

	public Parameterize(boolean needparameterize) {
		this.tokenizer = new TokenizerPython();
		this.needparameterize = needparameterize;
	}

	public Tokens process(String fileName) throws IOException {
		Tokens tokens = tokenizer.buildTokenizer(fileName);
		tokens = parameterizeTokens(tokens);

		return tokens;
	}

	public Tokens process(List<String> strList, String fileName) throws IOException {
		Tokens tokens = tokenizer.buildTokenizer(strList, fileName);
		tokens = parameterizeTokens(tokens);

		return tokens;
	}

	public Tokens parameterizeTokens(Tokens tokens) {
		if (needparameterize == false) {
			return tokens;
		}

		Tokens retTokens = new Tokens();
		int current = 0;
		int previous = 0;
		TokenEntry tmp = null;
		for (TokenEntry entry : tokens.getTokens()) {
			current = entry.getKind();
			if (current == PythonParserConstants.NAME) {
				if (previous == PythonParserConstants.NAME) {
					retTokens.add(tmp);
					tmp = entry;
				} else {
					if (tmp == null) {
						tmp = entry;
					} else {
						tmp.setImage(tmp.toString() + entry.toString());
						tmp.setKind(PythonParserConstants.NAME);
					}
				}
			} else if (current == PythonParserConstants.DOT) {
				if (previous == PythonParserConstants.NAME) {
					if (tmp == null) {
						tmp = entry;
					} else {
						tmp.setImage(tmp.toString() + entry.toString());
						tmp.setKind(PythonParserConstants.NAME);
					}
				} else {
					if (tmp != null) {
						retTokens.add(tmp);
						tmp = null;
					}
					retTokens.add(entry);
				}
			} else if (current == PythonParserConstants.LPAREN) {
				if (previous == PythonParserConstants.NAME) {
					if (tmp != null) {
						tmp.setKind(PythonParserConstants.DEFINE_FUNCTION);
						retTokens.add(tmp);

						tmp = null;
					}
				}
				retTokens.add(entry);
			} else {
				if (tmp != null) {
					retTokens.add(tmp);
					tmp = null;
				}
				retTokens.add(entry);
			}
			previous = current;
		}
		if (tmp != null) {
			retTokens.add(tmp);
		}

		return retTokens;
	}

	public List<String> parameterizeProcess(List<String> tokenlist) {
		List<String> returnlist = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();

		String previous = "";

		for (String token : tokenlist) {
			if (token.equals("82")) {
				if (previous.equals("82")) {
					returnlist.add("82");
					temp.clear();
				}
				temp.add(token);
			} else if (token.equals("15")) {
				if (previous.equals("82")) {
					temp.add(token);
				} else {
					returnlist.add(token);
				}
			} else if (token.equals("7")) {
				if (temp.size() > 0) {
					returnlist.add("120");
					returnlist.add("7");
					temp.clear();
				} else {
					returnlist.add(token);
				}
			} else {
				if (temp.size() > 0) {
					returnlist.add("82");
					temp.clear();
				}
				returnlist.add(token);
			}
			previous = token;
		}

		return returnlist;

	}
}
