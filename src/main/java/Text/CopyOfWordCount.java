package Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.math.BigDecimal; 
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import java.util.TreeMap; 
import org.apache.commons.collections4.map.LinkedMap; 
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 

public class CopyOfWordCount {
	public static void main(String[] args) throws IOException {
		String FilePath = "C:\\Users\\superadmin.DLHZ106\\Desktop\\CORPS_II_RELEASE202\\data\\";
		String FileName = FilePath + "Result.xlsx";
		File file = new File(FilePath);
		File[] files = file.listFiles();
		System.out.println("files Count" + files.length);
		List<String> wordList = new ArrayList<>();
		for (File file2 : files) {
			File file3 = new File(file2.getPath());
			File[] file4 = file3.listFiles();
			for (File file5 : file4) {   
				String fileStr = getStringFromFile(file5);
				String[] wordStr = fileStr.split(" ");
				for (String string : wordStr) {
					wordList.add(string);
				} 
			}
		}
		if (wordList.size() > 0) {
			getResultExcel(FileName,   wordList);
		} 

	}

	 

	private static void getResultExcel(String filePath, 
			List<String> wordList) throws IOException {
		Map<String, Integer> wordCountMap = getWordCountMap(wordList);
		int fileWordCount = 0;
		for (String string : wordCountMap.keySet()) {
			fileWordCount += wordCountMap.get(string);
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
		Map<Double, String> sortedMapByKey = new TreeMap<Double, String>();
		sortedMapByKey.putAll(wordMap);
		sortedMapByKey = ((TreeMap) sortedMapByKey).descendingMap();
		Map<String, Integer> resultMap = new LinkedMap<>();
		for (double value : sortedMapByKey.keySet()) {
			resultMap.put(sortedMapByKey.get(value), (int) value);
		}
		SetToExcel(filePath, resultMap);
	}

	public static void SetToExcel(String filePath ,
			Map<String, Integer> needBuildMap) throws IOException {
		// 定义表头
		String[] title = { "N", "Word", "Freq.", "%" };
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
		int fileWordCount = 0;
		for (String string : needBuildMap.keySet()) {
			fileWordCount += needBuildMap.get(string);
		}
		System.out.println(fileWordCount);
		int RowIndex = 0;
		for (String string : needBuildMap.keySet()) {
			if (string.equalsIgnoreCase(".") || string.equalsIgnoreCase(",")
					|| string.equalsIgnoreCase("，") || string.contains("\"")
					|| string.contains("-") || string.contains(":")
					|| string.contains(")") || string.contains("(")
					|| string.contains("：") || string.equalsIgnoreCase("'")
					|| string.contains("‘’") || string.contains("’")
					|| string.contains("“”") || string.contains("”")
					|| string.contains("?") || string.contains("!")
					|| string.equalsIgnoreCase("？") || string.contains("！")
					|| string.contains("...") || string.contains("。")
					|| string.contains("；") || string.contains(";")
					|| string.equalsIgnoreCase(" ")
					|| string.equalsIgnoreCase("") || string.contains("、")
					|| string.equalsIgnoreCase("－") || string.contains("》")
					|| string.contains("《") || string.contains("@")
					|| string.contains("$") || string.contains("（")
					|| string.contains("%") || string.contains("^")
					|| string.contains("）") || string.contains("&")
					|| string.contains("*") || string.contains("{")
					|| string.contains("``") || string.contains("''")
					|| string.contains("}") || string.contains("''``")
					|| string.contains("~") || string.contains("-")
					|| string.contains("#") || string.contains("--")
					|| string.length() < 1 || string.contains("`")
					|| string.equalsIgnoreCase("“") || string.contains(">")
					|| string.contains("<") || string.contains("0")
					|| string.contains("1") || string.contains("2")
					|| string.contains("3") || string.contains("4")
					|| string.contains("5") || string.contains("6")
					|| string.contains("7") || string.contains("8")
					|| string.contains("9")|| string.contains("/")

			) {
				continue;
			}
			RowIndex++;
			XSSFRow nrow = sheet.createRow(RowIndex);
			XSSFCell ncell = null;
			ncell = nrow.createCell(0);
			ncell.setCellValue(RowIndex);
			ncell = nrow.createCell(1);
			ncell.setCellValue(string);
			ncell = nrow.createCell(2);
			ncell.setCellValue(needBuildMap.get(string));
			double percent = needBuildMap.get(string) * 100.0 / fileWordCount;
			BigDecimal bd = new BigDecimal(percent);
			bd = bd.setScale(16, BigDecimal.ROUND_HALF_UP);
			ncell = nrow.createCell(3);
			ncell.setCellValue(Double.parseDouble(bd.toString()));
		}
		RowIndex++;
		XSSFRow nrow = sheet.createRow(RowIndex);
		XSSFCell ncell = null;
		ncell = nrow.createCell(0);
		ncell.setCellValue(RowIndex);
		ncell = nrow.createCell(1);
		ncell.setCellValue("allword");
		ncell = nrow.createCell(2);
		ncell.setCellValue(fileWordCount);
		double percent = 100;
		BigDecimal bd = new BigDecimal(percent);
		bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
		ncell = nrow.createCell(3);
		ncell.setCellValue(Double.parseDouble(bd.toString()));

		// 创建excel文件
		File file1 = new File( filePath);
		 
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

	private static Map<String, Integer> getWordCountMap(List<String> wordList) {
		Map<String, Integer> wordMap = new LinkedMap<>();
		for (String string : wordList) {
			string = string.toLowerCase();
			if (!wordMap.containsKey(string)) {
				wordMap.put(string, 1);
			} else {
				int wordCount = wordMap.get(string) + 1;
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
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		inputStream1.close();
		String Text = sb.toString();
		return Text;
	}

}
