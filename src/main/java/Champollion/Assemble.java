package Champollion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import LiberyUtils.LiberyCache;

//将通过champollion对齐好的Ctext文档和Etext文档组装成一份文档
public class Assemble {
	public static void main(String[] args) throws IOException {
		File Basefile = new File("D:\\financialDocuments\\documents");
		File[] files = Basefile.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					String filepath = f.getPath();
					File writename = new File("D:\\AnalysResult1\\output" + f.getName() + ".txt");
					Organization(filepath, writename);
				}
			}

		}
	}

	public static void Organization(String filepath, File writename) throws IOException {
		Assemble obj = new Assemble();
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		writename.createNewFile();
		File file = new File(filepath);
		File[] fileList = file.listFiles();
		List<File> wjjList = new ArrayList<File>();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory())
				wjjList.add(fileList[i]);
		}
		for (int i = 0; i < wjjList.size(); i++) {
			if (wjjList.get(i).isDirectory()) {
				File[] textFiles = wjjList.get(i).listFiles();
				if (textFiles.length < 3) {
					continue;
				}
				List<String> Result = LiberyCache.ReadTextFromTxt(textFiles[0], "gbk");

				List<String> listC = LiberyCache.ReadTextFromTxt(textFiles[1], "gbk");
				List<String> listE = LiberyCache.ReadTextFromTxt(textFiles[2], "gbk");
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
						out.write(English + finalResult.get(j).getEnglishData() + "\r\n");
						out.write(Chinese + finalResult.get(j).getChineseData() + "\r\n");
						English = "";
						Chinese = "";
					} else {
						if (finalResult.get(j).getEnglishData() == null
								|| finalResult.get(j).getEnglishData().length() <= 0)
							Chinese += finalResult.get(j).getChineseData();
						if (finalResult.get(j).getChineseData() == null
								|| finalResult.get(j).getChineseData().length() <= 0)
							English += finalResult.get(j).getEnglishData();
					}
					out.flush(); // �ѻ���������ѹ���ļ�
//				   System.out.println(finalResult.get(j).getEnglishData());
//				   System.out.println(finalResult.get(j).getChineseData());
				}
			}
		}
		out.close();
	}

}
