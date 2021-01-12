package luceneIK;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
  
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wltea.analyzer.lucene.IKAnalyzer;

 
import Model.Model;
import Model.ParFre;
import Model.RelationModel;
  
public class LuceneSearch {
      
    private static String DISC_URL = "/home/indexData/data";
      
    static {
        String os = System.getProperty("os.name"); 
        if(os.toLowerCase().startsWith("win")){ 
            DISC_URL = "D:\\CEData\\ChineseEnglisnData2";
        }
        else{
            DISC_URL ="/home/indexData/data";
        }
    }
          
    //ָ���ִ���
    private Analyzer analyzer=new IKAnalyzer();
    private static Directory directory;
    //����
    private static IndexWriterConfig iwConfig;
    //����IndexWriter
    private static IndexWriter writer; 
    private static File indexFile = null; 
      
    private static Version version = Version.LUCENE_36;
      
    private final int PAPGESIZE=10;
  
    /**
     * ȫ������
     * @Author haoning
     */
    public void init() throws Exception {
          
        try {
            indexFile = new File(DISC_URL);
            if (!indexFile.exists()) {
                indexFile.mkdir();
            }
            directory=FSDirectory.open(indexFile); 
            //����IndexWriterConfig 
            iwConfig = new IndexWriterConfig(version,analyzer); 
            iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); 
                //����д�������� 
            writer = new IndexWriter(directory,iwConfig);  
        } catch (Exception e) {
        }
    }
      
    public void closeWriter(){
        try {
            writer.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
    public void commit(){
          
        try {
            writer.commit();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
    /**
     * һ��һ������
     * @Author haoning
     */
    public void singleIndex(Document doc) throws Exception {
        writer.addDocument(doc);
    }
      
    /**
     * һ������
     * @Author haoning
     */
    public void singleUpdate(Document doc) throws Exception {
        Term term = new Term("url", doc.get("url"));
        writer.updateDocument(term,doc);
    }
      
    /**
     * ȫ������
     * @Author haoning
     */
    public void fullIndex(Document[] documentes) throws Exception {
          
        writer.deleteAll();
        for (Document document : documentes) {
            writer.addDocument(document);
        }
        writer.commit();
    }
      
    /**
     * ����idɾ������
     * @Author haoning
     */
    public void deleteIndex(Document document)throws Exception{
        Term term = new Term("url", document.get("url"));//url����Ψһ��־
        writer.deleteDocuments(term);
        writer.commit();
    }
      
    /**
     * ����id��������
     * @Author haoning
     */
    public void updateIndex(Document[] documentes) throws Exception{
        for (Document document : documentes) {
            Term term = new Term("url", document.get("url"));
            writer.updateDocument(term, document);
        }
        writer.commit();
    }
      
    /**
     * ֱ�Ӳ�ѯ
     * @Author haoning
     */
    public void simpleSearch(String filedStr,String queryStr,int page, int pageSize) throws Exception{
        File indexDir = new File(DISC_URL); 
        //����Ŀ¼ 
        Directory dir=FSDirectory.open(indexDir); 
        //��������Ŀ¼�������������� 
        IndexReader reader = IndexReader.open(dir); 
        //�������󴴽� 
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector topCollector = TopScoreDocCollector.create(searcher.maxDoc(), false);
          
        Term term = new Term(filedStr, queryStr);
        Query query = new TermQuery(term);
        searcher.search(query, topCollector);
        ScoreDoc[] docs = topCollector.topDocs((page-1)*pageSize, pageSize).scoreDocs;
          
        printScoreDoc(docs, searcher);
    }
      
    /**
     * ������ѯ
     * @Author haoning
     */
    public void highLightSearch(String filed,String keyWord,int curpage, int pageSize ) throws Exception{
        File indexDir = new File(DISC_URL); //����Ŀ¼  
        Directory dir=FSDirectory.open(indexDir);//��������Ŀ¼��������������   
        IndexReader reader = IndexReader.open(dir);//�������󴴽�   
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new IKAnalyzer(true);
        QueryParser queryParser = new QueryParser(Version.LUCENE_36, filed, analyzer);
        queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
        Query query = queryParser.parse(keyWord);
//        Query query1 = queryParser.parse(cancer1);
//        Query query2 = queryParser.parse(cancer2);
        TopDocs topDocs = searcher.search(query, 100000);
//        TopDocs topDocs1 = searcher.search(query1, 100000);
//        TopDocs topDocs2 = searcher.search(query2, 100000);
        ScoreDoc[] sd = topDocs.scoreDocs;
        for (int i = 0; i < sd.length; i++) {
        	Document doc = searcher.doc(sd[i].doc);
			JSONArray jsonArray = new JSONArray(doc.get("EnglishNLP").toString());
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject jObject = (JSONObject)jsonArray.get(j);
				if (jObject.has(keyWord)) {
					System.out.println(doc.get("EnglishData").toString());
					System.out.println(keyWord+":"+jObject.get(keyWord));
					System.out.println(doc.get("ChineseData").toString());
				}
			}
		}
        System.out.println("��������"+sd.length);
//        ScoreDoc[] sd1 = topDocs1.scoreDocs;
//        ScoreDoc[] sd2 = topDocs2.scoreDocs;
//        double width = (sd.length/41844.0)*100;
//        BigDecimal   b   =   new   BigDecimal(width);
//        double Width = b.setScale(2,RoundingMode.HALF_UP).doubleValue();
        
//        
//        AddData addData = new AddData();
//        RelationModel model = new RelationModel();
//        model.setCancer1(cancer1+sd1.length);
//        model.setCancer2(cancer2+sd2.length);
//        model.setWidth(sd.length);
//        addData.AddRelation(model, "Relation5");
        
        
//        System.out.println((sd.length/41844.0)*100);
//        AddData pAddData  = new AddData();
//        String tableName =cancer1+"_"+cancer2;
//        pAddData.CreateTable(tableName);
        
//        for (int i = 0; i < sd.length; i++) {
//            Document doc = searcher.doc(sd[i].doc);
//            AddData p = new AddData();
//	        Model Text = new Model();
//			Text.setId(Integer.parseInt(doc.get("Id")));
//			Text.setName(doc.get("name").toString());
//			Text.setTime(doc.get("time").toString());
//			Text.setData(doc.get("data").toString());
//			p.AddData(Text);
        	
//        	ParFre parFre = new ParFre();
//        	parFre.setWord(cancer1);
//            parFre.setTimes(cancer2+i);
//            ParFre parFre2 = new ParFre();
//            parFre2.setWord(cancer2+i);
//            parFre2.setTimes(cancer2);
//            pAddData.AddParFre(parFre,tableName);
//            pAddData.AddParFre(parFre2,tableName);
//            System.out.println(doc.get("data").toString());
        	
//        } 
        
        
        
//        double width = (double)sd.length/sd1.length*100;
//        BigDecimal   b   =   new   BigDecimal(width);
//        double Width = b.setScale(2,RoundingMode.HALF_UP).doubleValue();
//        int count = 0;
//        if(Width > 9){
//        	AddData addData = new AddData();
//            RelationModel model = new RelationModel();
//            model.setCancer1(cancer1);
//            model.setCancer2(cancer2);
//            model.setWidth(Width);
//            addData.AddRelation(model, "Relation2");
//        	System.out.print(cancer1+" ");
//        	System.out.print(sd1.length);
//        	System.out.print(cancer2);
//        	System.out.print(sd.length);
//        	System.out.print("+"+Width);
//        	System.out.println();
//        	count = 1;
//        }
        
        
        
//        System.out.println(sd.length/sd1.length*100);
//        System.out.println("������:"+topDocs.totalHits);  
        searcher.close();
//        return count;
    }
      
    /**
     * ����ǰ׺��ѯ
     * @Author haoning
     */
    public void prefixSearch(String filedStr,String queryStr) throws Exception{
        File indexDir = new File(DISC_URL); 
        //����Ŀ¼ 
        Directory dir=FSDirectory.open(indexDir); 
        //��������Ŀ¼�������������� 
        IndexReader reader = IndexReader.open(dir); 
        //�������󴴽� 
        IndexSearcher searcher = new IndexSearcher(reader);
          
        Term term = new Term(filedStr, queryStr);
        Query query = new PrefixQuery(term);
          
        ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
        printScoreDoc(docs, searcher);
    }
      
    /**
     * ͨ�����ѯ
     * @Author haoning
     */
    public void wildcardSearch(String filedStr,String queryStr) throws Exception{
        File indexDir = new File(DISC_URL); 
        //����Ŀ¼ 
        Directory dir=FSDirectory.open(indexDir); 
        //��������Ŀ¼�������������� 
        IndexReader reader = IndexReader.open(dir); 
        //�������󴴽� 
        IndexSearcher searcher = new IndexSearcher(reader);
          
        Term term = new Term(filedStr, queryStr);
        Query query = new WildcardQuery(term);
        ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
        printScoreDoc(docs, searcher);
    }
      
    /**
     * �ִʲ�ѯ
     * @Author haoning
     */
    public void analyzerSearch(String filedStr,String queryStr) throws Exception{
        File indexDir = new File(DISC_URL); 
        //����Ŀ¼ 
        Directory dir=FSDirectory.open(indexDir); 
        //��������Ŀ¼�������������� 
        IndexReader reader = IndexReader.open(dir); 
        //�������󴴽� 
        IndexSearcher searcher = new IndexSearcher(reader);
          
        QueryParser queryParser = new QueryParser(version, filedStr, analyzer);
        Query query = queryParser.parse(queryStr);
          
        ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
        printScoreDoc(docs, searcher);
    }
      
    /**
     * �����Էִʲ�ѯ
     * @Author haoning
     */
    public void multiAnalyzerSearch(String[] filedStr,String queryStr) throws Exception{
        File indexDir = new File(DISC_URL); 
        //����Ŀ¼ 
        Directory dir=FSDirectory.open(indexDir); 
        //��������Ŀ¼�������������� 
        IndexReader reader = IndexReader.open(dir); 
        //�������󴴽� 
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser queryParser = new MultiFieldQueryParser(version, filedStr, analyzer);
        Query query = queryParser.parse(queryStr);
          
        ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
        printScoreDoc(docs, searcher);
    }
      
    public void printScoreDoc(ScoreDoc[] docs,IndexSearcher searcher)throws Exception{
        for (int i = 0; i < docs.length; i++) {
            List<Fieldable> list = searcher.doc(docs[i].doc).getFields();
            for (Fieldable fieldable : list) {
                String fieldName = fieldable.name();
                String fieldValue = fieldable.stringValue();
                System.out.println(fieldName+" : "+fieldValue);
            }
        }
    }
}
