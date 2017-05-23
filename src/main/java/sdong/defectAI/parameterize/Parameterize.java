package sdong.defectAI.parameterize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokens;
import sdong.defectAI.exception.DefectAIException;
import sdong.defectAI.tokenizer.AbstractTokenizer;
import sdong.defectAI.tokenizer.TokenizerPython;

public class Parameterize {

	protected Tokens tokens;

	public Parameterize(Tokens tokens) {
		this.tokens = tokens;
	}

	public Map<Integer, List<Integer>> parameterProcess(List<TokenEntry> tokens) {
		Map<Integer, List<Integer>> kindlist = new HashMap<Integer, List<Integer>>();
		List<Integer> temp = new ArrayList<Integer>();
		int lineNo = 0;
		int previouslineNo = 0;
		int currentKind;
		int previousKind;
		boolean push = false;
		List<Integer> lineTokens;
		for (TokenEntry entry : tokens) {
			lineNo = entry.getBeginLine();
			currentKind = entry.getKind();
			if (lineNo != previouslineNo) {
				if (push == false) {

					push = true;
				}
			}

			if (currentKind == 82) {
				temp.add(currentKind);
			} else if (currentKind == 15) {
				temp.add(currentKind);
			} else if (currentKind == 7) {
				pushToken( kindlist, currentKind, 82);
				pushToken( kindlist, currentKind, 7);
				temp.clear();
			}

		}

		return kindlist;

	}

	private void pushToken(Map<Integer, List<Integer>> kindlist, int lineNo, int kind) {
		List<Integer> list = kindlist.get(lineNo);
		if (list == null) {
			list = new ArrayList<Integer>();
			kindlist.put(lineNo, list);
		}
		list.add(kind);

	}
}
