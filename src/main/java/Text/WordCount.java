package Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
 
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
 
import com.google.gson.JsonArray;

import HttpClientDemo.AddData;
import HttpClientDemo.DBUtil;
import TransLationModel.CEDataNLP2;
import TransLationModel.CEDataNLPText;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.English;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


public class WordCount {
     public static void main(String[] args) throws IOException {
    	 String filePath  = "FROWNresult";
    	 String FilePath = "C:\\Users\\superadmin.DLHZ106\\Desktop\\数据分析\\OneDrive_1_2019-4-6\\"+filePath;
    	 File file = new File(FilePath); 
    	 File[] files = file.listFiles();
    	 System.out.println("files Count"+files.length);
         if (!filePath.equals("FLOBtexts(1)")&&!filePath.equals("ZCTC WordSmith edition")&&!filePath.equals("ZCTC") ) {
        	 for (File file2 : files) {  
        		 String[] fileName = file2.getName().split("\\.");
        		 String excelName = fileName[0]+".xls";
        		 String fileStr = getStringFromFile(file2);
        		 String[] wordStr = fileStr.split(" ");
        		 List<String> wordList = new ArrayList<>(); 
        		 for (String string : wordStr) {
        			 wordList.add(string);
        		 } 
        		 if (wordList.size() >0) { 
        			 getResultExcel(filePath,excelName,wordList);
        		 }else {
        			 System.out.println(filePath+"\\"+excelName+"没有解析到单词");
        		 } 
			}  
		}
         if (filePath.equals("ZCTC")) {
        	 for (File file2 : files) {
        		 HSSFWorkbook excelbook =  readExcel(file2);
        		 Map<String,Integer>  mapString=  getWordList(excelbook);
        		 String[] fileName = file2.getName().split("\\.");
        		 String excelName = fileName[0]+".xls"; 
        		 List<String> wordList = new ArrayList<>(); 
        		 for (String string : mapString.keySet()) {
        			 for (int i = 0; i < mapString.get(string); i++) {
        				 wordList.add(string); 
					}
        		 } 
        		 if (wordList.size() >0) { 
        			 getResultExcel(filePath,excelName,wordList);
        		 }else {
        			 System.out.println(filePath+"\\"+excelName+"没有解析到单词");
        		 } 
			}  
        	 
				
		}
         if (filePath.equals("FLOBtexts(1)")) {
	    	 Map<String, String> map = new HashMap<>();
	         for (File file2 : files) { 
	        	 String aaString =  file2.getName().substring(0, 6);
	        	 String fileStr = getStringFromFile(file2);
	        	 if (!map.containsKey(aaString)) {
	        		 map.put(aaString, "");
				} 
					String newString = map.get(aaString) + fileStr;
					map.put(aaString, newString); 
	         }
	         for (String  key : map.keySet()) {
	        	 List<String> wordList = new ArrayList<>();
	        	 String excelName = key+".xls";
	          			Document doc = Jsoup.parse(map.get(key)); 
	     				Elements masthead = doc.select("w");// class等于masthead的div标签 
	     				for (int i = 0; i < masthead.size() ; i++) {
	     					String string = masthead.get(i).text(); 
	     					 wordList.add(string); 
	     				} 
	        	 if (wordList.size() >0) { 
	        		 getResultExcel(filePath,excelName,wordList);
				}else {
					System.out.println(filePath+"\\"+excelName+"没有解析到单词");
				} 
	         }  
		} 
	}
     public static HSSFWorkbook readExcel(File file) {
         try {
             // 创建输入流，读取Excel
         	FileInputStream  is = new FileInputStream(file.getAbsolutePath());
             // jxl提供的Workbook类
         	HSSFWorkbook  workbook =  new HSSFWorkbook(is);
             // Excel的页签数量
         	is.close();
         	return workbook;
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } 
           catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }
     public static Map<String,Integer> getWordList(HSSFWorkbook workbook) {
    	 Map<String,Integer> tempMap = new TreeMap<>(); 
         HSSFSheet  sheet = workbook.getSheetAt(0);  
    	   for (int rowIndex = 17; rowIndex < sheet.getLastRowNum(); rowIndex++) {
    		   HSSFRow row = sheet.getRow(rowIndex);
    		   if (row.getCell(1).getCellTypeEnum()!= CellType.NUMERIC && row.getCell(1).getCellTypeEnum()!= CellType.BOOLEAN && row.getCell(1).getCellTypeEnum()!= CellType.FORMULA) {
  			
    			   tempMap.put((row.getCell(1).getStringCellValue()), (int) row.getCell(2).getNumericCellValue());
  		}
    	   }
    	   return tempMap;
      }
     private static void getResultExcel(String filePath, String excelName,
			List<String> wordList) throws IOException {
    	 Map<String, Integer> wordCountMap = getWordCountMap(wordList);
    	 int fileWordCount = 0;
    	 for (String string : wordCountMap.keySet()) {
    		 fileWordCount+= wordCountMap.get(string); 
		}
    	 System.out.println(fileWordCount);
    	 Map<Double, String> wordMap = new HashMap<>();
    	 for (String string : wordCountMap.keySet()) {
			double value = wordCountMap.get(string);
			while (wordMap.containsKey(value)) {
				value += 0.00001;
			}
			wordMap.put(value, string);
		}
    	 Map<Double,String> sortedMapByKey = new TreeMap<Double,String>();
    	 sortedMapByKey.putAll(wordMap);
    	 sortedMapByKey =  ((TreeMap) sortedMapByKey).descendingMap();
    	 Map<String, Integer> resultMap = new LinkedMap<>();
    	 for (double value : sortedMapByKey.keySet()) {
    		 resultMap.put(sortedMapByKey.get(value), (int)value);
		}  
    	 SetToExcel(filePath,excelName,resultMap); 
	}
	public static void SetToExcel(String filePath ,String filename ,Map<String, Integer>  needBuildMap) throws IOException {
     	//定义表头
     	String[] title={"N","Word","Freq.","%"};
     	//创建excel工作簿
     	HSSFWorkbook workbook=new HSSFWorkbook();
     	//创建工作表sheet
     	HSSFSheet sheet=workbook.createSheet();
     	//创建第一行
     	HSSFRow row=sheet.createRow(0);
     	HSSFCell cell=null;
     	//插入第一行数据的表头
     	for(int i=0;i<title.length;i++){
     	    cell=row.createCell(i);
     	    cell.setCellValue(title[i]);
     	}
     	 int fileWordCount = 0;
    	 for (String string : needBuildMap.keySet()) {
    		 fileWordCount+= needBuildMap.get(string); 
		}
    	 System.out.println(fileWordCount);
    	 int RowIndex = 0;
    	 for (String string : needBuildMap.keySet()) {
    		 if (string.equalsIgnoreCase(".") ||string.equalsIgnoreCase(",") ||string.equalsIgnoreCase("，")
				 ||string.contains("\"") ||string.contains("-") ||string.contains(":")
				 ||string.contains(")") ||string.contains("(") ||string.contains("：")
				 ||string.equalsIgnoreCase("'") ||string.contains("‘’") ||string.contains("’")
				 ||string.contains("“”")  ||string.contains("”") ||string.contains("?")
				 ||string.contains("!") ||string.equalsIgnoreCase("？")  ||string.contains("！")
				 ||string.contains("...")  ||string.contains("。")  ||string.contains("；")
				 ||string.contains(";") ||string.equalsIgnoreCase(" ") ||string.equalsIgnoreCase("")
				 ||string.contains("、")  ||string.equalsIgnoreCase("－") ||string.contains("》")
				 ||string.contains("《")||string.contains("@")||string.contains("$")
				 ||string.contains("（")||string.contains("%")||string.contains("^")
				 ||string.contains("）")||string.contains("&")||string.contains("*")
				 ||string.contains("{")||string.contains("``")||string.contains("''")
				 ||string.contains("}")||string.contains("''``")||string.contains("~")
				 ||string.contains("-") ||string.contains("#")||string.contains("--")
				 ||string.length() <1||string.contains("`")||string.equalsIgnoreCase("“")
				  ||string.contains(">") ||string.contains("<")||string.contains("0")
				  ||string.contains("1")||string.contains("2")||string.contains("3")
				   ||string.contains("4")||string.contains("5")||string.contains("6")
				    ||string.contains("7")||string.contains("8")||string.contains("9")
				  
				 )
    		 {
				continue;
			}
    		 RowIndex++;
    		 HSSFRow nrow=sheet.createRow(RowIndex);
     		 HSSFCell ncell = null;
     		 ncell=nrow.createCell(0);
 			 ncell.setCellValue(RowIndex);
 			 ncell=nrow.createCell(1);
			 ncell.setCellValue(string);
			 ncell=nrow.createCell(2); 
			 ncell.setCellValue(needBuildMap.get(string)); 
			 double percent = needBuildMap.get(string)*100.0/fileWordCount;
			 BigDecimal  bd = new BigDecimal(percent)  ; 
			 bd = bd.setScale(16,BigDecimal.ROUND_HALF_UP); 
			 ncell=nrow.createCell(3); 
			 ncell.setCellValue(Double.parseDouble(bd.toString()));  
		}
    	 RowIndex++;
		 HSSFRow nrow=sheet.createRow(RowIndex);
 		 HSSFCell ncell = null;
 		 ncell=nrow.createCell(0);
			 ncell.setCellValue(RowIndex);
			 ncell=nrow.createCell(1);
		 ncell.setCellValue("allword");
		 ncell=nrow.createCell(2); 
		 ncell.setCellValue(fileWordCount); 
		 double percent = 100;
		 BigDecimal  bd = new BigDecimal(percent)  ; 
		 bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP); 
		 ncell=nrow.createCell(3); 
		 ncell.setCellValue(Double.parseDouble(bd.toString())); 
      
     	//创建excel文件
     	File file1=new File("C://老师需求文档//OneDrive//"+filePath);
     	if (!file1.exists()) {
 			file1.mkdirs();
 		}
     	File file=new File("C://老师需求文档//OneDrive//"+filePath+"//"+filename);
     	try {
     	    file.createNewFile();
     	    //将excel写入
     	    FileOutputStream stream=new  FileOutputStream(file);
     	    workbook.write(stream);
     	    stream.close();
     	} catch (IOException e) {
     	    e.printStackTrace();
     	} 
      }
	private static Map<String, Integer> getWordCountMap(List<String> wordList) {
		Map<String, Integer> wordMap = new LinkedMap<>();
		for (String string : wordList) {
			string = string.toUpperCase();
			if (!wordMap.containsKey(string)) {
				wordMap.put(string, 1);
			}
			else {
				int wordCount = wordMap.get(string)+1; 
				wordMap.replace(string, wordCount);
			}
		}
		return wordMap;
	}

	private static String getStringFromFile(File file2) throws IOException {
		InputStream inputStream1 = new FileInputStream(file2);
    	BufferedReader br = null;
    	br = new BufferedReader(new InputStreamReader(inputStream1, "utf-8"));
    	StringBuilder sb = new StringBuilder();
    	String line;
    	while ((line = br.readLine()) != null)
    	{
    		sb.append(line);
    	}
    	br.close();
    	inputStream1.close(); 
    	String Text = sb.toString();
    	return Text;
	}
	

	
}
