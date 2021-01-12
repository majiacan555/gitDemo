package LiberyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Ref;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.sun.org.apache.regexp.internal.recompile;

import HttpClientDemo.AddData;
import HttpClientDemo.DBUtil;
import TransLationModel.CEDataNLP2;
import TransLationModel.CEDataNLPText;
import TransLationModel.ChineseAndEnglishModel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

// ��StanfordCoreNLP�ĸ��������annotator������tokenize���ִʣ�, ssplit���Ͼ䣩, pos�����Ա�ע��, lemma����Ԫ����,
//ner������ʵ��ʶ��, parse���﷨������, dcoref��ͬ��ʷֱ棩��˳����д���
//, lemma, ner, parse, dcoref
public class StanfordPos {
	static Connection conn=DBUtil.getConnection();
    public static void main(String[] args) throws SQLException, IOException {
    	String regionTableName = "ChineseAndEnglishModel";
    	 String tableName = "CEDataNLP3";
    	 AddData search = new AddData(); 
    	 
//        Properties props = new Properties(); 
//        props.setProperty("annotators", "tokenize, ssplit, pos");//
//        StanfordCoreNLP Englishpipeline = new StanfordCoreNLP(props);  
//    	  
//        for (int i = 1621663; i <= 1656618; i++) {
//        	System.out.println("���ڽ����ڣ�"+i);
//			ChineseAndEnglishModel CE = search.SearchData(i,regionTableName);
//			try {
//				if (CE != null  ) {
//					getEnglishNLP(CE,Englishpipeline,tableName);
//				} 
//			} catch (Exception e) {
//				continue;
//			}
//		}
        
        Properties props = new Properties();
        File file = new File("E:\\Stanford_Core\\stanford-chinese-corenlp-2018-02-27-models\\StanfordCoreNLP-chinese.properties");
        InputStream inputStream = new FileInputStream(file);
        props.load(inputStream);
        StanfordCoreNLP Chineapipe = new StanfordCoreNLP(props);
         
        
        for (int i = 1629605; i <= 1656618; i= i + 2001) {
        	List<CEDataNLP2> CElist = search.SearchChineseData(i,conn,tableName);
        	for (int j = 0; j < CElist.size(); j++) {
        		CEDataNLP2 CE = CElist.get(j);
        		System.out.println("���ڽ����ڣ�"+CE.getId());
        		try {
        			getChineseNLP(CE,Chineapipe,tableName);  
				} catch (Exception e) {
					System.out.println(e);
				}
			}
        	AddData addData = new AddData();
        	addData.Update(CElist,conn,tableName);
		}
        conn.close(); 
    }

	private static void getEnglishNLP(ChineseAndEnglishModel CE, StanfordCoreNLP Englishpipeline,String TableName) throws SQLException {
		String Englishtext = CE.getEnglishData();
		Annotation document = new Annotation(Englishtext); 
    	Englishpipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
       
        JSONArray jaArray = new JSONArray();
        for (CoreMap sentence : sentences) {
        	for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
        		JSONObject jObject = new JSONObject();
        		String word = token.get(TextAnnotation.class);
        		String pos = token.get(PartOfSpeechAnnotation.class);
        		jObject.put(word, pos);
        		jaArray.put(jObject);
//        		String ne = token.get(NamedEntityTagAnnotation.class);
//        		nerTags.add(ne);
        	}
        }
        CEDataNLP2 ce = new CEDataNLP2();
        ce.setDataId(CE.getId());
        ce.setChineseData(CE.getChineseData());
        ce.setEnglishData(CE.getEnglishData());
        ce.setEnglishNLP(jaArray.toString());
        ce.setMemo(CE.getMemo());
        ce.setTitle(CE.getTitle());
        ce.setTitleID(CE.getTitleID());
        ce.setURL(CE.getURL());
        ce.setUrlType(CE.getUrlType());
        AddData addData = new AddData();
        addData.Add(ce,  TableName);
        System.out.println(jaArray.toString());
//        System.out.println(df.format(new Date()));		
	}

	private static void getChineseNLP(  CEDataNLP2 CE, StanfordCoreNLP Chineapipe ,String TableName ) throws SQLException {
		String Chinatext = CE.getChineseData(); 
		if (Chinatext!=null && Chinatext.length()>0) { 
			Annotation document = new Annotation(Chinatext);
			Chineapipe.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			JSONArray jaArray = new JSONArray();
			for (CoreMap sentence : sentences) {
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					JSONObject jObject = new JSONObject();
					String word = token.get(TextAnnotation.class);
					String pos = token.get(PartOfSpeechAnnotation.class);
					jObject.put(word, pos);
					jaArray.put(jObject);
					
				}
			}
			String jaString= jaArray.toString();
			
			if (jaString.contains("'")) {
				jaString = jaString.replace("'", "''");
			}
			CE.setChineseNLP(jaString); 
		} 
	}
}
