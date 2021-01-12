//package lucene;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.StringReader;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
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
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//public class LuceneSearch {
//	private static String DISC_URL = "/home/indexData/data";
//    
//    static {
//        String os = System.getProperty("os.name"); 
//        if(os.toLowerCase().startsWith("win")){ 
//            DISC_URL = "E:\\indexData\\data";
//        }
//        else{
//            DISC_URL ="/home/indexData/data";
//        }
//    }
//          
//    //指定分词器
//    private Analyzer analyzer=new IKAnalyzer();
//    private static Directory directory;
//    //配置
//    private static IndexWriterConfig iwConfig;
//    //配置IndexWriter
//    private static IndexWriter writer; 
//    private static File indexFile = null; 
//      
//    private static Version version = Version.LUCENE_36;
//      
//    private final int PAPGESIZE=10;
//  
//    /**
//     * 全量索引
//     * @Author haoning
//     */
//    public void init() throws Exception {
//          
//        try {
//            indexFile = new File(DISC_URL);
//            if (!indexFile.exists()) {
//                indexFile.mkdir();
//            }
//            directory=FSDirectory.open(indexFile); 
//            //配置IndexWriterConfig 
//            iwConfig = new IndexWriterConfig(version,analyzer); 
//            iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); 
//                //创建写索引对象 
//            writer = new IndexWriter(directory,iwConfig);  
//        } catch (Exception e) {
//        }
//    }
//      
//    public void closeWriter(){
//        try {
//            writer.close();
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//      
//    public void commit(){
//          
//        try {
//            writer.commit();
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//      
//    /**
//     * 一个一个索引
//     * @Author haoning
//     */
//    public void singleIndex(Document doc) throws Exception {
//        writer.addDocument(doc);
//    }
//      
//    /**
//     * 一个跟新
//     * @Author haoning
//     */
//    public void singleUpdate(Document doc) throws Exception {
//        Term term = new Term("url", doc.get("url"));
//        writer.updateDocument(term,doc);
//    }
//      
//    /**
//     * 全量索引
//     * @Author haoning
//     */
//    public void fullIndex(Document[] documentes) throws Exception {
//          
//        writer.deleteAll();
//        for (Document document : documentes) {
//            writer.addDocument(document);
//        }
//        writer.commit();
//    }
//      
//    /**
//     * 根据id删除索引
//     * @Author haoning
//     */
//    public void deleteIndex(Document document)throws Exception{
//        Term term = new Term("url", document.get("url"));//url才是唯一标志
//        writer.deleteDocuments(term);
//        writer.commit();
//    }
//      
//    /**
//     * 根据id增量索引
//     * @Author haoning
//     */
//    public void updateIndex(Document[] documentes) throws Exception{
//        for (Document document : documentes) {
//            Term term = new Term("url", document.get("url"));
//            writer.updateDocument(term, document);
//        }
//        writer.commit();
//    }
//      
//    /**
//     * 直接查询
//     * @Author haoning
//     */
//    public void simpleSearch(String filedStr,String queryStr,int page, int pageSize) throws Exception{
//        File indexDir = new File(DISC_URL); 
//        //索引目录 
//        Directory dir=FSDirectory.open(indexDir); 
//        //根据索引目录创建读索引对象 
//        IndexReader reader = IndexReader.open(dir); 
//        //搜索对象创建 
//        IndexSearcher searcher = new IndexSearcher(reader);
//        TopScoreDocCollector topCollector = TopScoreDocCollector.create(searcher.maxDoc(), false);
//          
//        Term term = new Term(filedStr, queryStr);
//        Query query = new TermQuery(term);
//        searcher.search(query, topCollector);
//        ScoreDoc[] docs = topCollector.topDocs((page-1)*pageSize, pageSize).scoreDocs;
//          
//        printScoreDoc(docs, searcher);
//    }
//      
//    /**
//     * 高亮查询
//     * @Author haoning
//     */
//    public Map<String, Object> highLightSearch(String filed,String keyWord,int curpage, int pageSize) throws Exception{
//        List<SerachResult> list=new ArrayList<SerachResult>();
//        Map<String,Object> map = new HashMap<String,Object>();
//        if (curpage <= 0) {
//            curpage = 1;
//        }
//        if (pageSize <= 0 || pageSize>20) {
//             pageSize = PAPGESIZE;
//        }
//        File indexDir = new File(DISC_URL); //索引目录  
//        Directory dir=FSDirectory.open(indexDir);//根据索引目录创建读索引对象   
//        IndexReader reader = IndexReader.open(dir);//搜索对象创建   
//        IndexSearcher searcher = new IndexSearcher(reader);
//          
//        int start = (curpage - 1) * pageSize;
//          
//        Analyzer analyzer = new IKAnalyzer(true);
//        QueryParser queryParser = new QueryParser(Version.LUCENE_36, filed, analyzer);
//        queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
//        Query query = queryParser.parse(keyWord);
//          
//        int hm = start + pageSize;
//        TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);
//        searcher.search(query, res);
//          
//        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
//        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
//          
//        long amount = res.getTotalHits();
//        //long pages = (rowCount - 1) / pageSize + 1; //计算总页数
//          
//        map.put("amount",amount);//总共多少条记录
//          
//        TopDocs tds = res.topDocs(start, pageSize);
//        ScoreDoc[] sd = tds.scoreDocs;
//          
//        for (int i = 0; i < sd.length; i++) {
//            Document doc = searcher.doc(sd[i].doc);
//            String temp=doc.get("name");
//            //做高亮处理
//            TokenStream ts = analyzer.tokenStream("name", new StringReader(temp));
//              
//            SerachResult record=new SerachResult();
//            String name = highlighter.getBestFragment(ts,temp);
//            String skydirverName=doc.get("skydirverName");
//            String username=doc.get("username");
//            String shareTime=doc.get("shareTime");
//            String describ=doc.get("describ");
//            String typeId=doc.get("typeId");
//            String id=doc.get("id");
//            String url=doc.get("url");
//              
//            record.setName(name);
//            record.setSkydriverName(skydirverName);
//            record.setUsername(username);
//            record.setShareTime(DateFormater.getFormatDate(shareTime,"yyyy-MM-dd HH:mm:ss"));
//            record.setDescrib(describ);
//            record.setTypeId(Integer.parseInt(typeId));
//            record.setId(new BigInteger(id));
//            record.setUrl(url);
//            list.add(record);
//              
//            /*System.out.println("name:"+name);
//            System.out.println("skydirverName:"+skydirverName);
//            System.out.println("username:"+username);
//            System.out.println("shareTime:"+shareTime);
//            System.out.println("describ:"+describ);
//            System.out.println("typeId:"+typeId);
//            System.out.println("id:"+id);
//            System.out.println("url:"+url);*/
//        }
//        map.put("source",list);
//        return map;
//    }
//      
//    /**
//     * 根据前缀查询
//     * @Author haoning
//     */
//    public void prefixSearch(String filedStr,String queryStr) throws Exception{
//        File indexDir = new File(DISC_URL); 
//        //索引目录 
//        Directory dir=FSDirectory.open(indexDir); 
//        //根据索引目录创建读索引对象 
//        IndexReader reader = IndexReader.open(dir); 
//        //搜索对象创建 
//        IndexSearcher searcher = new IndexSearcher(reader);
//          
//        Term term = new Term(filedStr, queryStr);
//        Query query = new PrefixQuery(term);
//          
//        ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
//        printScoreDoc(docs, searcher);
//    }
//      
//    /**
//     * 通配符查询
//     * @Author haoning
//     */
//    public void wildcardSearch(String filedStr,String queryStr) throws Exception{
//        File indexDir = new File(DISC_URL); 
//        //索引目录 
//        Directory dir=FSDirectory.open(indexDir); 
//        //根据索引目录创建读索引对象 
//        IndexReader reader = IndexReader.open(dir); 
//        //搜索对象创建 
//        IndexSearcher searcher = new IndexSearcher(reader);
//          
//        Term term = new Term(filedStr, queryStr);
//        Query query = new WildcardQuery(term);
//        ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
//        printScoreDoc(docs, searcher);
//    }
//      
//    /**
//     * 分词查询
//     * @Author haoning
//     */
//    public void analyzerSearch(String filedStr,String queryStr) throws Exception{
//        File indexDir = new File(DISC_URL); 
//        //索引目录 
//        Directory dir=FSDirectory.open(indexDir); 
//        //根据索引目录创建读索引对象 
//        IndexReader reader = IndexReader.open(dir); 
//        //搜索对象创建 
//        IndexSearcher searcher = new IndexSearcher(reader);
//          
//        QueryParser queryParser = new QueryParser(version, filedStr, analyzer);
//        Query query = queryParser.parse(queryStr);
//          
//        ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
//        printScoreDoc(docs, searcher);
//    }
//      
//    /**
//     * 多属性分词查询
//     * @Author haoning
//     */
//    public void multiAnalyzerSearch(String[] filedStr,String queryStr) throws Exception{
//        File indexDir = new File(DISC_URL); 
//        //索引目录 
//        Directory dir=FSDirectory.open(indexDir); 
//        //根据索引目录创建读索引对象 
//        IndexReader reader = IndexReader.open(dir); 
//        //搜索对象创建 
//        IndexSearcher searcher = new IndexSearcher(reader);
//        QueryParser queryParser = new MultiFieldQueryParser(version, filedStr, analyzer);
//        Query query = queryParser.parse(queryStr);
//          
//        ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
//        printScoreDoc(docs, searcher);
//    }
//      
//    public void printScoreDoc(ScoreDoc[] docs,IndexSearcher searcher)throws Exception{
//        for (int i = 0; i < docs.length; i++) {
//            List<Fieldable> list = searcher.doc(docs[i].doc).getFields();
//            for (Fieldable fieldable : list) {
//                String fieldName = fieldable.name();
//                String fieldValue = fieldable.stringValue();
//                System.out.println(fieldName+" : "+fieldValue);
//            }
//        }
//    }
//}
