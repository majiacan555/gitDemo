package SimHashService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.lucene.store.Directory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import LiberyUtils.LiberyCache;
import luceneIK.CEData;

public class HLMGetExcelResult {

	public static void main(String[] args) throws IOException {
		String author1 = "Hawkes"; // another Hawkes
		String author2 = "Yang"; // another Hawkes
		double SimPerCent = 0.9;
		String sourcePath = "E:\\数据分析\\HLMSimilarity\\" + author1 + "\\";
		List<HLMExcelModel> resultList = new ArrayList<HLMExcelModel>();
		String SaveFileName = author1+"_"+SimPerCent+".xls";
		String SavePath = "E:\\数据分析\\HLMexcelResult\\"+SaveFileName;
		File sourceFile = new File(sourcePath);
		File[] fileList = sourceFile.listFiles();
		Map<Integer, File> fileMap = new TreeMap<Integer, File>();
		for (File file : fileList) {
			String fileName = file.getName();
		int key = Integer.parseInt(   fileName.split("\\.")[0].split(" ")[1])  ;
		    fileMap.put(key, file);
		}
		for (File file : fileMap.values()) {
			String fileName = file.getName();
			String Chapter = fileName.split("\\.")[0];
			System.out.println(fileName);
			List<SimHashModel> modelList = ReadExcelFromLocal(file);
			System.out.println(modelList.size()); 
			for (SimHashModel model : modelList) {
				if (model.getSimilarPercent()>=SimPerCent&& model.gethChineseData().contains("？")) { 
					int cCount =LiberyCache. getWordCount(model.gethChineseData(),"？");
					int eCount1 =LiberyCache. getWordCount(model.gethEnglishData(),"?"); 
					int eCount2 =LiberyCache. getWordCount(model.getyEnglishData(),"?"); 
					HLMExcelModel hlmModel= new HLMExcelModel();
					hlmModel.setCharpter(Chapter);
					hlmModel.setAuthor1(author1);
					hlmModel.setAuthor2(author2);
					hlmModel.setChineseData(model.gethChineseData());
					hlmModel.setEnglishData1(model.gethEnglishData());
					hlmModel.setEnglishData2(model.getyEnglishData());
					hlmModel.setChineseCount(cCount);
					hlmModel.setEnglishCount1(eCount1);
					hlmModel.setEnglishCount2(eCount2);
					resultList.add(hlmModel);
				}
			} 
		} 
		SetToExcel(SavePath,resultList);
	}
	public static void SetToExcel(String filePath ,
			List<HLMExcelModel> List) throws IOException {
		// 定义表头
		String[] title = { "章节", "译本一", "译文一","译本二", "译文二", "原文", "译文一问号","译文二问号", "原文问号" };
		// 创建excel工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建工作表sheet
		XSSFSheet sheet = workbook.createSheet();
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = null;
		// 插入第一行数据的表头
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		} 
		int RowIndex = 0;
		for (HLMExcelModel Data : List) { 
			RowIndex++;
			XSSFRow nrow = sheet.createRow(RowIndex);
			XSSFCell ncell = null;
			ncell = nrow.createCell(0);
			ncell.setCellValue(Data.getCharpter());
			ncell = nrow.createCell(1);
			ncell.setCellValue(Data.getAuthor1());
			ncell = nrow.createCell(2);
			ncell.setCellValue(Data.getEnglishData1()); 
			ncell = nrow.createCell(3);
			ncell.setCellValue(Data.getAuthor2());
			ncell = nrow.createCell(4);
			ncell.setCellValue(Data.getEnglishData2());
			ncell = nrow.createCell(5);
			ncell.setCellValue(Data.getChineseData());
			ncell = nrow.createCell(6);
			ncell.setCellValue(Data.getEnglishCount1());
			ncell = nrow.createCell(7);
			ncell.setCellValue(Data.getEnglishCount2());
			ncell = nrow.createCell(8);
			ncell.setCellValue(Data.getChineseCount());
		}  
		// 创建excel文件
		File file1 = new File( filePath); 
		if (!file1.getParentFile().exists())
			file1.getParentFile().mkdirs(); 
		try {
			file1.createNewFile();
			// 将excel写入
			FileOutputStream stream = new FileOutputStream(file1);
			workbook.write(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static List<SimHashModel> ReadExcelFromLocal(File file) { 
		List<SimHashModel> simList = new ArrayList<SimHashModel>();
		HSSFWorkbook workbook = LiberyCache.readExcel(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			SimHashModel model = new SimHashModel(); 
			model.setNumID((int) row.getCell(0).getNumericCellValue());
			model.sethChineseData(row.getCell(1).getStringCellValue());
			model.sethEnglishData(row.getCell(2).getStringCellValue());
			model.setyChineseData(row.getCell(3).getStringCellValue());
			model.setyEnglishData(row.getCell(4).getStringCellValue());
			model.setSimilarPercent(row.getCell(5).getNumericCellValue());
			simList.add(model);
		}
		return simList;
	}

}
