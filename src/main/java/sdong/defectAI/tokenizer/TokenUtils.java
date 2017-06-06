package sdong.defectAI.tokenizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokens;
import sdong.defectAI.cluster.Kmeans.Node;

public class TokenUtils {
	public static void printTokensInfo(Tokens tokens) {
		for (TokenEntry entry : tokens.getTokens()) {
			System.out.println("tokenSrcID=" + entry.getTokenSrcID() + ", index=" + entry.getIndex() + ", identifier="
					+ entry.getIdentifier() + ", beginLine" + entry.getBeginLine() + ", kind=" + entry.getKind()
					+ ", token=" + entry.toString());
		}
	}

	public static List<Tokens> splitTokensByLine(Tokens tokens) {
		List<Tokens> tokenslist = new ArrayList<Tokens>();
		Map<Integer, List<TokenEntry>> map = getTokensEntryByLine(tokens);
		Tokens onetokens;
		for (Map.Entry<Integer, List<TokenEntry>> entrylist : map.entrySet()) {
			onetokens = new Tokens();
			for (TokenEntry tokenEntry : entrylist.getValue()) {
				onetokens.add(tokenEntry);
			}
			tokenslist.add(onetokens);
		}
		return tokenslist;
	}

	/**
	 * Get tokens kind list from list tokens
	 * 
	 * @param tokenslist
	 * @return
	 */
	public static List<List<Integer>> getTokensKindList(List<Tokens> tokenslist) {
		List<List<Integer>> kindlist = new ArrayList<List<Integer>>();
		for (Tokens tokens : tokenslist) {
			kindlist.add(getTokensKind(tokens));
		}
		return kindlist;
	}

	/**
	 * Get tokens kind from tokens
	 * 
	 * @param tokens
	 * @return
	 */
	public static List<Integer> getTokensKind(Tokens tokens) {
		return getTokensKind(tokens.getTokens());
	}

	/**
	 * Get tokens kind by list TokenEntry
	 * 
	 * @param entrys
	 * @return
	 */
	public static List<Integer> getTokensKind(List<TokenEntry> entrys) {
		List<Integer> kindlist = new ArrayList<Integer>();
		for (TokenEntry entry : entrys) {
			if (entry.getKind() != 0) {
				kindlist.add(entry.getKind());
			}
		}
		return kindlist;
	}

	/**
	 * Get tokens kind as string list from list tokens
	 * 
	 * @param tokenslist
	 * @return
	 */
	public static List<List<String>> getTokensKindListAsStr(List<Tokens> tokenslist) {
		List<List<String>> kindlist = new ArrayList<List<String>>();
		for (Tokens tokens : tokenslist) {
			kindlist.add(getTokensKindAsStr(tokens));
		}
		return kindlist;
	}

	/**
	 * Get token kind as string from tokens
	 * 
	 * @param tokens
	 * @return
	 */
	public static List<String> getTokensKindAsStr(Tokens tokens) {
		return getTokensKindAsStr(tokens.getTokens());
	}

	public static List<String> getTokensKindAsStr(List<TokenEntry> entrys) {
		List<String> kindlist = new ArrayList<String>();
		for (TokenEntry entry : entrys) {
			if (entry.getKind() != 0) {
				kindlist.add(entry.getKind() + "");
			}
		}
		return kindlist;
	}

	public static Map<Integer, List<TokenEntry>> getTokensEntry(List<Tokens> tokenlist) {
		Map<Integer, List<TokenEntry>> entrylist = new TreeMap<Integer, List<TokenEntry>>();
		for (int i = 0; i < tokenlist.size(); i++) {
			entrylist.put(i + 1, tokenlist.get(i).getTokens());
		}
		return entrylist;
	}

	public static Map<Integer, List<Integer>> getTokensKindByLine(Tokens tokens) {
		Map<Integer, List<Integer>> kinds = new TreeMap<Integer, List<Integer>>();

		Map<Integer, List<TokenEntry>> entrylist = getTokensEntryByLine(tokens);
		for (Map.Entry<Integer, List<TokenEntry>> entry : entrylist.entrySet()) {
			kinds.put(entry.getKey(), getTokensKind(entry.getValue()));
		}

		return kinds;
	}

	public static Map<Integer, List<String>> getTokensKindByLineAsStr(Tokens tokens) {
		Map<Integer, List<String>> kinds = new TreeMap<Integer, List<String>>();

		Map<Integer, List<TokenEntry>> entrylist = getTokensEntryByLine(tokens);
		for (Map.Entry<Integer, List<TokenEntry>> entry : entrylist.entrySet()) {
			kinds.put(entry.getKey(), getTokensKindAsStr(entry.getValue()));
		}

		return kinds;
	}

	public static Map<Integer, List<TokenEntry>> getTokensEntryByLine(Tokens tokens) {
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

	public static List<List<String>> getTokensValueList(List<Tokens> tokenslist) {
		List<List<String>> valuelist = new ArrayList<List<String>>();
		for (Tokens tokens : tokenslist) {
			valuelist.add(getTokensValue(tokens));
		}
		return valuelist;
	}

	public static List<String> getTokensValue(Tokens tokens) {
		return getTokensValue(tokens.getTokens());
	}

	public static List<String> getTokensValue(List<TokenEntry> entrys) {
		List<String> valuelist = new ArrayList<String>();
		for (TokenEntry entry : entrys) {
			valuelist.add(entry.toString());
		}
		return valuelist;
	}

	public static Map<Integer, List<String>> getTokensValueByLine(Tokens tokens) {
		Map<Integer, List<String>> values = new TreeMap<Integer, List<String>>();
		Map<Integer, List<TokenEntry>> entrylist = getTokensEntryByLine(tokens);

		for (Map.Entry<Integer, List<TokenEntry>> entry : entrylist.entrySet()) {
			values.put(entry.getKey(), getTokensValue(entry.getValue()));
		}
		return values;

	}

	public static List<String> getTokensValueFromFile(String path) throws FileNotFoundException {
		List<String> valuelist = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String str;
			while ((str = br.readLine()) != null) {
				valuelist.add(str);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valuelist;
	}

	public static void saveTokensKind(Tokens tokens, String fileName) throws FileNotFoundException {
		saveTokensKind(splitTokensByLine(tokens), fileName);
	}

	/**
	 * Save tokens kind by list tokens
	 * 
	 * @param tokenslist
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public static void saveTokensKind(List<Tokens> tokenslist, String fileName) throws FileNotFoundException {
		PrintStream out = new PrintStream(fileName);
		int line = 0;
		for (Tokens tokens : tokenslist) {
			out.println(line + "=" + String.join(",", getTokensKindAsStr(tokens)));
			line = line + 1;
		}
		out.close();
	}

	public static void saveTokensValue(Tokens tokens, String fileName) throws FileNotFoundException {
		saveTokensValue(splitTokensByLine(tokens), fileName);
	}

	/**
	 * Save token value by list tokens
	 * 
	 * @param tokenslist
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public static void saveTokensValue(List<Tokens> tokenslist, String fileName) throws FileNotFoundException {
		PrintStream out = new PrintStream(fileName);
		int line = 0;
		for (Tokens tokens : tokenslist) {
			out.println(line + "=" + String.join(",", getTokensValue(tokens)));
			line = line + 1;
		}
		out.close();
	}

}
