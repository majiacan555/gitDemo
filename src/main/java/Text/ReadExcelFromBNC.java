package Text;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.math.BigDecimal;
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

import com.gargoylesoftware.htmlunit.javascript.host.arrays.Int32Array;

import LiberyUtils.LiberyCache;
 
public class ReadExcelFromBNC {
	static String filePath = "FROWNresult";
    public static void main(String[] args) throws IOException { 
    	File file = new File("C:\\��ʦ�����ĵ�\\OneDrive\\"+filePath); 
        File[] files = file.listFiles();
        int MinWord = 3000;
        String txtType = "FROWN";
        String tempType = "BNC";
        //��excel�ж�ȡ����
        Map<String,HSSFWorkbook>   excelMap = new HashMap<String, HSSFWorkbook>()  ;
        for (int i = 0; i < files.length; i++) {
        	String filename = files[i].getName();
			if (filename.contains("0")||filename.contains("1")||filename.contains("2")||filename.contains(txtType)||filename.contains(tempType)) {
				HSSFWorkbook workbook  =LiberyCache. readExcel(files[i]);
				excelMap.put( filename, workbook) ;
			}  
		}  
         
        //��excel�еĵ��ʺ�������Ӧ��λ�û�������
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
        System.out.println("ģ�嵥������"+tempList.size()); 
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
					if(wordTemp.contains("'")&& !word.contains("'"))  
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
					String valueString = map.get(equalsWord);
					if(wordTemp.contains("'")&& !valueString.contains("'"))  
						valueString = valueString.replace(equalsWord, "'"+equalsWord);
					needToBuild.get(fileName).put(wordTemp, valueString);  
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
	}
    public static Map<String, String> getWordAndFre(String filename ,Map<String,HSSFWorkbook>   excelMap,int FreIndex) {
    	 Map<String, String> wordMap = new HashMap<String, String>();
    	 HSSFWorkbook workbook= excelMap.get(filename);
  	     HSSFSheet sheet = workbook.getSheetAt(0);
  	     for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
  		 HSSFRow row = sheet.getRow(rowIndex);
  		 if (row!=null && row.getCell(1) !=null &&row.getCell(FreIndex) !=null &&  row.getCell(1).getCellTypeEnum() == CellType.STRING ) {
  			   String rowString = "";
  			   for (int i = 0; i < 7; i++) { 
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
    public static void SetToExcel(String filename ,Map<String, String>  needBuildMap) throws IOException {
    	//�����ͷ
    	String[] title={"N","Word","Freq.","%","Texts","%","Lemmas","Set"};
    	//����excel������
    	HSSFWorkbook workbook=new HSSFWorkbook();
    	//����������sheet
    	HSSFSheet sheet=workbook.createSheet();
    	//������һ��
    	HSSFRow row=sheet.createRow(0);
    	HSSFCell cell=null;
    	//�����һ�����ݵı�ͷ
    	for(int i=0;i<title.length;i++){
    	    cell=row.createCell(i);
    	    cell.setCellValue(title[i]);
    	}
    	//д������
    	int wordCount = 0;
    	for (String key : needBuildMap.keySet()) { 
    		if (needBuildMap.get(key).contains("Freq.")) {
				continue;
			}
    		String[] str = needBuildMap.get(key).split(","); 
    		 if (str.length>1) {  
				 if (str.length>3) {
					 
					 if (!str[2].isEmpty()&&!str[2].equals(" ")) { 
						 try { 
							 wordCount +=Double.parseDouble(str[2]) ;   
						} catch (Exception e) {
							System.out.println(str[2]);
						}
					 }
				 }  
			}else {
				wordCount +=1;
			} 
    	}
    	System.out.println("�ܵ�������"+wordCount);
    	int RowIndex = 0;
    	for (String key : needBuildMap.keySet()) { 
    		if (needBuildMap.get(key).contains("Freq.")) {
				continue;
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
						 try { 
							 ncell.setCellValue(Double.parseDouble(str[2]) );   
						} catch (Exception e) {
							System.out.println(str[2]);
						}
					 }
				 } 
	    		 if (str.length>3) {
	    			 ncell=nrow.createCell(3); 
	    			 double percent = Double.parseDouble(str[2])*100.0/wordCount;
	    			 BigDecimal  bd = new BigDecimal(percent)  ; 
	    			 bd = bd.setScale(16,BigDecimal.ROUND_HALF_UP);  
	    				 ncell.setCellValue(Double.parseDouble(bd.toString())); 
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
				 double percent = 1*100.0/wordCount;
    			 BigDecimal  bd = new BigDecimal(percent)  ; 
    			 bd = bd.setScale(16,BigDecimal.ROUND_HALF_UP);  
//				ncell.setCellValue(Double.parseDouble(bd.toString()));  
				ncell.setCellValue(100);  
			} 
    	}
    	//����excel�ļ�
    	File file1=new File("C://��ʦ�����ĵ�//Result//"+filePath);
    	if (!file1.exists()) {
			file1.mkdirs();
		}
    	File file=new File("C://��ʦ�����ĵ�//Result//"+filePath+"//"+filename);
    	try {
    	    file.createNewFile();
    	    //��excelд��
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
        	HSSFSheet  sheet = workbook.getSheetAt(0);  
   	   for (int rowIndex = 2; rowIndex < sheet.getLastRowNum(); rowIndex++) {
   		   HSSFRow row = sheet.getRow(rowIndex);
   		   if (row.getCell(1).getCellTypeEnum()!= CellType.NUMERIC && row.getCell(1).getCellTypeEnum()!= CellType.BOOLEAN && row.getCell(1).getCellTypeEnum()!= CellType.FORMULA) {
 			
   			   tempMap.add(row.getCell(1).getStringCellValue());
 		}
   	   }
   	   return tempMap;
     }
    // ȥ��Excel�ķ���readExcel���÷�������ڲ���Ϊһ��File����
   
}