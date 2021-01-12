package Text;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.jce.provider.asymmetric.ec.Signature.ecCVCDSA; 
 
public class ReadExcelFromBNC {
    public static void main(String[] args) { 
        File file = new File("C:\\老师需求文档\\论文A-R"); 
        File[] files = file.listFiles();
        int MinWord = 3000;
        String txtType = "FLOB";
        String tempType = "BNC";
        //从excel中读取出来
        Map<String,HSSFWorkbook>   excelMap = new HashMap<String, HSSFWorkbook>()  ;
        for (int i = 0; i < files.length; i++) {
        	String filename = files[i].getName();
			if (filename.contains(txtType)||filename.contains(tempType)) {
				HSSFWorkbook workbook  = readExcel(files[i]);
				excelMap.put( filename, workbook) ;
			}  
		}  
         
        //将excel中的单词和其所对应的位置缓存起来
        Map<String, Map<String, String>> allMap = new HashMap<String, Map<String,String>>();
        for (String filename : excelMap.keySet()) {
        	if (!filename.contains(tempType)) {
        		Map<String, String> wordMap = getWordAndFre(filename,excelMap,2); 
        		allMap.put(filename, wordMap);  
        		System.out.println(filename+":done");  
			}
   		}
        List<String> tempList =getWordList (tempType+".xls",excelMap ); 
        Map<String , Integer> tempMap  = new HashMap<String , Integer>(); 
		 
        System.out.println("模板单词数："+tempList.size());
        
        
        Map<String, Map<String, String>> needToBuild = new HashMap<String, Map<String,String>>();
        for (int i = 0; i < tempList.size(); i++) {
			String wordTemp = tempList.get(i); 
			if (wordTemp.equals(1)) {
				System.out.println();
			}
			for (String fileName : allMap.keySet()) {
				Map<String, String> map = allMap.get(fileName);
				boolean isWordExit = false;
				String equalsWord= "";
				for (String word : map.keySet()) { 
					String wordString= "";
					if(wordTemp.contains("'"))  
						  wordString = "'"+word;  
					else  
						wordString= word;   
					if (wordString.equalsIgnoreCase(wordTemp)) {
						isWordExit = true;
						equalsWord = word;
					}
				}
				if (!needToBuild.containsKey(fileName)) {
					Map<String, String> mapset = new LinkedHashMap<String, String>();
					needToBuild.put(fileName, mapset);
				}
				 
				if (needToBuild.get(fileName).size()>=MinWord) {
					break;
				}
				if (isWordExit) {
//					if (equalsWord.equalsIgnoreCase("have")) {
//						int a = 0;
//						System.out.println();
//					}
//					if (equalsWord.equalsIgnoreCase("HAVE")) {
//						int a = 0;
//						System.out.println();
//					}
//					if (wordTemp.equalsIgnoreCase("HAVE")) {
//						int a = 0;
//						System.out.println();
//					}
					String valueString = map.get(equalsWord);
					if(wordTemp.contains("'"))  
						valueString = valueString.replace(equalsWord, "'"+equalsWord);
					needToBuild.get(fileName).put(wordTemp, valueString); 
//					System.out.println(fileName +" wordTemp: "+wordTemp+" equalsWord: "+equalsWord+":fre: "+needToBuild.get(fileName).get(wordTemp));
				}
				else {
					needToBuild.get(fileName).put(wordTemp, "1"); 
				} 
			}
        }
        for (String filename : needToBuild.keySet()) {
        	for (String key : needToBuild.get(filename).keySet()) {
				System.out.println(filename +"  "+key+":fre: "+needToBuild.get(filename).get(key));
			} 
        	SetToExcel(filename,needToBuild.get(filename));
		}
//        //循环，筛选出其他文档没有的单词，记录下他们的index  接下来移除
//        String templateName = allMap.keySet().iterator().next();
//        Map<String, Integer> templateMap = allMap.get(templateName);
//        for (String temWord : templateMap.keySet()) {
//        	for (String mapName : allMap.keySet()) {
//        		boolean isWordExist = false;
//        		Map<String, Integer> testMap = allMap.get(mapName);
//        		for (String word : testMap.keySet()) {
//					if (temWord.equals(word)) {
//						isWordExist = true;
//					}
//				}
//				if (!isWordExist) {
//					if (needToRemove.containsKey(templateName)) {
//						needToRemove.get(templateName).put(temWord, testMap.get(temWord));
//					}else {
//						Map<String, Integer> removeMap = new HashMap<String, Integer>();
//						removeMap.put(temWord, testMap.get(temWord));
//						needToRemove.put(templateName, removeMap);
//					}
//					
//				}
//			} 
//        }
//        for (String needRemoveWord : needToRemove.get(templateName).keySet()) {
//        	templateMap.remove(needRemoveWord);
//		}
//        for (String mapName : allMap.keySet()) {
//    		Map<String, Integer> testMap = allMap.get(mapName);
//    		for (String word : testMap.keySet()) {
//    			boolean isWordExist = false;
//    			for (String temWord : templateMap.keySet()) {
//					if (temWord.equals(word)) {
//						isWordExist = true;
//					}
//				}
//    			if (!isWordExist) {
//    				if (needToRemove.containsKey(mapName)) {
//    					needToRemove.get(mapName).put(word, testMap.get(word));
//    				}else {
//    					Map<String, Integer> removeMap = new HashMap<String, Integer>();
//    					removeMap.put(word, testMap.get(word));
//    					needToRemove.put(mapName, removeMap);
//    				} 
//    			}
//			}
//		} 
//        System.out.println("文档单词数小于："+MinWord+"不做处理");
//        for (String mapName : needToRemove.keySet()) {
//        	if (mapName.equals(templateName)) {
//        		System.out.println(mapName+"需要移除的单词数:"+needToRemove.get(mapName).size()+"剩下的单词数："+(allMap.get(mapName).size() ));
//			}else {
//				System.out.println(mapName+"需要移除的单词数:"+needToRemove.get(mapName).size()+"剩下的单词数："+(allMap.get(mapName).size()-needToRemove.get(mapName).size()));
//			}
//		}
	}
    public static Map<String, String> getWordAndFre(String filename ,Map<String,HSSFWorkbook>   excelMap,int FreIndex) {
    	 Map<String, String> wordMap = new HashMap<String, String>();
    	 HSSFWorkbook workbook= excelMap.get(filename);
  	     HSSFSheet sheet = workbook.getSheetAt(0);
  	     for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
  		 HSSFRow row = sheet.getRow(rowIndex);
  		 if (row!=null && row.getCell(1) !=null &&row.getCell(FreIndex) !=null &&  row.getCell(1).getCellTypeEnum() == CellType.STRING ) {
  			   String rowString = "";
  			   for (int i = 0; i < row.getLastCellNum(); i++) { 
				  if (row.getCell(i) !=null) {
					  if (row.getCell(i).getCellTypeEnum() == CellType.STRING) {
						  	rowString += row.getCell(i).getStringCellValue()+",";
					  	}
					  if (row.getCell(i).getCellTypeEnum() == CellType.NUMERIC) {
							rowString += row.getCell(i).getNumericCellValue()+",";
						}
					  if (row.getCell(i).getCellTypeEnum() == CellType.BOOLEAN) {
							rowString += row.getCell(i).getBooleanCellValue()+",";
						}
				 }
				  else {
					  rowString +=  " ,";
				}
				wordMap.put(row.getCell(1).getStringCellValue(),rowString);
  			   }
		  }
//  		   String string = "";
//  		   for (int j = 0; j < row.getLastCellNum(); j++) {
//  			   if (row.getCell(j) == null) {
//  				   continue;
//  			   }
//  			   if (row.getCell(j).getCellTypeEnum()==CellType.NUMERIC) {
//  				   string +=row.getCell(j).getNumericCellValue() ;
//  			   }	 
//  			   if (row.getCell(j).getCellTypeEnum()==CellType.STRING) {
//  				   string +=row.getCell(j).getStringCellValue() ;
//  			   } 
//  		   }  
//  		   System.out.println(string);
  	   }
  	   return wordMap;
    }
    public static Map<String ,Integer > getFreAndWord(String filename ,Map<String,HSSFWorkbook>   excelMap,int FreIndex) {
       Map<String ,Integer > tempMap = new HashMap<String ,Integer >();
       HSSFWorkbook workbook= excelMap.get(filename);
  	   HSSFSheet sheet = workbook.getSheetAt(0);
  	   for (int rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
  		   HSSFRow row = sheet.getRow(rowIndex);
  		   if (row.getCell(1).getCellTypeEnum()!= CellType.NUMERIC && row.getCell(1).getCellTypeEnum()!= CellType.BOOLEAN && row.getCell(1).getCellTypeEnum()!= CellType.FORMULA) {
			
  			   tempMap.put(row.getCell(1).getStringCellValue(),    (int)row.getCell(FreIndex).getNumericCellValue()); 
		}
  	   }
  	   return tempMap;
    }
    public static void SetToExcel(String filename ,Map<String, String>  needBuildMap) {
    	//定义表头
    	String[] title={"N","Word","Freq.","%","Texts","%","Lemmas","Set"};
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
    	//写入数据
    	int RowIndex = 0;
    	for (String key : needBuildMap.keySet()) {
    		if (key.equalsIgnoreCase("have")) {
				int a = 0;
				System.out.println();
			}
    		String[] str = needBuildMap.get(key).split(",");
    		RowIndex++;
    		HSSFRow nrow=sheet.createRow(RowIndex);
    		 HSSFCell ncell = null;
    		 ncell=nrow.createCell(0);
			 ncell.setCellValue(RowIndex);
    		 if (str.length>1) { 
				 ncell=nrow.createCell(1);
				 ncell.setCellValue(str[1]); 
				 if (str.length>3) {
					 ncell=nrow.createCell(2);
					 if (!str[2].isEmpty()&&!str[2].equals(" ")) {
						 
						 ncell.setCellValue(Double.parseDouble(str[2]) );  
						 
					 }
				 }
					
	    		 if (str.length>3) {
	    			 ncell=nrow.createCell(3);
	    			 if (!str[3].isEmpty()&&!str[3].equals(" ")) {
	    				    				 
	    				 ncell.setCellValue(Double.parseDouble(str[3]));
	    			 }
	    		 }
	    		 if (str.length>4) {
	    			 ncell=nrow.createCell(4);
	    			 if (!str[4].isEmpty()&&!str[4].equals(" ")) {
	    				 			 
	    				 ncell.setCellValue(Double.parseDouble(str[4]));
	    			 }
	    		 }
	    		 if (str.length>5) {
	    			 ncell=nrow.createCell(5);
	    			 if (!str[5].isEmpty()&&!str[5].equals(" ")) {
	    				 	 
	    				 ncell.setCellValue(Double.parseDouble(str[5]));
	    			 }
	    		 }
	    		 if (str.length>6) {
	    			 ncell=nrow.createCell(6); 
	    				 ncell.setCellValue(str[6]);
	    		 }
	    		 if (str.length>7) {
	    			 ncell=nrow.createCell(7);
	    			 ncell.setCellValue(str[7]);
	    		 }
			}else {
				ncell=nrow.createCell(1);
				ncell.setCellValue(key);
				ncell=nrow.createCell(2); 
				ncell.setCellValue(1); 
				ncell=nrow.createCell(3); 
				ncell.setCellValue(100); 
				ncell=nrow.createCell(5); 
				ncell.setCellValue(100); 
			} 
    	}
    	//创建excel文件
    	File file=new File("C://老师需求文档//Result//"+filename);
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
    public static List<String> getWordList(String filename ,Map<String,HSSFWorkbook>   excelMap ) {
        List<String> tempMap = new ArrayList<String>();
        HSSFWorkbook workbook= excelMap.get(filename);
   	   HSSFSheet sheet = workbook.getSheetAt(0);
   	   for (int rowIndex = 2; rowIndex < sheet.getLastRowNum(); rowIndex++) {
   		   HSSFRow row = sheet.getRow(rowIndex);
   		   if (row.getCell(1).getCellTypeEnum()!= CellType.NUMERIC && row.getCell(1).getCellTypeEnum()!= CellType.BOOLEAN && row.getCell(1).getCellTypeEnum()!= CellType.FORMULA) {
 			
   			   tempMap.add(row.getCell(1).getStringCellValue());
 		}
   	   }
   	   return tempMap;
     }
    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
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
}