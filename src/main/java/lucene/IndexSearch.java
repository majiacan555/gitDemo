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
//	private static Analyzer analyzer = new IKAnalyzer(); // �������ķִ���  
//    public static void main(String[] args) throws Exception {  
//        IndexSearch search = new IndexSearch();  
//        search.search("����");  
//    }  
//    public void search(String keyWord) throws Exception {  
//        IndexSearcher indexSearcher = null;  
//        QueryParser queryParser = new MultiFieldQueryParser(new String[]{"colContent"}, analyzer);  
//        Query query = null;  
//        try {  
//            // ���ؼ���ת�������������ʶ���Query����  
//            query = queryParser.parse(keyWord);  
//        } catch (ParseException e) {  
//            System.err.println("�ؼ��ʽ���ʧ��!");  
//        }  
//        indexSearcher = new IndexSearcher(Constants.indexPath);  
//          
//        if (indexSearcher != null) {  
//            TopDocs topDocs;  
//            try {  
//                topDocs = indexSearcher.search(query, null, 10);  
//                ScoreDoc[] scoreDocs = topDocs.scoreDocs;  
//                Document document = null;  
//                System.out.println("���ҵ�ƥ���ļ�: " + scoreDocs.length + "��");  
//                for (int i = 0; i < scoreDocs.length; i++) {  
//                    ScoreDoc scorDoc = scoreDocs[i];  
//                    int doc = scorDoc.doc;  
//                    document = indexSearcher.doc(doc);  
//                    System.out.println("�ļ�·��:"+document.getField("path").stringValue());  
//                    System.out.println("����:"+document.getField("colContent").stringValue());  
//                    System.out.println("++++++++++++++++++++++++++++++");  
//                }  
//            } catch (IOException e) {  
//                System.err.println("�������ѯʧ��");  
//                e.printStackTrace();  
//            } finally {  
//                try {  
//                    if (indexSearcher != null) {  
//                        indexSearcher.close();  
//                    }  
//                } catch (Exception e) {  
//                    System.err.println("���ܹر�indexSearcher����");  
//                }  
//            }  
//        }  
//    }  
//}
