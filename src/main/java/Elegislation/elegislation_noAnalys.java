package Elegislation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import Champollion.CEDataNLPText;
import LiberyUtils.LiberyCache;

//将通过champollion对齐好的Ctext文档和Etext文档组装成一份文档
public class elegislation_noAnalys {
	static int NoAnalysCount = 0;

	public static void main(String[] args) throws IOException {
		File Basefile = new File("C:\\Users\\superadmin.DLHZ106\\Desktop\\Elegislation");
		String SavePath = "C:\\Users\\superadmin.DLHZ106\\Desktop\\NoAnayls\\";
		File[] files = Basefile.listFiles();
		for (File f : files) {
			String dirSavePath = SavePath + f.getName() +"\\";
			String filepath = f.getPath();  
			Organization(filepath, dirSavePath); 
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
			List<String> cList = LiberyCache.ReadTextFromTxt(ctextFile, "utf-8"); 
			if (cList.size() <4000) { 
				cList = GetClearList(cList,true);
				String cSavePath = savePath + ctextFile.getName() ;
				LiberyCache.WriteStringToFile(cSavePath, cList);   
				String eSavePath = savePath + etextFile.getName() ;
				List<String> eList = LiberyCache.ReadTextFromTxt(etextFile, "utf-8"); 
				eList = GetClearList(eList,false);
				LiberyCache.WriteStringToFile(eSavePath, eList); 
			} 
		} 
	} 
	
	private static List<String> GetClearList(List<String> StringList, boolean isChinese) {
		List<String> newList = new  ArrayList<>();
		for (String string : StringList) {
			boolean isFind = false;
			if (isChinese) {
				  isFind =  FindEnglish(string); 
			}else {
				  isFind =  FindChinese(string); 
			}
			if (isFind) {
				string = string.replaceAll("(\\()(.*?)(\\))", "");   
			}
			while (string.length() > 0 && string.substring(0, 1).equals("	")) {
				string = string.substring(1);
			}
			while (string.length() > 0 && string.substring(0, 1).equals(" ")) {
				string = string.substring(1);
			}
			while (string.length() > 0 && string.substring(0, 1).equals(" ")) {
				string = string.substring(1);
			}
			
			if (!string.trim().isEmpty() && string.trim().length() > 0) {   
				newList .add(string);
			}
		}
		return newList;
	}

	private static boolean FindChinese(String string) {
		Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        if(p.matcher(string).find()) 
           return true;
        else  
           return false;
         
	}

	private static boolean FindEnglish(String string) {
		Pattern p = Pattern.compile("[a-zA-z]");
        if(p.matcher(string).find()) 
           return true;
        else  
           return false; 
	} 
}
