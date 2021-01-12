package Stanford_Corenlp;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import LiberyUtils.LiberyCache;
import LiberyUtils.StanfordCoreNlPCommon;

public class StanfordPos {
	public static void main(String[] args) throws IOException, SQLException {
		String Base = "C:\\Users\\superadmin.DLHZ106\\Desktop\\";
		String basePath = Base + "word\\";
		String savePath = Base + "wordResult\\";
		List<String> nameList = new ArrayList<String>();
		nameList.add("lcmc");
		nameList.add("zctc");
		for (String name : nameList) {
			String DirectoryName = basePath + name;
			String SaveDirectory = savePath + name;
			File file = new File(DirectoryName);
			File[] files = file.listFiles();
			for (File file2 : files) {
				List<String> resultList = new ArrayList<>();
				List<String> textList = LiberyCache.ReadTextFromTxt(file2, "utf-8");
				for (String string : textList) {
					String result = StanfordCoreNlPCommon.getChinesePos(string);
					resultList.add(result);
				}
				String saveName = SaveDirectory +"\\" + file2.getName();
				LiberyCache.WriteStringToFile(saveName, resultList);
			}
		}
		
	}

}
