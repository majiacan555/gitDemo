package LiberyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import SimHashService.SimHashModel;

public class SetToExcel {
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
	public static void SetToExcelSimHashHLM(String SavePath   ,List<SimHashModel> resultList ) throws IOException {
     	//定义表头
     	String[] title={"id","cWord","eWord","cWord2","eWord2","similarity"};
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
    	 int RowIndex = 0;
    	 for (SimHashModel model : resultList) { 
    		 RowIndex++;
    		 HSSFRow nrow=sheet.createRow(RowIndex);
     		 HSSFCell ncell = null;
     		 ncell=nrow.createCell(0);
 			 ncell.setCellValue(model.getNumID());
 			 ncell=nrow.createCell(1);
			 ncell.setCellValue(model.getyChineseData());
			 ncell=nrow.createCell(2); 
			 ncell.setCellValue(model.getyEnglishData()); 
			 ncell=nrow.createCell(3);
			 ncell.setCellValue(model.gethChineseData());
			 ncell=nrow.createCell(4); 
			 ncell.setCellValue(model.gethEnglishData()); 
			 ncell=nrow.createCell(5);
			 ncell.setCellValue(model.getSimilarPercent());
		} 
     	//创建excel文件
     	File file=new File(SavePath);
     	if (!file.getParentFile().exists()) {
     		file.getParentFile().mkdirs();
 		} 
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
}
