package Champollion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import LiberyUtils.LiberyCache;

public class ReadTxt {
	public static void main(String[] args) throws IOException {
		String author = "Yang";
		File Cfile = new File("E:\\数据分析\\HLM\\"+author+"\\Chinese\\");
		File Efile = new File("E:\\数据分析\\HLM\\"+author+"\\English\\");
		File[] cfiles = Cfile.listFiles();
		File[] efiles = Efile.listFiles();
		String saveBaseString = "E:\\数据分析\\HLMsplit\\"+author+"\\";
		for (File f3 : cfiles) {
			String fileName = f3.getName();
			String[] splString = fileName.split("\\."); 	
			String num = splString[0].substring(2);
			String saveString = saveBaseString + num+"\\TextC.txt";
			List<String> strList = LiberyCache.ReadTextFromTxt(f3, "gbk");
			List<String> strList2 = new ArrayList<String>();
			for (String str : strList) {
				str = str.replace(" ", "");
				if (str.contains("\\.")) {
					str = str.replace("\\.", "");
				}
				if (str.contains("§")) {
					str = str.replace("§", "");
				}
				strList2.add(str);
			}
			CommonCache.TextSplit(strList2, "。", saveString);

		}
		for (File f3 : efiles) {
			String fileName = f3.getName();
			String[] splString = fileName.split("\\."); 
			String num = splString[0].substring(3);
			String saveString = saveBaseString + num+"\\TextE.txt";
			List<String> strList = LiberyCache.ReadTextFromTxt(f3, "gbk");
			List<String> strList2 = new ArrayList<String>();
			for (String str : strList) { 
				if (str.contains("§")) {
					str = str.replace("§", "");
				}
				strList2.add(str);
			}
			CommonCache.TextSplit(strList2, ".", saveString);

		} 
	}
}
