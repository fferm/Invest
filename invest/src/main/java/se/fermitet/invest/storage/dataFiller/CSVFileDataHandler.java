package se.fermitet.invest.storage.dataFiller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class CSVFileDataHandler {
	
	@SuppressWarnings("finally")
	List<String[]> getSplitLines(String filename) {
		BufferedReader br = null;
		List<String[]> ret = new ArrayList<String[]>();
		try {
			String path = "dataFiller/" + filename;
			br = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource(path).getFile())));
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				i++;
				if (i == 1) continue;
				
				ret.add(line.split(";"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)	br.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return ret;
			}
		}
	}
}
