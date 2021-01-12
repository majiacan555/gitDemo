package Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import LiberyUtils.LiberyCache;

public class LCMC {
	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Users\\pc\\Desktop\\ZCTCWordSmithedition"); 
		String SaveBasePath = "C:\\Users\\pc\\Desktop\\ZCTC原始文本\\"; 
		File SaveDirectory =new File(SaveBasePath); 
		if (!SaveDirectory.exists()) {
			SaveDirectory.mkdirs();
		}
        File[] files = file.listFiles();
        for (File file2 : files) { 
        	System.out.println("开始解析："+file2.getPath());
        	List<String> stringFile =LiberyCache.ReadTextFromTxt(file2,"Unicode");
        	List<String> strList = GetOriginalText(stringFile); 
        	 String fileName = SaveBasePath +file2.getName();
        	LiberyCache.appendStringToFile(fileName,strList);
        	System.out.println(fileName + "_保存成功");
        	
		} 
	}

	private static List<String> GetOriginalText(List<String> stringFile) {
		List<String> strList = new ArrayList<String>();
    	for (int i = 44; i < stringFile.size(); i++) {
    		String string  = stringFile.get(i);
//    		System.out.println(string); 
    		String[] words = string.split(" ");
    		if (words.length>1) {
    			String strLine = "";
    			for (String word : words) {
    				String[] texts = word.split("_");
    				if (texts.length>1) {
    					strLine+= texts[0];
					}
    			}
//    			System.out.println(strLine);  
    			strList.add(strLine);
			}
		} 
		return strList;
	} 
	private static List<String> GetPosText(List<String> stringFile) {
		List<String> strList = new ArrayList<String>();
    	for (int i = 44; i < stringFile.size(); i++) {
    		String string  = stringFile.get(i);
//    		System.out.println(string); 
    		String[] words = string.split(" ");
    		if (words.length>1) {
    			String strLine = "";
    			for (String word : words) {
    				String[] texts = word.split("_");
    				if (texts.length>1) {
    					strLine+= word+" ";
					}
    			} 
    			if (!strLine.isEmpty()) { 
    				strLine = strLine.substring(0,strLine.length()-1); 
//    				System.out.println(strLine);  
    				strList.add(strLine);
				}
			}
		} 
		return strList;
	} 
}
