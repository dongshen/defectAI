package sdong.defectAI.parameterize;

import java.util.ArrayList;
import java.util.List;

public class Parameterize {

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
				}else{
					returnlist.add(token);
				}
			} else if (token.equals("7")) {
				if (temp.size() > 0) {
					returnlist.add("120");
					returnlist.add("7");
					temp.clear();
				}else{
					returnlist.add(token);
				}
			}else{
				if(temp.size()>0){
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
