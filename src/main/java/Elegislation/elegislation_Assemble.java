package Elegislation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Champollion.CEDataNLPText;
import LiberyUtils.LiberyCache;

//将通过champollion对齐好的Ctext文档和Etext文档组装成一份文档
public class elegislation_Assemble {
	static int NoAnalysCount = 0;

	public static void main(String[] args) throws IOException {
		File Basefile = new File("C:\\Users\\superadmin.DLHZ106\\Desktop\\NoAnayls");
		String SavePath = "C:\\Users\\superadmin.DLHZ106\\Desktop\\NoAnaylsResult\\";
		File[] files = Basefile.listFiles();
		for (File f : files) {
			String filepath = f.getPath();
			String dirPath = SavePath + f.getName()+".txt"  ;
			Organization(filepath, dirPath);
		}
	}

	public static void Organization(String filepath, String savePath) throws IOException {
		List<String> resultList = new ArrayList<String>();
		File ctextFile = new File(filepath + "\\TextC.txt");
		File etextFile = new File(filepath + "\\TextE.txt");
		File ResultFile = new File(filepath + "\\ECresult.txt");
		if (!ctextFile.exists() || !etextFile.exists() || !ResultFile.exists()) {
			NoAnalysCount++;
			System.out.println(NoAnalysCount + " :" + filepath + " ---textFiles.length<3");
			return;
		}
		List<String> Result = LiberyCache.ReadTextFromTxt(ResultFile, "utf-8");
		List<String> listC = LiberyCache.ReadTextFromTxt(ctextFile, "utf-8");
		List<String> listE = LiberyCache.ReadTextFromTxt(etextFile, "utf-8");
		List<CEDataNLPText> finalResult = new ArrayList<>();
		for (int j = 0; j < Result.size(); j++) {
			String[] spiltStrings = Result.get(j).split("<=>");
			String[] spiltE = spiltStrings[0].split(",");
			String[] spiltC = spiltStrings[1].split(",");
			CEDataNLPText text = new CEDataNLPText();
			String CData = "";
			String EData = "";
			for (int k = 0; k < spiltE.length; k++) {
				if (!spiltE[k].contains("omitted"))
					EData += listE.get(Integer.parseInt(spiltE[k].trim()) - 1);
			}
			for (int k = 0; k < spiltC.length; k++) {
				if (!spiltC[k].contains("omitted"))
					CData += listC.get(Integer.parseInt(spiltC[k].trim()) - 1);
			}
			text.setChineseData(CData);
			text.setEnglishData(EData);
			finalResult.add(text);
		}
		String English = "";
		String Chinese = "";
		for (int j = 0; j < finalResult.size(); j++) {
			if (finalResult.get(j).getEnglishData() != null && finalResult.get(j).getEnglishData().length() > 0
					&& finalResult.get(j).getChineseData() != null
					&& finalResult.get(j).getChineseData().length() > 0) {
				resultList.add(English + finalResult.get(j).getEnglishData());
				resultList.add(Chinese + finalResult.get(j).getChineseData());
				English = "";
				Chinese = "";
			} else {
				if (finalResult.get(j).getEnglishData() == null || finalResult.get(j).getEnglishData().length() <= 0)
					Chinese += finalResult.get(j).getChineseData();
				if (finalResult.get(j).getChineseData() == null || finalResult.get(j).getChineseData().length() <= 0)
					English += finalResult.get(j).getEnglishData();
			}
		} 
		LiberyCache.WriteStringToFile(savePath, resultList);
	}

}
