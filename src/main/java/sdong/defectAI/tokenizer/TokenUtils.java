package sdong.defectAI.tokenizer;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokens;

public class TokenUtils {
	public static void printTokensInfo(Tokens tokens) {
		for (TokenEntry entry : tokens.getTokens()) {
			System.out.println("tokenSrcID=" + entry.getTokenSrcID() + ", index=" + entry.getIndex() + ", identifier="
					+ entry.getIdentifier() + ", beginLine" + entry.getBeginLine() + ", kind=" + entry.getKind()
					+ ", token=" + entry.toString());
		}
	}

	public static Map<Integer, List<TokenEntry>> getTokensEntry(Tokens tokens) {
		Map<Integer, List<TokenEntry>> entrylist = new TreeMap<Integer, List<TokenEntry>>();
		List<TokenEntry> lineEntry;
		int lineNo = 0;
		for (TokenEntry entry : tokens.getTokens()) {
			if (entry.getKind() != 0) {
				lineNo = entry.getBeginLine();
				lineEntry = entrylist.get(lineNo);
				if (lineEntry == null) {
					lineEntry = new ArrayList<TokenEntry>();
				}
				lineEntry.add(entry);
				entrylist.put(lineNo, lineEntry);
			}
		}
		return entrylist;
	}

	public static List<Integer> getTokenKind(List<TokenEntry> entrys) {
		List<Integer> kinds = new ArrayList<Integer>();
		for (TokenEntry entry : entrys) {
			kinds.add(entry.getKind());
		}
		return kinds;
	}

	public static List<String> getTokenValue(List<TokenEntry> entrys) {
		List<String> values = new ArrayList<String>();
		for (TokenEntry entry : entrys) {
			values.add(entry.toString());
		}
		return values;
	}

	public static Map<Integer, List<Integer>> getTokensKind(Tokens tokens) {
		Map<Integer, List<Integer>> kinds = new TreeMap<Integer, List<Integer>>();

		Map<Integer, List<TokenEntry>> entrylist = getTokensEntry(tokens);
		for (Map.Entry<Integer, List<TokenEntry>> entry : entrylist.entrySet()) {
			kinds.put(entry.getKey(), getTokenKind(entry.getValue()));
		}

		return kinds;
	}

	public static Map<Integer, List<String>> getTokensValue(Tokens tokens) {
		Map<Integer, List<String>> values = new TreeMap<Integer, List<String>>();
		Map<Integer, List<TokenEntry>> entrylist = getTokensEntry(tokens);

		for (Map.Entry<Integer, List<TokenEntry>> entry : entrylist.entrySet()) {
			values.put(entry.getKey(), getTokenValue(entry.getValue()));
		}
		return values;

	}

	public static void saveTokensKind(Tokens tokens, String fileName) throws FileNotFoundException {
		Map<Integer, List<Integer>> kinds = getTokensKind(tokens);
		PrintStream out = new PrintStream(fileName);
		for (Map.Entry<Integer, List<Integer>> line : kinds.entrySet()) {
			out.println(line.getKey() + "=" + line.getValue().toString());
		}
		out.close();
	}
}
