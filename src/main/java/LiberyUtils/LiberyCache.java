package LiberyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
 
public class LiberyCache { 
//	 public static HSSFWorkbook readExcel(File file) {
//	        try {
//	            // ��������������ȡExcel
//	        	FileInputStream  is = new FileInputStream(file.getAbsolutePath());
//	            // jxl�ṩ��Workbook��
//	        	HSSFWorkbook  workbook =  new HSSFWorkbook(is);
//	            // Excel��ҳǩ����
//	        	is.close();
//	        	return workbook;
//	        } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	        } 
//	          catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        return null;
//	    }
	 public static int getWordCount(String s, String searchWord) { 
			String s1=searchWord;
			int count = 0;
			int index = 0;
			while(s.indexOf(s1)!=-1){
				index=s.indexOf(s1) + s1.length();
				s = s.substring(index);
				count++;
			}
			return count;
		} 
	public static void appendStringToFile(String fileName, List<String> contentList) { 
		try {   
		File file = new File(fileName);
		if (!file.exists()) { 
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs(); 
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(fileName, true);
		for (String string : contentList) {
			string +="\r\n";
			writer.write(string); 
		}
		writer.close(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		} 
	public static void WriteStringToFile(String fileName, List<String> contentList) { 
		try {   
		File file = new File(fileName);
		if (!file.exists()) { 
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs(); 
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(fileName, false);
		for (String string : contentList) {
			string +="\r\n";
			writer.write(string); 
		}
		writer.close(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		} 
	public static void WriteStringToCSV(String fileName, List<String> contentList) throws IOException { 
		    
		File file = new File(fileName);
		if (!file.exists()) { 
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs(); 
			file.createNewFile();
		}
		BufferedWriter writer=null;
		OutputStreamWriter outputStreamWriter=null; 
        try{
            outputStreamWriter=new OutputStreamWriter(new FileOutputStream(file),"GBK"); 
            writer=new BufferedWriter(outputStreamWriter);
            for (String string : contentList) {
            	string +="\r\n";
            	writer.write(string); 
            } 
        }catch (Exception e){
            e.printStackTrace();
        } 
        
		 
		} 
	public static  List<String> ReadTextFromTxt(File file,String Type) throws IOException { 
		List<String> list = new ArrayList<>(); 
		if (file.exists()) {
			InputStream inputStream1 = new FileInputStream(file);
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(inputStream1, Type));
			String line;
			while ((line = br.readLine()) != null )
			{
				if(!line.isEmpty())
					list.add(line);
			}
			br.close();
			inputStream1.close(); 
		}
		return list;
		
	}
	public static  List<String> ReadTextFromLocal(String filePath,String Type) throws IOException { 
		File file = new File(filePath);
		List<String> list = new ArrayList<>(); 
		if (file.exists()) {
			InputStream inputStream1 = new FileInputStream(file);
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(inputStream1, Type));
			String line;
			while ((line = br.readLine()) != null )
			{
				if(!line.isEmpty())
					list.add(line);
			}
			br.close();
			inputStream1.close(); 
		}
		return list;
		
	}
	public static  String ReplaceString(String text,String oldType,String newType) throws IOException { 
		if (text.contains(oldType)) {
			text = text.replace(oldType, newType);
		}
		return text;
		
	}
	public static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000.0)+"秒");
		Thread.sleep(Random);
		
	}

}
