package luceneIK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap; 
import org.apache.lucene.analysis.Analyzer; 
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser; 
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs; 
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import org.wltea.analyzer.lucene.IKAnalyzer;

import LiberyUtils.LiberyCache;
 

public class WordSearch {
	private static String DISC_URL = "/home/indexData/data";

	static {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			DISC_URL = "E:\\CEData\\ChineseEnglisnData1";
		} else {
			DISC_URL = "/home/indexData/data";
		}
	}
	public static void main(String[] args) throws Exception {
		String author = "Yang";
		String savePath  = "E:\\" + author + ".xlsx";
		highLightSearch("Chinese","？","hongloumeng",author,savePath);
	} 
	
	public static void highLightSearch(String ChineseOrEnglish,
		String SearchWord,    String TextType,String author,String savePath) throws Exception {
		String filed = ChineseOrEnglish + "Data"; 
		//根据关键字构造一个数组 
		String[] keyword = new String[]{SearchWord,TextType};
		//同时声明一个与之对应的字段数组 
		String[] fields = {filed,"Memo"}; 
		//声明BooleanClause.Occur[]数组,它表示多个条件之间的关系 
		BooleanClause.Occur[] flags=new BooleanClause.Occur[]{BooleanClause.Occur.MUST,BooleanClause.Occur.MUST}; 
		Analyzer analyzer = new IKAnalyzer(true);
		//用MultiFieldQueryParser得到query对象 
		Query query1 = MultiFieldQueryParser.parse(Version.LUCENE_36,keyword, fields, flags, analyzer); 
		File indexDir = new File(DISC_URL); // 索引目录
		Directory dir = FSDirectory.open(indexDir);// 根据索引目录创建读索引对象
		IndexReader reader = IndexReader.open(dir);// 搜索对象创建
		IndexSearcher searcher = new IndexSearcher(reader);   
		TopDocs topDocs = searcher.search(query1, 100000);
		ScoreDoc[] sd = topDocs.scoreDocs; 
		System.out.println(sd.length); 
		Map<Integer, List<CEData>> ResultMap = new TreeMap<Integer,  List<CEData>>();
		int Count = 0;
		for (int i = 0; i < sd.length; i++) {
			Document doc = searcher.doc(sd[i].doc);  
			String English = doc.get("EnglishData").toString();
			String Chinese = doc.get("ChineseData").toString();
			String URL = doc.get("URL").toString(); 
			if ((ChineseOrEnglish.equals("Chinese") &&!Chinese.contains(SearchWord))
					|| (ChineseOrEnglish.equals("English") &&!English.contains(SearchWord))) { 
					continue;
			} 
			if (English.length() > 15 && Chinese.length() > 5 ) { 
				String Title =  doc.get("title").toString();
				String Author =  doc.get("URL").toString();
				if (!author.isEmpty() && author!=null && !author.equals(Author)) {
					continue;
				}
				String[] titles = Title.split(" ");
				Integer value = 0;
				if (titles.length>=2) {
					value = Integer.parseInt(titles[1]);
				}
				if (!ResultMap.containsKey(value)) {
					ResultMap.put(value, new ArrayList<CEData>())  ;
				}
				CEData ceData = new CEData();
				ceData.setChinesedata(Chinese);
				ceData.setEnglishdata(English);
				ceData.setUrl(Author);  
				ceData.setTitle(Title); 
				ResultMap.get(value).add(ceData);   
				Count++;
			}  
		} 
		List<CEData> list = new ArrayList<CEData>();
		for (Integer key : ResultMap.keySet()) {
			for (CEData data : ResultMap.get(key)) {  
				int cCount =LiberyCache. getWordCount(data.getChinesedata(),SearchWord);
				int eCount =LiberyCache. getWordCount(data.getEnglishdata(),"?"); 
				data.setCcount(cCount);
				data.setEcount(eCount); 
				list.add(data);
			}  
		}  
		SetToExcel(savePath,list);
	}
	public static void SetToExcel(String filePath ,
			List<CEData> List) throws IOException {
		// 定义表头
		String[] title = { "章节", "译本", "译文", "原文", "译文问号", "原文问号" };
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
		for (CEData Data : List) { 
			RowIndex++;
			XSSFRow nrow = sheet.createRow(RowIndex);
			XSSFCell ncell = null;
			ncell = nrow.createCell(0);
			ncell.setCellValue(Data.getTitle());
			ncell = nrow.createCell(1);
			ncell.setCellValue(Data.getUrl());
			ncell = nrow.createCell(2);
			ncell.setCellValue(Data.getEnglishdata()); 
			ncell = nrow.createCell(3);
			ncell.setCellValue(Data.getChinesedata());
			ncell = nrow.createCell(4);
			ncell.setCellValue(Data.getEcount());
			ncell = nrow.createCell(5);
			ncell.setCellValue(Data.getCcount());
		}  
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

	
}

 
