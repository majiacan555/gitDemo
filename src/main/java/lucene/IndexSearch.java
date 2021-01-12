//package lucene;
//
//import java.io.IOException;
//
//import javax.management.Query;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.queryParser.MultiFieldQueryParser;
//import org.apache.lucene.queryParser.ParseException;
//import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.util.Constants;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//public class IndexSearch {
//	private static Analyzer analyzer = new IKAnalyzer(); // 极易中文分词器  
//    public static void main(String[] args) throws Exception {  
//        IndexSearch search = new IndexSearch();  
//        search.search("姓名");  
//    }  
//    public void search(String keyWord) throws Exception {  
//        IndexSearcher indexSearcher = null;  
//        QueryParser queryParser = new MultiFieldQueryParser(new String[]{"colContent"}, analyzer);  
//        Query query = null;  
//        try {  
//            // 将关键字转换成索引库可以识别的Query对象  
//            query = queryParser.parse(keyWord);  
//        } catch (ParseException e) {  
//            System.err.println("关键词解析失败!");  
//        }  
//        indexSearcher = new IndexSearcher(Constants.indexPath);  
//          
//        if (indexSearcher != null) {  
//            TopDocs topDocs;  
//            try {  
//                topDocs = indexSearcher.search(query, null, 10);  
//                ScoreDoc[] scoreDocs = topDocs.scoreDocs;  
//                Document document = null;  
//                System.out.println("共找到匹配文件: " + scoreDocs.length + "个");  
//                for (int i = 0; i < scoreDocs.length; i++) {  
//                    ScoreDoc scorDoc = scoreDocs[i];  
//                    int doc = scorDoc.doc;  
//                    document = indexSearcher.doc(doc);  
//                    System.out.println("文件路径:"+document.getField("path").stringValue());  
//                    System.out.println("内容:"+document.getField("colContent").stringValue());  
//                    System.out.println("++++++++++++++++++++++++++++++");  
//                }  
//            } catch (IOException e) {  
//                System.err.println("索引库查询失败");  
//                e.printStackTrace();  
//            } finally {  
//                try {  
//                    if (indexSearcher != null) {  
//                        indexSearcher.close();  
//                    }  
//                } catch (Exception e) {  
//                    System.err.println("不能关闭indexSearcher连接");  
//                }  
//            }  
//        }  
//    }  
//}
