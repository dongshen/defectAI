package sdong.defectAI.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.apache.log4j.Logger;

import sdong.defectAI.exception.DefectAIException;



public class FileUtil {

	private static final Logger LOG = Logger.getLogger(FileUtil.class);

	public static void saveFile(List<String> strList, String path) throws DefectAIException{
		try {
			PrintStream out = new PrintStream(path);
			for (String strline: strList) {
				out.println(strline);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new DefectAIException(e.getMessage());
		}
	}

}
