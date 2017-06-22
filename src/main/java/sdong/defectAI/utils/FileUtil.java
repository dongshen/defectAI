package sdong.defectAI.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sdong.defectAI.exception.DefectAIException;

public class FileUtil {

	private static final Logger LOG = Logger.getLogger(FileUtil.class);

	public static void saveFile(List<String> strList, String path) throws DefectAIException {
		try {
			PrintStream out = new PrintStream(path);
			for (String strline : strList) {
				out.println(strline);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new DefectAIException(e.getMessage());
		}
	}

	public static List<String> readFileByLine(String path) throws DefectAIException {
		List<String> lines = new ArrayList<String>();
		try {
			// Open the file
			FileInputStream fstream = new FileInputStream(path);

			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				lines.add(strLine);
			}

			// Close the input stream
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new DefectAIException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new DefectAIException(e.getMessage());
		}
		return lines;
	}
}
