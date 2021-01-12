//package luceneIK;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.StringReader;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.math.RoundingMode;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//  
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Fieldable;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.IndexWriterConfig.OpenMode;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.queryParser.MultiFieldQueryParser;
//import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.search.BooleanClause;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.PrefixQuery;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.search.TopScoreDocCollector;
//import org.apache.lucene.search.WildcardQuery;
//import org.apache.lucene.search.highlight.Highlighter;
//import org.apache.lucene.search.highlight.QueryScorer;
//import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//
//import HttpClientDemo.AddData;
//import Model.Model;
//import Model.ParFre;
//import Model.RelationModel;
//  
//public class Search {
//      
//    private static String DISC_URL = "/home/indexData/data";
//      
//    static {
//        String os = System.getProperty("os.name"); 
//        if(os.toLowerCase().startsWith("win")){ 
//            DISC_URL = "D:\\CEData\\ChineseEnglisnData2";
//        }
//        else{
//            DISC_URL ="/home/indexData/data";
//        }
//    }
//         
//    public static void highLightSearch(String filed,String TextType, String SearchWord  ) throws Exception{
//    	String[] keyword = new String[]{SearchWord,TextType};
//		//同时声明一个与之对应的字段数组 
//		String[] fields = {filed,"Memo"}; 
//		//声明BooleanClause.Occur[]数组,它表示多个条件之间的关系 
//				BooleanClause.Occur[] flags=new BooleanClause.Occur[]{BooleanClause.Occur.MUST,BooleanClause.Occur.MUST}; 
//				Analyzer analyzer = new IKAnalyzer(true);
//				//用MultiFieldQueryParser得到query对象 
//				Query query1 = MultiFieldQueryParser.parse(Version.LUCENE_36,keyword, fields, flags, analyzer); 
//				File indexDir = new File(DISC_URL); // 索引目录
//				Directory dir = FSDirectory.open(indexDir);// 根据索引目录创建读索引对象
//				IndexReader reader = IndexReader.open(dir);// 搜索对象创建
//				IndexSearcher searcher = new IndexSearcher(reader);  
//        Query query = queryParser.parse(keyWord); 
//        TopDocs topDocs = searcher.search(query, 100000); 
//        ScoreDoc[] sd = topDocs.scoreDocs;
//        for (int i = 0; i < sd.length; i++) {
//        	Document doc = searcher.doc(sd[i].doc); 
//			System.out.println(doc.get("EnglishData").toString()); 
//			System.out.println(doc.get("ChineseData").toString()); 
//		}
//        System.out.println("搜索到："+sd.length); 
//        searcher.close(); 
//    }
//    public static void main(String[] args) throws Exception {
//		String filed  = "ChineseData";
//		String keyWord = "？";
//		highLightSearch(filed,"hongloumeng",keyWord);
//	}
//      
//   
//}
